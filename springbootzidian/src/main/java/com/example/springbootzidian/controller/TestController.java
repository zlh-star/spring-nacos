package com.example.springbootzidian.controller;

import com.example.springbootzidian.pojo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "数据字典")
@RestController
public class TestController {

    @ApiOperation(value = "字典",notes = "性别")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(){
        UserInfo userInfo=new UserInfo();
        userInfo.getSexStr();
    }
}
