package com.example.springbootmybatis0.service;

import com.example.springbootmybatis0.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getAll();

    List<User> getUser(String id);

    List<User> insertUser(@Param("id") String id, @Param("myName") String myName, @Param("age") String age);

    Long updateUser(@Param("id") String id, @Param("myName") String myName, @Param("age") String age);

    Long deleteUser(@Param("id") String id);
}
