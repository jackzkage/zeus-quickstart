package com.sf.request;

import com.sf.core.mvc.AbstractRequest;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author lijie.zhong
 */
@Data
public class LoginRequest extends AbstractRequest {

    @NotBlank(message = "用户名不能为空")
    String username;

    @NotBlank(message = "密码不能为空")
    String password;

    String verifyCode;

}
