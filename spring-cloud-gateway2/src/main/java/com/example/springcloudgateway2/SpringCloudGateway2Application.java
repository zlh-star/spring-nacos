package com.example.springcloudgateway2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGateway2Application {

    public static void main(String[] args) {

        SpringApplication.run(SpringCloudGateway2Application.class, args);
    }

}
