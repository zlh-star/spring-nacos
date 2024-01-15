package com.example.springcloudzuul.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
