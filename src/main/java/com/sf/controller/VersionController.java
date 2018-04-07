package com.sf.controller;

import com.sf.core.mvc.R;
import com.sf.request.VersionCheckRequest;
import com.sf.response.VersionCheckResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 衷立杰
 * @date 2017/12/7
 */
@RestController
@Slf4j
@RequestMapping("version")
@Api(tags = {"登录相关接口"})
public class VersionController {

    @ApiOperation(value = "校验版本号", notes = "注意问题点")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public R<VersionCheckResponse> check(VersionCheckRequest request) {
        Integer reqCurCacheNo = request.getCurCacheNo();
        String reqCurVersion = request.getCurVersion();
        log.debug("reqCacheNo:{}, reqCurVersion:{}", reqCurCacheNo, reqCurVersion);
        VersionCheckResponse response = VersionCheckResponse.builder().updatedContent("1、修复BUG，提升优化体验").isForceUpdate(false).findNewVersion("v3.0.1").build();
        return new R(response);
    }

}
