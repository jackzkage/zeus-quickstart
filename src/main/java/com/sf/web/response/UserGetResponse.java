package com.sf.web.response;

import com.sf.core.mvc.AbstractResponse;
import com.sf.web.domain.User;
import lombok.Builder;
import lombok.Data;

/**
 * @author lijie.zh
 */
@Builder
@Data
public class UserGetResponse extends AbstractResponse {
    User user;
}
