//package com.example.streamconsumer.controller;
//
//import com.example.streamconsumer.service.MessageListener;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@Api(value = "消费")
//@RestController
//public class consumerController {
//
//    @Resource
//    private MessageListener messageListener;
//
//    @ApiOperation(value = "消费",tags = "consumer")
//    @RequestMapping(value = "/consumer",method = RequestMethod.GET)
//    public void consumer(){
//        messageListener.input();
//    }
//}
