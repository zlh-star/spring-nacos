package com.example.dubboapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.dubboapi")
public class DubboApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboApiApplication.class, args);
    }

}
