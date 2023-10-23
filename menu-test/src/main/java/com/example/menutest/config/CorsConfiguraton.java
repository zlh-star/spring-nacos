package com.example.menutest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguraton {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许所有网站跨域请求
        corsConfiguration.addAllowedOrigin("*");
        //允许在请求头中携带头信息
        corsConfiguration.addAllowedHeader("*");
        //允许的跨域请求方式
        corsConfiguration.addAllowedMethod("*");
        //允许携带cookie
        corsConfiguration.setAllowCredentials(true);
        //请求有效期
        corsConfiguration.setMaxAge(360000L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //注册跨域配置
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
