package com.sf.auth.shiro;

import com.sf.auth.shiro.cache.EhCacheManager;
import com.sf.auth.shiro.cache.RedisCacheManager;
import com.sf.auth.shiro.filter.LogoutFilter;
import com.sf.auth.shiro.filter.StatelessAuthcFilter;
import com.sf.auth.shiro.token.DefaultTokenManagerImpl;
import com.sf.auth.shiro.token.EhCacheUserTokenHelper;
import com.sf.auth.shiro.token.RedisUserTokenHelper;
import com.sf.auth.shiro.token.TokenManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
@Setter
@Getter
@Slf4j
public class ShiroConfig {

    /**
     * token管理类
     *
     * @param
     * @param bootProperties
     * @return
     */
    @Bean
    public TokenManager tokenManager(@Qualifier("stringRedisTemplate") RedisTemplate redisTemplate, BootProperties bootProperties) {
        log.info("ShiroConfig.getTokenManager()");
        //默认的token管理实现类 32位uuid
        DefaultTokenManagerImpl tokenManager = new DefaultTokenManagerImpl();


        if(bootProperties.getCacheType().equals("redis")){
            RedisUserTokenHelper userTokenHelper = new RedisUserTokenHelper();
            userTokenHelper.setRedis(redisTemplate);
            userTokenHelper.setExpirateTime(bootProperties.getExpirateTime());
            tokenManager.setUserTokenOperHelper(userTokenHelper);
        }else{
            EhCacheUserTokenHelper userTokenHelper = new EhCacheUserTokenHelper();
            userTokenHelper.setCacheManager(ehCacheManager());
            tokenManager.setUserTokenOperHelper(userTokenHelper);
        }


        // 安全的jwttoken方式 不用担心token被拦截
//          RaFilterJwtTokenManagerImpl tokenManager1 = new RaFilterJwtTokenManagerImpl();
//          JwtUtil jwtUtil = new JwtUtil();
//          jwtUtil.setProfiles(bootProperties.getKey());
//          tokenManager1.setJwtUtil(jwtUtil);
//          tokenManager1.setExpirateTime(bootProperties.getExpirateTime());
//          EhCacheLoginFlagHelper ehCacheLoginFlagHelper = new EhCacheLoginFlagHelper();
//          ehCacheLoginFlagHelper.setCacheManager(cacheManager);
//          tokenManager1.setLoginFlagOperHelper(ehCacheLoginFlagHelper);
//          tokenManager1.setUserTokenOperHelper(ehCacheUserTokenHelper);

        return tokenManager;
    }

    /**
     * 无状态域
     *
     * @param tokenManager
     * @param principalService     登陆账号服务需要实现PrincipalService接口
     * @param authorizationService 授权服务 需要实现authorizationService接口
     * @return
     */
    @Bean
    public StatelessRealm statelessRealm(TokenManager tokenManager, @Qualifier("authUserService") PrincipalService principalService, MyAuthService authorizationService, @Qualifier("redisTemplate") RedisTemplate redisTemplate, BootProperties bootPropertiess) {
        log.info("ShiroConfig.getStatelessRealm()");
        StatelessRealm realm = new StatelessRealm();
        realm.setTokenManager(tokenManager);
        realm.setPrincipalService(principalService);
        realm.setAuthorizationService(authorizationService);
        if(bootPropertiess.getCacheType().equals("redis")){
            realm.setCacheManager(redisCacheManager(redisTemplate));
        }else{
            realm.setCacheManager(ehCacheManager());
        }
        return realm;
    }

    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }

    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return em;
    }



    /**
     * 会话管理类 禁用session
     *
     * @return
     */
    @Bean
    public DefaultSessionManager defaultSessionManager() {
        log.info("ShiroConfig.getDefaultSessionManager()");
        DefaultSessionManager manager = new DefaultSessionManager();
        manager.setSessionValidationSchedulerEnabled(false);
        return manager;
    }

    /**
     * 安全管理类
     *
     * @param statelessRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(StatelessRealm statelessRealm) {
        log.info("ShiroConfig.getDefaultWebSecurityManager()");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();

        //禁用sessionStorage
        DefaultSubjectDAO de = (DefaultSubjectDAO) manager.getSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = (DefaultSessionStorageEvaluator) de.getSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);

        manager.setRealm(statelessRealm);

        //无状态主题工程，禁止创建session
        StatelessDefaultSubjectFactory statelessDefaultSubjectFactory = new StatelessDefaultSubjectFactory();
        manager.setSubjectFactory(statelessDefaultSubjectFactory);

        manager.setSessionManager(defaultSessionManager());
        //设置了SecurityManager采用使用SecurityUtils的静态方法 获取用户等
        SecurityUtils.setSecurityManager(manager);
        return manager;
    }

    /**
     * Shiro生命周期处理
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        log.info("ShiroConfig.getLifecycleBeanPostProcessor()");
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 身份验证过滤器
     *
     * @param manager
     * @param tokenManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager manager, TokenManager tokenManager) {
        log.info("ShiroConfig.getShiroFilterFactoryBean()");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        Map<String, Filter> filters = new HashMap<String, Filter>();
        //无需增加 shiro默认会添加该filter
        //filters.put("anon", anonymousFilter());

        //无状态授权过滤器
        //特别注意！自定义的StatelessAuthcFilter
        //不能声明为bean 否则shiro无法管理该filter生命周期，该过滤器会执行其他过滤器拦截过的路径
        //这种情况通过普通spring项目集成shiro不会出现，boot集成会出现，搞了好久才明白，巨坑</span>
        StatelessAuthcFilter statelessAuthcFilter = statelessAuthcFilter(tokenManager);
        filters.put("statelessAuthc", statelessAuthcFilter);

        LogoutFilter logoutFilter = logoutFilter(tokenManager);
        filters.put("logout", logoutFilter);
        bean.setFilters(filters);


        //注意是LinkedHashMap 保证有序
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //1， 相同url规则，后面定义的会覆盖前面定义的(执行的时候只执行最后一个)。
        //2， 两个url规则都可以匹配同一个url，只执行第一个
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");

        filterChainDefinitionMap.put("/**", "statelessAuthc");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * @return
     * @Function: ShiroConfig::anonymousFilter
     * @Description: 该过滤器无需增加 shiro默认会添加该filter
     */
    public AnonymousFilter anonymousFilter() {
        log.info("ShiroConfig.anonymousFilter()");
        return new AnonymousFilter();
    }

    /**
     * @param tokenManager
     * @return
     * @Function: ShiroConfig::statelessAuthcFilter
     * @Description: 无状态授权过滤器 注意不能声明为bean 否则shiro无法管理该filter生命周期，<br>
     * 该过滤器会执行其他过滤器拦截过的路径
     */
    public StatelessAuthcFilter statelessAuthcFilter(TokenManager tokenManager) {
        log.info("ShiroConfig.statelessAuthcFilter()");
        StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter();
        statelessAuthcFilter.setTokenManager(tokenManager);
        return statelessAuthcFilter;
    }

    public LogoutFilter logoutFilter(TokenManager tokenManager) {
        log.info("ShiroConfig.logoutFilter()");
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setTokenManager(tokenManager);
        return logoutFilter;
    }

}