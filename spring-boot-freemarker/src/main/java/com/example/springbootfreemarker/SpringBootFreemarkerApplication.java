package com.example.springbootfreemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootFreemarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFreemarkerApplication.class, args);
    }

}
