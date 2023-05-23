package com.example.streamrocketmqconsumer.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

//@EnableBinding(Sink.class)
@EnableBinding(MessageCha.class)
@Component
public class MessageListener {

    @StreamListener(MessageCha.MYINPUT)
    public void input(String message){
        System.out.println("获取到的消息是："+message);
    }
}
