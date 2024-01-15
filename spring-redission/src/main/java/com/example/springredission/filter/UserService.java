package com.example.springredission.filter;

import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    int addUser(UserQuery query);

    User findUser(User user);

    void logout(String token);

}
