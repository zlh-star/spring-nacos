package com.example.springboottest.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboottest.dao.UserDao;
import com.example.springboottest.model.UserModel;
import com.example.springboottest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao,UserModel>implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public List<UserModel> insertAccount(UserModel userModel) {
        return null;
    }

    @Override
    public List<UserModel> deleteAccount(String accountId, List<UserModel> userModelList) {
        return null;
    }

    @Override
    public List<UserModel> updateAccount(String accountId,UserModel userModel) {
        return null;
    }

    @Override
    public List<UserModel> selectAccount(String accountId) {
        return null;
    }

    @Override
    public List<UserModel> getAllAccount() {
        return userDao.getAllAccount();
    }
}
