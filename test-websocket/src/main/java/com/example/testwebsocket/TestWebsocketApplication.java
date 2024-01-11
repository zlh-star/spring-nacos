package com.example.testwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TestWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestWebsocketApplication.class, args);
    }

}
