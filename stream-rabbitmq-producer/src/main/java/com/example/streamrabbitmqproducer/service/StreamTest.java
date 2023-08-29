package com.example.streamrabbitmqproducer.service;

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
    private MessageChannel output;

    public void send(Object obj)  {
        output.send(MessageBuilder.withPayload(obj).build());
    }
}
