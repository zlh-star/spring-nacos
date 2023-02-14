package com.github.ealen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.github.ealen.domain.mapper")
@SpringBootApplication
public class Oauth2AuthenticatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthenticatorApplication.class, args);
    }
}
