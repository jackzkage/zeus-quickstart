package com.sf.web.request;

import com.sf.core.mvc.AbstractRequest;
import lombok.Data;
import javax.validation.constraints.NotBlank;


/**
 * @author lijie.zh
 */
@Data
public class LoginRequest extends AbstractRequest {

    @NotBlank(message = "用户名不能为空")
    String username;

    @NotBlank(message = "密码不能为空")
    String password;

    String verifyCode;

}
