package com.springboot.springbootswagger.service;

import com.springboot.springbootswagger.model.User;

import java.util.List;


public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteById(Long id);
}
