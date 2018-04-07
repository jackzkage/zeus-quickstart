package com.sf.auth.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sf.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author zhonglj
 */
@Data
@Builder
public class CurrentUser implements Serializable{

    Long userId;

    String userName;

    String mobileNo;

    String avatar;

    private String email;

    private int sex;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private String lastLoginIp;

}
