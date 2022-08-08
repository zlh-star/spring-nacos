package com.example.springboot.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public String username();
    public String password();
    public int sex();
    public String birth();
    List<String> getAll();

    List<String> getUser(String username);

    List<String> insertUser(@Param("username") String username, @Param("password") String myName, @Param("sex") String sex,@Param("birth") String birth);

    Long updateUser(@Param("username") String username, @Param("password") String password, @Param("sex") String sex,@Param("birth") String birth);

    Long deleteUser(@Param("username") String username);
}
