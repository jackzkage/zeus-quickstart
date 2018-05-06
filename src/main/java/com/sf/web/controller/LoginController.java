package com.sf.web.controller;

import com.sf.auth.AuthService;
import com.sf.auth.model.CurrentUser;
import com.sf.auth.shiro.token.StatelessToken;
import com.sf.auth.shiro.token.TokenManager;
import com.sf.core.mvc.IResponse;
import com.sf.core.mvc.R;
import com.sf.log.define.AccessLogger;
import com.sf.web.dao.model.SysUser;
import com.sf.web.request.LoginRequest;
import com.sf.web.request.RegisterRequest;
import com.sf.web.response.LoginResponse;
import com.sf.web.service.PasswordEncoder;
import com.sf.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lijie.zh
 */
@RestController
@Slf4j
@Api(tags = {"登录相关接口"})
public class LoginController {

    private TokenManager tokenManager;

    private AuthService authService;

    private UserService userService;


    @Autowired
    public LoginController(TokenManager tokenManager, AuthService authService, UserService userService) {
        this.tokenManager = tokenManager;
        this.authService = authService;
        this.userService = userService;
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @AccessLogger("用户登录")
    public R<IResponse> login(LoginRequest request, HttpServletRequest httpServletRequest) {

        CurrentUser currentUser = authService.login(request.getUsername(), request.getPassword());
        LoginResponse response = new LoginResponse();
        response.setCurrentUser(currentUser);
        StatelessToken createToken = tokenManager.createToken(currentUser.getUserId().toString());
        response.setToken(createToken.getToken());

        //更新最后登录信息
        authService.updateInfoAfterLogin(currentUser.getUserId(), httpServletRequest);
        return R.success(response);

    }

    @ApiOperation(value = "用户退出")
    @PostMapping("/logout")
    public R logout() {
        //退出工作由logoutFilter执行，这里只做消息提示
        return R.success("退出成功");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R register(RegisterRequest request) {

        SysUser user = new SysUser();
        user.setMobileNo(request.getMobileNo());
        user.setPassword(PasswordEncoder.getEncryptedPwd(request.getPassword()));
        userService.createUser(user);
        return R.success();

    }
}
