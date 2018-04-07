
package com.sf;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "com.sf.mapper")
public class SysBootApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("服务启动完成!");
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SysBootApplication.class, args);
    }


}
