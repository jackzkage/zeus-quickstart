package com.sf.auth.shiro;

import java.util.List;

/**
 * @author 衷立杰
 * @date 2018/3/27
 */
public interface AuthorizationService {
    List<String> selectRoles(String principal);
}
