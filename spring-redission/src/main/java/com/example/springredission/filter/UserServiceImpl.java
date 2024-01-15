package com.example.springredission.filter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springredission.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(UserQuery query) {
        return 0;
    }

    @Override
    public User findUser(User user) {
        return userMapper.selectById(user.getUserId());
    }

    @Override
    public void logout(String token) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getToken,token);
        userMapper.delete(queryWrapper);
    }
}
