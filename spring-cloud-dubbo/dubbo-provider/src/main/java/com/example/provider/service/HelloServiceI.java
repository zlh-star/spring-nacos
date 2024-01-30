package com.example.provider.service;

import com.example.dubboapi.service.HelloService;
import com.example.dubboapi.service.User;
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

    @Autowired
    private UserMonoMapper userMonoMapper;

//    @Override
//    public void hello(User user) {
//        userMapper.insert(user);
//    }

    @Override
    public void hello(User user) {
        userMapper.insert(user);
    }

//    @Override
//    public Mono<Person> save(Person person) {
//        Mono<Person> personMono=null;
//        try {
//          personMono=userMonoMapper.save(person);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return personMono ;
//    }


//    @Override
//    public String hello(String name) {
//        return "hello!"+name;
//    }
}
