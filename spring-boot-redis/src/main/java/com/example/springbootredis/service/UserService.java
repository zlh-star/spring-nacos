package com.example.springbootredis.service;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    @ApiOperation(value="保存账号信息",httpMethod = "get")
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    User save(@ApiParam(name="user",value="用户") @RequestParam("user") User user);

    @ApiOperation(value="根据id获取用户",httpMethod = "get")
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    User getUser(@ApiParam(name="id",value="账号")@RequestParam("id") Long id);

    @ApiOperation(nickname = "删除用户",value="根据用户信息删除用户",httpMethod = "POST")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    void delete(@ApiParam(name="user",value="用户")@RequestParam("user")User user);
}
