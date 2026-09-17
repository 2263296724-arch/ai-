package com.example.backenddemo.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

//你以为 MyBatis-Plus 会自动分页。但是实际上：
//Page 只是告诉 MyBatis-Plus“我要第几页、每页多少条”，它还需要一个“分页插件”真正去修改 SQL
//所以我们创建了：
@Configuration //@Configuration  意思：告诉 Spring：这个类是“配置类”，里面放的是项目配置。
//这个类的作用就是：专门放 MyBatis-Plus 的一些配置。
public class MybatisPlusConfig {
    // @Bean    告诉 Spring：这个方法返回的对象，你帮我创建并管理。
    @Bean   //@Bean 是干什么的？ 我们之前学过 Spring 的依赖注入。这个地方的意思就是：告诉 Spring：帮我创建并管理这个分页拦截器对象
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加 MySQL 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

//        乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor; //因为 @Bean 方法最终要把这个对象交给 Spring 管理。
    }
}
/*
    MybatisPlusConfig
            ↓
    告诉 MyBatis-Plus：
    “你要启用哪些额外功能？”

    @Configuration = 这是配置类
    @Bean = 这个对象交给 Spring 管理
*/