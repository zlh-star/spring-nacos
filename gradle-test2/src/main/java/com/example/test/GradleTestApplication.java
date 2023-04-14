package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GradleTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradleTestApplication.class, args);
    }

}
