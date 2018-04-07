package com.sf.auth.shiro.token;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * ehcache用户令牌帮助类
 *
 * @author luanhy
 */
@Getter
@Setter
@Slf4j
public class EhCacheUserTokenHelper implements UserTokenOperHelper {

    /**
     * 对应ehcache.xml cache Name
     */
    private String userTokenCacheName = "userTokenCache";

    /**
     * ehcache缓存管理器
     */
    private CacheManager cacheManager;


    @Override
    public void createUserToken(String userCode, String token) {
        Cache cache = getUserTokenCache();
        cache.put(userCode, token);
        log.info("创建ehcache缓存：Key：{}， Value:{}", userCode, token);
    }

    @Override
    public String getUserToken(String userCode) {
        Cache<String, String> cache = getUserTokenCache();
        if (cache == null) {
            log.info("Ehcache的缓存为空");
            return null;
        } else {
            String token = cache.get(userCode);
            if (token == null) {
                return null;
            } else {
                log.info("获取ehcache的缓存：Key：{}， Value:{}", userCode, token);
                return token;
            }
        }
    }

    public Cache getUserTokenCache() {
        Cache cache = cacheManager.getCache(userTokenCacheName);
        return cache;
    }

    @Override
    public void updateUserToken(String userCode, String token) {
        Cache cache = getUserTokenCache();
        cache.put(userCode, token);
        log.info("更新ehcache缓存：Key：{}， Value:{}", userCode, token);
    }

    @Override
    public void deleteUserToken(String userCode) {
        Cache cache = getUserTokenCache();
        cache.remove(userCode);
        log.info("删除ehcache缓存：Key：{}", userCode);
    }


}
