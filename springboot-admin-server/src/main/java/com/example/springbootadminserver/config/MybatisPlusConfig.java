package com.example.springbootadminserver.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    //注入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
       return new PaginationInterceptor();
    }
}
