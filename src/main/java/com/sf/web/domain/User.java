package com.sf.web.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author lijie.zh
 */
@Data
public class User implements Serializable {

    @ApiModelProperty(value = "用户ID")
    String userId;

    @ApiModelProperty(value = "用户名")
    String userName;

    @ApiModelProperty(value = "用户手机号码")
    String mobileNo;

    @ApiModelProperty(value = "头像")
    String avatar;

    @ApiModelProperty(value = "地址列表")
    List<String> addrs;


}
