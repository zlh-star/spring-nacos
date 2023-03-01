package com.example.elastic;

import com.example.elastic.test.AuditServiceImpl;
import com.example.elastic.test.SwaggerConfig;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.MalformedURLException;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
//@MapperScan("com.example.elastic.test")
public class ElasticApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticApplication.class, args);
    }

}
