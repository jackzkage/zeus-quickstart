package com.sf.auth.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.sf.auth.shiro.token.StatelessToken;
import com.sf.auth.shiro.token.TokenManager;
import com.sf.core.constant.ResultStatusEnum;
import com.sf.core.mvc.R;
import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 无状态授权过滤器
 *
 * @author luanhy
 */
public class StatelessAuthcFilter extends AccessControlFilter {


    private final Logger logger = Logger.getLogger(StatelessAuthcFilter.class);

    @Autowired
    private TokenManager tokenManager;

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.info("拦截到的url:" + httpRequest.getRequestURL().toString());

        // 前段token授权信息放在请求头中传入
        String authorization = httpRequest.getHeader("authorization");
        if (StringUtils.isEmpty(authorization)) {
            onLoginFail(response, "请求头不包含认证信息authorization");
            return false;
        }
        // 获取无状态Token
        StatelessToken accessToken = tokenManager.getToken(authorization);
        try {
            // 委托给Realm进行登录
            getSubject(request, response).login(accessToken);
            // 通过isPermitted 才能调用doGetAuthorizationInfo方法获取权限信息
            getSubject(request, response).isPermitted(httpRequest.getRequestURI());

        } catch (Exception e) {
            logger.error("auth error:" + e.getMessage());
            // 6、登录失败
            onLoginFail(response, "会话不存在或者已过期，请重新登录！");
            return false;
        }

        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        return false;
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response, String errorMsg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_OK);


        httpResponse.setHeader("Access-control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setStatus(HttpStatus.OK.value());

        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("utf-8");
        R resultMsg = R.error(errorMsg);
        httpResponse.getWriter().write(JSON.toJSONString(resultMsg));
        httpResponse.getWriter().close();
    }

}
