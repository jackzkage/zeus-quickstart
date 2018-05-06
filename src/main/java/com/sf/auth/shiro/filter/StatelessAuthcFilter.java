package com.sf.auth.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.sf.auth.shiro.token.StatelessToken;
import com.sf.auth.shiro.token.TokenManager;
import com.sf.core.mvc.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 无状态授权过滤器
 *
 * @author lijie.zh
 */
@Slf4j
public class StatelessAuthcFilter extends AbstractBaseFilter {

    @Autowired
    private TokenManager tokenManager;

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("拦截到的url:" + httpRequest.getRequestURL().toString());

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
            log.error("auth error:" + e.getMessage());
            // 6、登录失败
            onLoginFail(response, "会话不存在或者已过期，请重新登录！");
            return false;
        }

        return true;
    }


}
