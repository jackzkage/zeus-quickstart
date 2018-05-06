package com.sf.auth.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.sf.core.mvc.R;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lijie.zh
 */
public abstract class AbstractBaseFilter extends AccessControlFilter {
    protected void corsResponseHeader(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Access-control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            corsResponseHeader(httpResponse);
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.onPreHandle(request, response, mappedValue);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }


    /**
     * 登录失败时默认返回401状态码
     *
     * @param response
     * @param errorMsg
     * @throws IOException
     */
    protected void onLoginFail(ServletResponse response, String errorMsg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        corsResponseHeader(httpResponse);
        httpResponse.setStatus(HttpStatus.OK.value());
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("utf-8");
        R resultMsg = R.error(errorMsg);
        httpResponse.getWriter().write(JSON.toJSONString(resultMsg));
        httpResponse.getWriter().close();
    }
}
