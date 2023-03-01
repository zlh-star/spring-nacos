package com.example.dubboapi.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
     void saveUser(User user);
}
