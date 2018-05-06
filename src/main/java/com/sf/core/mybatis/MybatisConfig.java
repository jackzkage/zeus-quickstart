package com.sf.core.mybatis;

import com.sf.core.mybatis.interceptor.CommonDbInterceptor;
import com.sf.core.mybatis.interceptor.SqlStatementInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijie.zh
 */
@Configuration
public class MybatisConfig {

    /**
     * 设置通用的CREATE_TIME UPDATE_TIME 信息
     *
     * @return
     */
    @Bean
    public CommonDbInterceptor getCommonDbInterceptor() {
        return new CommonDbInterceptor();
    }

//    /**
//     * 记录SQL执行日志的切面
//     * @return
//     */
    @Bean
    public SqlStatementInterceptor getSqlStatementInterceptor(){
        return new SqlStatementInterceptor();
    }

}
