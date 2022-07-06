package com.example.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserDao {
    public String username();
    public String password();
    public int sex();
    public String birth();
    List<String> getAll();
    public String loginUser(@Param("username") String username, @Param("password") String password);

    List<String> getUser(String username);

    List<String> insertUser(@Param("username") String username, @Param("password") String myName, @Param("sex") String sex,@Param("birth") String birth,@Param("email") String email);

    Long updateUser(@Param("username") String username, @Param("password") String password, @Param("sex") String sex,@Param("birth") String birth,@Param("email") String email);

    Long deleteUser(@Param("username") String username);
}
