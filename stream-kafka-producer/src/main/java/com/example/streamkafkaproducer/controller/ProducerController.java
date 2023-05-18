package com.example.streamkafkaproducer.controller;

import com.example.streamkafkaproducer.service.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cong 2018/5/28
 */
@Api(value = "kafka-producer")
@RestController
public class ProducerController {


    @Autowired
    private SendService sendService;


    @RequestMapping(value = "/send/{msg}",method = RequestMethod.POST)
    @ApiOperation(value = "/send",tags = "kafka-生产")
    public void send(@PathVariable("msg") String msg){
        sendService.sendMsg(msg);
    }
}
