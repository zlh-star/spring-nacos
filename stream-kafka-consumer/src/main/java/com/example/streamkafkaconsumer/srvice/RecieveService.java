package com.example.streamkafkaconsumer.srvice;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageCha.class)
public class RecieveService {

    @StreamListener(MessageCha.MYINPUT)
    public void recieve(Object payload){
        System.out.println("consumer接收到的消息为:"+payload);
    }

}
