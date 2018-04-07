package com.sf.auth.shiro;

import com.sf.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 衷立杰
 * @date 2018/3/27
 */
@Service
public class AuthUserService implements PrincipalService{
    @Override
    public Object select(String principal) {
        return null;
    }
}
