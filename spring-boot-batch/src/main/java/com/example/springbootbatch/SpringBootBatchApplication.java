package com.example.springbootbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBatchProcessing
public class SpringBootBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBatchApplication.class, args);
    }

}
