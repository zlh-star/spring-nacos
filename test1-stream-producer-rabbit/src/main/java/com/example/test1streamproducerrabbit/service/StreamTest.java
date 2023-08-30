package com.example.test1streamproducerrabbit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


//@EnableBinding(Source.class)
//@EnableBinding(MessageCha.class)
@Component
public class StreamTest  {

    @Value("${bingder.name}")
    private String binderName;

    @Autowired
    private StreamBridge streamBridge;
//    @Qualifier("myoutput")
//    private MessageChannel output;

    public void send(Object obj)  {
        streamBridge.send(binderName,MessageBuilder.withPayload(obj).build());
    }
}
