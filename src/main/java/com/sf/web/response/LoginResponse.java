package com.sf.web.response;

import com.sf.auth.model.CurrentUser;
import com.sf.core.mvc.AbstractResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lijie.zh
 */
@Data
public class LoginResponse extends AbstractResponse {

    @ApiModelProperty(value = "登录成功分配的TOKEN")
    String token;

    CurrentUser currentUser;

}
