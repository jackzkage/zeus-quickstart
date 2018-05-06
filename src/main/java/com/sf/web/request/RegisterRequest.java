package com.sf.web.request;

import com.sf.core.mvc.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijie.zh
 */
@Data
public class RegisterRequest extends AbstractRequest {

    @NotBlank(message = "用户手机号码不能为空")
    @ApiModelProperty(value = "用户手机号码", required = true)
    String mobileNo;

    @NotBlank(message = "用户密码不能为空")
    @ApiModelProperty(value = "用户密码（加密后的密码）", required = true)
    String password;

}
