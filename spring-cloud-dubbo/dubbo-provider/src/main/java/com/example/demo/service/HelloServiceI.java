package com.example.demo.service;

import com.example.dubboapi.service.HelloService;
import com.example.dubboapi.service.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class HelloServiceI implements HelloService {

//    @DubboReference
//    private UserMapper userMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void hello(User user) {
        userMapper.insert(user);
    }

//    @Override
//    public String hello(String name) {
//        return "hello!"+name;
//    }
}
