package com.example.testwebsocket.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testwebsocket.mapper.User;
import com.example.testwebsocket.mapper.UserMapper;
import com.example.testwebsocket.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
