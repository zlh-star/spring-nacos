package com.example.redisdelaydelete.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.redisdelaydelete.mapper.User;

public interface UserService extends IService<User> {


    int insert(User user);

    int update(User user) throws InterruptedException;

    public User getUser(String age);
}
