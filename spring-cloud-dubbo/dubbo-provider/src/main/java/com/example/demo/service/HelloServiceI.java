package com.example.demo.service;

import com.example.dubboapi.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class HelloServiceI implements HelloService {
    @Override
    public String hello(String name) {
        return "hello!"+name;
    }
}
