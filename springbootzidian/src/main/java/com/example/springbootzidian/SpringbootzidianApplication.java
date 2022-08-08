package com.example.springbootzidian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class SpringbootzidianApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootzidianApplication.class, args);
    }

}
