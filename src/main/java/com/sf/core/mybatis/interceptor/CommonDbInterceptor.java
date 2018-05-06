package com.sf.core.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * 通用数据库拦截器
 * <p>
 * 插入时，如果没有创建时间补充；更新时间必补充
 *
 * @author lijie.zh
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class CommonDbInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FIELD_CAMELCASE_CREATE_TIME = "createTime";
    private static final String FIELD_UNDERLINE_CREATE_TIME = "CREATE_TIME";

    private static final String FIELD_CAMELCASE_UPDATE_TIME = "updateTime";
    private static final String FIELD_UNDERLINE_UPDATE_TIME = "UPDATE_TIME";

    @SuppressWarnings({"rawtypes"})
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object parameter = invocation.getArgs()[1];
            Date now = new Date();
            Class classParameter = (Class) parameter.getClass();
            Field[] fields = classParameter.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (FIELD_CAMELCASE_CREATE_TIME.equalsIgnoreCase(fieldName) || FIELD_UNDERLINE_CREATE_TIME
                        .equalsIgnoreCase(fieldName)) {
                    Object value = field.get(parameter);
                    if (value == null) {
                        // create_time只有插入才添加
                        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
                        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
                        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                            field.set(parameter, now);
                        }
                    }
                } else if (FIELD_CAMELCASE_UPDATE_TIME.equalsIgnoreCase(fieldName) || FIELD_UNDERLINE_UPDATE_TIME
                        .equals(fieldName)) {
                    // update_time只有更新才添加
                    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
                    SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
                    if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        field.set(parameter, now);
                    }

                }
            }
        } catch (Exception e) {
            logger.error("通用设置值时出错", e);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}