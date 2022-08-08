package com.example.springbootmybatis.controller;

import ch.qos.logback.core.pattern.util.RestrictedEscapeUtil;
import com.example.springbootmybatis.mapper.User;
import com.example.springbootmybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
     public UserMapper userMapper;

    @GetMapping("/user")
    public List<User> getUsers(){
        return userMapper.getAll();
    }

    @RequestMapping("/user1")
    public List<String> getUsers(User user){
        String id=user.getId();
        List<String> users=userMapper.getOneUser(id);
        return users;
    }

    @RequestMapping("/add")
    public List<String> addUser(String id, String myName, String age){
//        List<User> userList=new ArrayList<>();
        User user=new User();
        user.getId();
        user.setMyName("myName");
        user.setAge("age");
        List<String> user1=userMapper.insertUser(user);
        List<String> userList=userMapper.addUser(id,user1);
        return userList;
    }

    @RequestMapping("/update")
    public int updateUser(String id,String myName, String age,User user){
//         List<String> ids=userMapper.getAlltedIds();
//         User user=new User();
         String id1=user.getId();
         List<String> user1=userMapper.getOneUser(id1);
        return userMapper.updateUser(user1,myName,age);
    }

    @RequestMapping("/deleteuser")
    public List<String> deleteUser(String id){
        List<String> users=userMapper.getOneUser(id);
        List<String> user=userMapper.deleteUser(id,users);
        return user;
    }
}
