package com.sf.auth.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.sf.auth.shiro.token.StatelessToken;
import com.sf.auth.shiro.token.TokenManager;
import com.sf.core.constant.ResultStatusEnum;
import com.sf.core.mvc.R;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 用户登出过滤器
 *
 * @author lijie.zh
 */
@Slf4j
@Setter
@Getter
public class LogoutFilter extends AbstractBaseFilter {

    private TokenManager tokenManager;


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
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
            getSubject(request, response).logout();
            tokenManager.deleteToken(accessToken.getUserCode());

        } catch (Exception e) {
            log.error("logout error:" + e.getMessage());
            // 6、登录失败
            onLoginFail(response, "退出失败！");
            return false;
        }

        return true;
    }

}
