package com.sf.web.request;

import com.sf.core.mvc.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @author lijie.zh
 */
@Data
public class UserGetRequest extends AbstractRequest {
    @ApiModelProperty(value = "用户ID", required = true)
    @NotBlank(message = "用户ID不能为空")
    String userId;
}
