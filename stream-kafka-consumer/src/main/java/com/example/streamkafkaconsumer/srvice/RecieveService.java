package com.example.streamkafkaconsumer.srvice;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;

@EnableBinding(MessageCha.class)
public class RecieveService {

//    @StreamListener(MessageCha.MYINPUT)
//    public void recieve(Object payload){
//        System.out.println("consumer接收到的消息为:"+payload);
//    }

    @StreamListener(MessageCha.MYINPUT)
    public void recieve(Message<Object> message){
        int b=1/0;
        Object payload=message.getPayload();
        String a=new String((byte[]) payload, StandardCharsets.UTF_8);
        System.out.println("consumer接收到的消息为:"+a);
    }

    @KafkaListener(groupId = "consumer-group",topics = "kafka-consumer-dlq")
    public void deadRecieve(Message<Object> message){
        Object payload=message.getPayload();
        String a=new String((byte[]) payload, StandardCharsets.UTF_8);
        System.out.println("死信消息为:"+a);
    }

}
