package com.example.springboothezelcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootHezelcastApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHezelcastApplication.class, args);
    }

}
