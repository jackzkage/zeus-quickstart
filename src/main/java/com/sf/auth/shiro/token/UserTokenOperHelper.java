package com.sf.auth.shiro.token;


/**
 * 用户令牌操作接口
 * @author luanhy
 *
 */
public interface UserTokenOperHelper {

	public void createUserToken(String userCode,String token);

	/**
	 * 根据用户编码获取令牌
	 * @param userCode
	 * @return
	 */
	public String getUserToken(String userCode);
	
	/**
	 * 更新令牌， 每次获取令牌成功时更新令牌失效时间
	 * @param userCode
	 * @param token
	 */
	public void updateUserToken(String userCode,String token);
	
	/**
	 * 删除令牌
	 * @param userCode
	 */
	public void deleteUserToken(String userCode);
	
}
