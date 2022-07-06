package com.example.springbootmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMailApplication.class, args);
    }

}
