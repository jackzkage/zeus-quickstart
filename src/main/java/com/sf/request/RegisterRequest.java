package com.sf.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author zhonglj
 * @date 2017/12/6
 */
@Data
public class RegisterRequest implements Serializable {

    @NotEmpty(message="用户手机号码不能为空")
    @ApiModelProperty(value = "用户手机号码", required = true)
    String mobileNo;

    @NotEmpty(message="用户密码不能为空")
    @ApiModelProperty(value = "用户密码（加密后的密码）", required = true)
    String password;

}
