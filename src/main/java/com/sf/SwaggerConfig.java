package com.sf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


/**
 * Swagger 文档配置
 *
 * @author lijie.zh
 */
@Configuration
@EnableSwagger2
@SuppressWarnings("unchecked")
public class SwaggerConfig {

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了）
     */
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("user")
                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(regex("/.*")))
                .build()
                .apiInfo(userApiInfo());
    }

    @Bean
    public Docket orderApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("order")
                .genericModelSubstitutes(DeferredResult.class)
//              .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .paths(or(regex("/order/.*")))
                .build()
                .apiInfo(orderApiInfo());
    }

    private ApiInfo userApiInfo() {
        return new ApiInfoBuilder()
                .title("用户登录、注册等")
                .description("包含用户的注册、登录、版本校验、升级等基础的接口服务")
                .version("1.0")
                .termsOfServiceUrl("NO terms of service")
                .build();
    }

    private ApiInfo orderApiInfo() {
        return new ApiInfoBuilder()
                .title("用户下单")
                .description("订单相关的接口都在这里")
                .version("1.0")
                .termsOfServiceUrl("NO terms of service")
                .build();
    }

}
