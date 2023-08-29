package com.example.streamrabbitmqconsumer.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.example.streamrabbitmqconsumer.bean.DemoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

//@EnableBinding(Sink.class)
@EnableBinding(MessageCha.class)
@Component
public class MessageListener {

    @Autowired
    private  AuditService auditService;

    @StreamListener(MessageCha.MYINPUT)
    public void input(String message){
        auditService.insertLog(JSONObject.parseArray(message, DemoDto.class));
        System.out.println("获取到的消息是："+message);
    }
}
