package com.example.springasync.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springasync.mapper.User;
import com.example.springasync.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
