package com.example.springbootmybatis0.dao;

import com.example.springbootmybatis0.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getAll();

    List<User> getUser(String id);

    List<User> insertUser(@Param("id") String id, @Param("myName") String myName, @Param("age") String age);

    Long updateUser(@Param("id") String id, @Param("myName") String myName, @Param("age") String age);

    Long deleteUser(@Param("id") String id);
}
