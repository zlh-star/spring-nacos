package com.example.springbootmybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAll();
    List<User> getUser(String id);
    List<String> insertUser(@Param("user") User user);
//    Long updateUser(User user);
    List<User> delteOneUser(@Param("id") String id,@Param("users") List<User> users);
//    新增
    List<String> getOneUser(String id);
    List<String> addUser(@Param("id") String id,@Param("addUser")List<String> addUser);
    int updateUser(@Param("id") List<String> id,@Param("myName") String myName,@Param("age") String age);
    List<String> deleteUser(@Param("id") String id, @Param("list") List<String> list );
    List<String> getAlltedIds();


}
