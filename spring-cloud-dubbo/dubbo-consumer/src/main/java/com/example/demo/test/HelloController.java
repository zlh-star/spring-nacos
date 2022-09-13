package com.example.demo.test;

import com.example.dubboapi.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @DubboReference
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello("Dubbo!");
    }
}
