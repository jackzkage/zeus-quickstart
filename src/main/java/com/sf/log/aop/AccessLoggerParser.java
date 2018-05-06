package com.sf.log.aop;


import com.sf.core.aop.MethodInterceptorHolder;
import com.sf.log.define.LoggerDefine;

import java.lang.reflect.Method;

/**
 * @author lijie.zh
 */
public interface AccessLoggerParser {

    boolean support(Class clazz, Method method);

    LoggerDefine parse(MethodInterceptorHolder holder);

}
