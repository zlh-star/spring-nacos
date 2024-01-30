package com.example.provider.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dubboapi.service.User;
import com.example.dubboapi.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }
}
