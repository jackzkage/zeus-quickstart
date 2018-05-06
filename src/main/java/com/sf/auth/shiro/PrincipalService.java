package com.sf.auth.shiro;

/**
 * 
 * @param <T>
 * @author lijie.zh
 */
public interface PrincipalService<T> {

    /**
     * 根据用户id获取用户信息
     *
     * @param principal
     * @return
     */
    T select(String principal);
}
