package com.sf.auth.shiro.token;


/**
 * 用户令牌操作接口
 *
 * @author lijie.zh
 */
public interface UserTokenOperHelper {

    /**
     * 创建令牌
     *
     * @param userCode
     * @param token
     */
    void createUserToken(String userCode, String token);

    /**
     * 根据用户编码获取令牌
     *
     * @param userCode
     * @return
     */
    String getUserToken(String userCode);

    /**
     * 更新令牌， 每次获取令牌成功时更新令牌失效时间
     *
     * @param userCode
     * @param token
     */
    void updateUserToken(String userCode, String token);

    /**
     * 删除令牌
     *
     * @param userCode
     */
    void deleteUserToken(String userCode);

}
