package com.sf.controller;

import com.sf.auth.AuthService;
import com.sf.auth.model.CurrentUser;
import com.sf.auth.shiro.token.StatelessToken;
import com.sf.auth.shiro.token.TokenManager;
import com.sf.core.mvc.IResponse;
import com.sf.core.mvc.R;
import com.sf.request.LoginRequest;
import com.sf.request.RegisterRequest;
import com.sf.response.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lijie.zhong
 */
@RestController
@Slf4j
@Api(tags = {"登录相关接口"})
public class LoginController {

    private TokenManager tokenManager;

    private AuthService authService;

    @Autowired
    public LoginController(TokenManager tokenManager, AuthService authService) {
        this.tokenManager = tokenManager;
        this.authService = authService;
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R<LoginResponse> login(LoginRequest request) {
        CurrentUser currentUser = authService.login(request.getUsername(), request.getPassword());
        LoginResponse response = new LoginResponse();
        response.setCurrentUser(currentUser);
        StatelessToken createToken = tokenManager.createToken(currentUser.getUserId().toString());
        response.setToken(createToken.getToken());
        return R.msg(response);
    }

    @ApiOperation(value = "用户退出")
    @PostMapping("/logout")
    public R logout() {
        //退出工作由logoutFilter执行，这里只做消息提示
        return R.msg("退出成功");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R register(RegisterRequest request) {
        return R.msg();
    }
}