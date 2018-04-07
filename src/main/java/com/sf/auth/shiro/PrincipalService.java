package com.sf.auth.shiro;

/**
 * 用户服务
 * @author luanhy
 *
 * @param <T>
 */
public interface PrincipalService<T> {
	
	/**
	 * 根据用户id获取用户信息
	 * @param principal
	 * @return
	 */
	T select(String principal);
}
