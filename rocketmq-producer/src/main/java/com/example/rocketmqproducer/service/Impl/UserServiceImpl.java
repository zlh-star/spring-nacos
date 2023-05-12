package com.example.rocketmqproducer.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rocketmqproducer.dto.UserMapper;
import com.example.rocketmqproducer.dto.Account;
import com.example.rocketmqproducer.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Account> implements UserService {
}
