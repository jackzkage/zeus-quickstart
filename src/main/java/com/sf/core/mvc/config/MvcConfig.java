package com.sf.core.mvc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


/**
 * @author zhonglj
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {



    /**
    * 定义返回的JOSN 处理器为FastJson
    */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //定义一个转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //添加fastjson的配置信息 比如 ：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //在转换器中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将转换器添加到converters中
        converters.add(fastConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new CustomModelAttributeMethodProcessor(true));
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(authorizationInterceptor);
//    }

    //    @Override
//    public Validator getValidator() {
//        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
//        validator.setValidationMessageSource(messageSource());
//        return validator;
//    }
//
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("msg");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }

}  