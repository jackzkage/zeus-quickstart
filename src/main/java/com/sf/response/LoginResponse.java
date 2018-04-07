package com.sf.response;

import com.sf.auth.model.CurrentUser;
import com.sf.core.mvc.AbstractResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.Current;

/**
 * @author zhonglj
 * @date 2017/12/6
 */
@Data
public class LoginResponse extends AbstractResponse {

    @ApiModelProperty(value = "登录成功分配的TOKEN")
    String token;

    CurrentUser currentUser;

}
