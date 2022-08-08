package com.example.springbootadminserver.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootadminserver.mapper.User;
import com.example.springbootadminserver.mapper.UserMapper;
import com.example.springbootadminserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
