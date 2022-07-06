package com.example.springbootschedul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootSchedulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSchedulApplication.class, args);
    }

}
