package com.example.springpictures;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springpictures")
public class SpringPicturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPicturesApplication.class, args);
    }

}
