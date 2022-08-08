package com.example.springswagger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserDao {
    User getUserById(String id);
    List<User> getAllUsers();
    User saveUser(User user);
    int addUser(@Param("users") List<User> users);
    void deleteById(String id);
    String updateUser(User user);
}
