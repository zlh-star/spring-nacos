package com.example.springasync.controller;

import com.example.springasync.mapper.User;
import com.example.springasync.service.TestService;
import com.example.springasync.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ceshi")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "test",tags = "测试")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public Object test(){

        String a="zhaolinhai";
        testService.test(a);
        System.out.println("测试结果："+userService.save(User.builder()
                        .id("3")
                        .nickName("zhaoweilin")
                        .age("20")
                .build()));
        System.out.println("控制器："+a);
        return "success";
    }
}
