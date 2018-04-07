package com.sf.core.mvc.config;

import com.sf.core.exception.InvalidParameterException;
import com.sf.core.mvc.IRequest;
import org.springframework.core.MethodParameter;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的参数绑定校验处理器
 * @author zhonglj
 */
public class CustomModelAttributeMethodProcessor extends ModelAttributeMethodProcessor {

    public CustomModelAttributeMethodProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    protected void bindRequestParameters(final WebDataBinder binder, final NativeWebRequest request) {
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        ((ServletRequestDataBinder) binder).bind(servletRequest);
    }

    @Override
    protected void validateIfApplicable(final WebDataBinder binder, final MethodParameter parameter) {

        if (binder.getTarget()  instanceof IRequest) {
            try {
                binder.validate();
                if(!binder.getBindingResult().hasErrors()){
                    ((IRequest) binder.getTarget()).check();
                }
            } catch (InvalidParameterException e) {
                binder.getBindingResult().addError(new ObjectError(binder.getTarget().getClass().getName(),new String[]{e.getErrCode()}, new Object[]{}, e.getErrMsg()));
            }

            return;
        }
        super.validateIfApplicable(binder, parameter);

    }
}