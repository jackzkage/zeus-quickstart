package com.sf.web.request;

import com.sf.core.exception.InvalidParameterException;
import com.sf.core.mvc.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijie.zh
 */
@Data
public class PasswordModifyRequest extends AbstractRequest {

    @NotBlank(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    String userId;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码")
    String newPassword;

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码")
    String oldPassword;

    @Override
    public void check() throws InvalidParameterException {
        if (getNewPassword().equals(getOldPassword())) {
            throw new InvalidParameterException("新密码和旧密码不能相同");
        }
    }
}
