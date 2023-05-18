package com.example.streamkafkaproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@EnableBinding(MessageCha.class)
@Component
public class SendService {

    @Autowired
    private MessageChannel myoutput;


    public void sendMsg(String msg){
        myoutput.send(MessageBuilder.withPayload(msg).build());
    }

}
