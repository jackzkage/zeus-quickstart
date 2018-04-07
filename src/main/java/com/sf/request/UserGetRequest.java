package com.sf.request;

import com.sf.core.mvc.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author 衷立杰
 * @date 2017/12/7
 */
@Data
public class UserGetRequest extends AbstractRequest {
    @ApiModelProperty(value = "用户ID", required = true)
    @NotBlank(message = "用户ID不能为空")
    String userId;
}
