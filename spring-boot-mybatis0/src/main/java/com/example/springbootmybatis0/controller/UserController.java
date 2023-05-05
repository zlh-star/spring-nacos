package com.example.springbootmybatis0.controller;

import com.example.springbootmybatis0.bean.User;
import com.example.springbootmybatis0.dao.UserMapper;
import com.example.springbootmybatis0.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@Api(value = "用户演示")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public UserMapper userMapper;

    @GetMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录",tags ="用户登录" )
    public ModelAndView login(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

//    @RequestMapping("/swagger")
//    @ApiOperation(value = "swagger界面")
//    public ModelAndView swagger(){
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.setViewName("swagger-ui");
//        return modelAndView;
//    }

    @GetMapping("/register")
    public  ModelAndView register(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取全部用户")
    public List<User> getAllUsers(){
        return userMapper.getAll();
    }

    @GetMapping("/getUser")
    @ApiOperation(value = "获取用户",notes = "根据用户Id获取用户，",tags = "id唯一")
    public List<User> getUser(String id){
        return userMapper.getUser(id);
    }

    @PostMapping("/insertUser")
    @ApiOperation(value ="新增新用户",notes ="更具相关信息添加新用户" )
    public List insertUser(String id, String myName, String age, User user){
//        user.setId("id");
//        user.setMyName("myName");
//        user.setAge("age");

        List<User> users=userMapper.insertUser(id, myName, age);
        if(CollectionUtils.isEmpty(users)){
            return Collections.EMPTY_LIST;
        }
        List<User> user1=userMapper.getAll();
        if(CollectionUtils.isEmpty(user1)){
            return Collections.EMPTY_LIST;
        }
//        List<User> listAll=user1.parallelStream().collect(Collectors.toList());
//        List<User> listAll2=users.parallelStream().collect(Collectors.toList());
        user1.addAll(users);
        List<User>distinctlist=user1.stream().distinct().collect(Collectors.toList());
        return distinctlist;
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户信息",notes ="更具需要更改的信息进行修改",tags = "并对新用户进行保存")
    public Long updateUser(User user,String id,String myName,String age){

        return userMapper.updateUser(id, myName, age);
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "删除用户信息")
    public Long deleteUser(String id){

        return userMapper.deleteUser(id);
    }


}
