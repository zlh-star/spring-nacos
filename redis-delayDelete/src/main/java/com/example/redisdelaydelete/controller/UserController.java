package com.example.redisdelaydelete.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.redisdelaydelete.config.Result;
import com.example.redisdelaydelete.mapper.User;
import com.example.redisdelaydelete.mapper.UserMapper;
import com.example.redisdelaydelete.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "delay")
@RestController
public class UserController {

    @Autowired
    UserService userService;



    @ApiOperation(value = "/insert",tags = "新增")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(@RequestBody User user){
       userService.insert(user);
    }

    @ApiOperation(value = "/update",tags = "更新")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void update(@RequestBody User user) throws InterruptedException {
        userService.update(user);
    }

    @ApiOperation(value = "/getUser",tags = "获取")
    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    public void getUser(@RequestParam String age){
       userService.getUser(age);
    }

}
