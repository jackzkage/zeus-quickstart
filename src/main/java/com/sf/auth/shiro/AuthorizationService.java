package com.sf.auth.shiro;

import java.util.List;

/**
 * @author 衷立杰
 * @date 2018/3/27
 */
public interface AuthorizationService {
    public List<String> selectRoles(String principal);
}
