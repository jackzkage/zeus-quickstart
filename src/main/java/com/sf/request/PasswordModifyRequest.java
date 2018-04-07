package com.sf.request;

import com.sf.core.exception.InvalidParameterException;
import com.sf.core.mvc.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zhonglj
 * @date 2017/12/6
 */
@Data
public class PasswordModifyRequest extends AbstractRequest {

    @NotEmpty(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    String userId;

    @NotEmpty(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码")
    String newPassword;

    @NotEmpty(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码")
    String oldPassword;

    @Override
    public void check() throws InvalidParameterException {
        if (getNewPassword().equals(getOldPassword())) {
            throw new InvalidParameterException("新密码和旧密码不能相同");
        }
    }
}
