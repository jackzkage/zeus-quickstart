package com.sf.auth.shiro.token;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * ehcache用户令牌帮助类
 *
 * @author luanhy
 */
@Setter
@Getter
@Slf4j
public class RedisUserTokenHelper implements UserTokenOperHelper {

    private RedisTemplate<String, String> redis;

    private Long expirateTime;

    @Override
    public void createUserToken(String userCode, String token) {
        redis.boundValueOps(userCode).set(token, getExpirateTime(), TimeUnit.SECONDS);
        log.info("创建redis缓存：Key：{}， Value:{}", userCode, token);

    }

    @Override
    public String getUserToken(String userCode) {
        String token = redis.boundValueOps(userCode).get();
        log.info("获取redis缓存：Key：{}， Value:{}", userCode, token);
        return token;
    }

    @Override
    public void updateUserToken(String userCode, String token) {
        redis.boundValueOps(userCode).expire(getExpirateTime(), TimeUnit.SECONDS);
        log.info("更新redis缓存：Key：{}， Value:{}", userCode, token);

    }

    @Override
    public void deleteUserToken(String userCode) {
        redis.delete(userCode);
        log.info("删除redis缓存：Key：{}", userCode);
    }
}
