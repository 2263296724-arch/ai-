package com.example.backenddemo.config;


import com.example.backenddemo.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //就是告诉 Spring： 这是一个配置类，启动项目时要读取这里面的配置。

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(
            InterceptorRegistry registry) {
        // 注册 JWT 拦截器
                registry.addInterceptor(new JwtInterceptor());
    }
}
