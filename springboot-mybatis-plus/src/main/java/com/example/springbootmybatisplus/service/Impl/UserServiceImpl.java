package com.example.springbootmybatisplus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootmybatisplus.mapper.User;
import com.example.springbootmybatisplus.mapper.UserMapper;
import com.example.springbootmybatisplus.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
        }
