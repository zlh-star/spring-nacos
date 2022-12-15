package com.example.springredission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringRedissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedissionApplication.class, args);
    }

}
