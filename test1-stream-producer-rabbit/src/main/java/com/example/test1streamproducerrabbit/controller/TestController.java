package com.example.test1streamproducerrabbit.controller;

//import com.example.streamrabbitmqproducer.bean.DemoDto;

import com.example.test1streamproducerrabbit.service.StreamTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "测试")
@RestController
public class TestController {

    @Autowired
    private StreamTest streamTest;

    @ApiOperation(value = "测试",tags ="producer" )
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void Test(@RequestParam("message") String message) {
        streamTest.send(message);
    }

//    @ApiOperation(value = "计入es",tags ="记录" )
//    @RequestMapping(value = "/insert",method = RequestMethod.POST)
//    public void insert(@RequestBody DemoDto demoDto) {
//        streamTest.send(JSONArray.of(demoDto).toString());
//    }
}
