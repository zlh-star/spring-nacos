package com.example.streamrocketmqproducer.controller;

import com.example.streamrocketmqproducer.service.StreamTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @ApiOperation(value = "测试",tags ="producer" )
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void Test(@RequestParam("message") String message) {
        streamTest.send(message);
    }

    @ApiOperation(value = "test1",tags ="producer1" )
    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public void Test1(@RequestParam("message") String message) {
        rocketMQTemplate.syncSend("exchange3",message);
    }
}
