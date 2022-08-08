package com.example.springbootjpadruid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootJpaDruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaDruidApplication.class, args);
    }

}
