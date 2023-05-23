package com.example.streamrocketmqproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


//@EnableBinding(Source.class)
@EnableBinding(MessageCha.class)
@Component
public class StreamTest  {

    @Autowired
    @Qualifier(value = "myoutput")
    private MessageChannel myoutput;

    public void send(Object obj)  {
        myoutput.send(MessageBuilder.withPayload(obj).build());
    }
}
