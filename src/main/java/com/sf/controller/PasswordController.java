package com.sf.controller;

import com.sf.core.mvc.R;
import com.sf.request.PasswordModifyRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lijie.zhong
 */
@RestController
@RequestMapping("password")
@Slf4j
@Api(tags = {"登录相关接口"})
public class PasswordController {

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public R modify(PasswordModifyRequest request) {
        log.debug("PasswordModifyRequest:{}", request);
        return R.msg("修改成功");
    }
}
