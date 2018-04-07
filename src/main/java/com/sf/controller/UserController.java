package com.sf.controller;

import com.sf.core.mvc.R;
import com.sf.domain.User;
import com.sf.response.UserGetResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhonglj
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(tags = "用户相关的接口")
public class UserController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public R<UserGetResponse> getUser() {
        User user = new User();
        user.setUserId("10001");
        user.setUserName("衷立杰");
        user.setMobileNo("18688869930");
        user.setAvatar("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3448484253,3685836170&fm=27&gp=0.jpg");

        UserGetResponse response = UserGetResponse.builder().user(user).build();

        Subject subject = SecurityUtils.getSubject();
        boolean a = subject.hasRole("admin");

        return new R(response);
    }

}