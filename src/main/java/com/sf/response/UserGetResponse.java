package com.sf.response;

import com.sf.core.mvc.AbstractResponse;
import com.sf.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

/**
 * @author 衷立杰
 * @date 2017/12/7
 */
@Builder
@Data
public class UserGetResponse extends AbstractResponse {
    User user;
}
