package com.sf.auth.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 衷立杰
 * @date 2017/12/7
 */
@Data
public class AuthDO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    String userId;

    @ApiModelProperty(value = "上次登录时间")
    String lastLoginTime;

    @ApiModelProperty(value = "上次登录IP")
    String lastLoginIp;

}
