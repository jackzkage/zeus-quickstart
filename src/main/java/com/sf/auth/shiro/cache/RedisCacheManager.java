package com.sf.auth.shiro.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
@Slf4j
public class RedisCacheManager implements CacheManager {
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

	private RedisTemplate redisTemplate;

	public RedisCacheManager(RedisTemplate redisTemplate){
		setRedisTemplate(redisTemplate);
	}
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		log.debug("获取名称为: " + name + " 的RedisCache实例");
		Cache c = caches.get(name);
		if (c == null) {
			c = new RedisCache<K, V>(name,redisTemplate);
			caches.put(name, c);
		}
		return c;
	}

}
