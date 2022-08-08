package com.example.springbootmybatis0;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan("com.example.springbootmybatis0.service")
@EnableDiscoveryClient
@EnableSwagger2
public class SpringBootMybatis0Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatis0Application.class, args);
    }

}
