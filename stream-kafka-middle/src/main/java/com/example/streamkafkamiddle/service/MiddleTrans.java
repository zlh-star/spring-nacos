package com.example.streamkafkamiddle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(MessageCha.class)
public class MiddleTrans {

//    @Autowired
//    private MessageChannel myoutput;

    @StreamListener(MessageCha.MYINPUT)
    @SendTo(MessageCha.MyOUTPUT)
    public Object transform(Message<String> message){
        System.out.println("消息中转站："+message);
        return message;
    }
}
