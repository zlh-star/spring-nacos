package com.example.springbootoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOauthApplication.class, args);
    }

}
