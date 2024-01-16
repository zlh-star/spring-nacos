package com.example.streamrocketmqconsumer.service;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "exchange3", topic = "group1")
public class RocketMqMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
//        String msg=new String(message.getBody());
        System.out.println(message);
    }
}
