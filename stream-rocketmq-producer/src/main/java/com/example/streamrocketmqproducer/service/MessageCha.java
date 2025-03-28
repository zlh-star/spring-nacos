package com.example.streamrocketmqproducer.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

//自定义消息通道
public interface MessageCha {

    /**
     * 消息生产者的配置
     */
    String MYOUTPUT="myoutput";

    @Output(MYOUTPUT)
    MessageChannel myoutput();


    /**
     * 消息消费者的配置
     */
//    String MYINPUT="myinput";
//    @Input(MYINPUT)
//    SubscribableChannel myinput();
}
