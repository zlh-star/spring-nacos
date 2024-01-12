package com.example.streamrabbitmqconsumer.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.example.streamrabbitmqconsumer.bean.DemoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//@EnableBinding(Sink.class)
@EnableBinding(MessageCha.class)
@Component
public class MessageListener {

    @Autowired
    private  AuditService auditService;

    @StreamListener(MessageCha.MYINPUT)
    public void input(Message<Object> message){
        List<Object> list=new ArrayList<>();
        Object payload = message.getPayload();
        String a=new String((byte[]) payload, StandardCharsets.UTF_8);
        list.add(a);
        auditService.insertLog(list);
        System.out.println("获取到的消息是："+message);
    }

    @RabbitListener(queues = "exchange2.group1.dlq")
    public void deadinput(Message<Object> message){
        // messages=new String((message.getPayload()).getBytes(), StandardCharsets.UTF_8);
        Object payload = message.getPayload();
        String a=new String((byte[]) payload, StandardCharsets.UTF_8);
        System.out.println("消费死信消息:"+ a);
    }
}
