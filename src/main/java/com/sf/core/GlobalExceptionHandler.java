package com.sf.core;

import com.sf.auth.shiro.BootProperties;
import com.sf.core.constant.ResultStatusEnum;
import com.sf.core.exception.BusinessException;
import com.sf.core.mvc.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.exceptions.IbatisException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加全局异常处理流程
 *
 * @author lijie.zh
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    BootProperties bootProperties;


    /**
     * validtor 参数绑定异常处理
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    @SuppressWarnings("unchecked")
    public Object methodArgumentNotValidHandler(HttpServletRequest request, BindException exception) throws Exception {
        List<String> invalidArguments = new ArrayList<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                log.error("defaultMessage:{}, field:{}, rejectedValue:{} ", error.getDefaultMessage(), ((FieldError) error).getField(), ((FieldError) error).getRejectedValue());
            } else {
                log.error("defaultMessage:{}, object:{} ", error.getDefaultMessage(), error.getObjectName(), error.getCode());
            }
            invalidArguments.add(error.getDefaultMessage());
        }

        String errorStr = StringUtils.join(invalidArguments, ",");
        return R.error(errorStr);
    }

    /**
     * spring 请求参数绑定异常处理
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public Object servletArgumentNotValidHandler(HttpServletRequest request, ServletRequestBindingException exception) throws Exception {
        return R.error(exception.getMessage());
    }


    /**
     * 业务异常处理
     *
     * @param request
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Object businessExceptionHandler(HttpServletRequest request, BusinessException exception) {
        log.error("业务异常：{}, {}", exception.getErrMsg(), exception.getStackTrace().length > 0 ? exception.getStackTrace()[0] : "");
        return R.error(exception.getErrMsg(), StringUtils.isBlank(exception.getErrCode()) ? ResultStatusEnum.ERROR.getValue() : exception.getErrCode());
    }


    /**
     * MyBatis 异常处理
     *
     * @param request
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyBatisSystemException.class)
    public Object myBatisSystemExceptionHandler(HttpServletRequest request, MyBatisSystemException exception) {
        log.error("mybatis异常", exception);
        return gerExceptionMessage(exception);
    }

    /**
     * 未知异常的统一的处理
     *
     * @param request
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object unknownExceptionHandler(HttpServletRequest request, Exception exception) {
        log.error("系统异常", exception);
        return gerExceptionMessage(exception);

    }

    private R gerExceptionMessage(Exception exception) {
        if (bootProperties.isEnableFriendlyError()) {
            return R.error("系统错误，请稍后再试！");
        }
        String message = exception.getMessage();
        if (StringUtils.isBlank(message)) {
            message = null != exception.getCause() ? exception.getCause().getMessage() : "未知错误";
        }
        return R.error(message);
    }
}