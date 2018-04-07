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
 * 无状态授权过滤器
 *
 * @author luanhy
 */
@Slf4j
@Setter
@Getter
public class LogoutFilter extends AccessControlFilter {

    private TokenManager tokenManager;

    private void corsResponseHeader(HttpServletResponse httpResponse) {
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
    protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
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

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 登录失败时默认返回401状态码
     * @param response
     * @param errorMsg
     * @throws IOException
     */
    private void onLoginFail(ServletResponse response, String errorMsg) throws IOException {
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
