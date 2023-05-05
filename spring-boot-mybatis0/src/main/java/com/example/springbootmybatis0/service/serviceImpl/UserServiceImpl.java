package com.example.springbootmybatis0.service.serviceImpl;

import com.example.springbootmybatis0.bean.User;
import com.example.springbootmybatis0.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> getUser(String id) {
        return null;
    }

    @Override
    public List<User> insertUser(String id, String myName, String age) {
        return null;
    }

    @Override
    public Long updateUser(String id, String myName, String age) {
        return null;
    }

    @Override
    public Long deleteUser(String id) {
        return null;
    }
}
