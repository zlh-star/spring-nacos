package com.example.rocketmqproducer.test;

import com.example.rocketmqproducer.dto.Account;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ProducerDemo {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 发送消息的实例
    public void sendMessage(String topic, String msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }

    //消息发出之后，在接收方确认之后，才会发送下一条信息
    public void syncSendMessage(String topic, List<String> msg){
            rocketMQTemplate.syncSend(topic,msg);
    }

    public void AsyncSendMessage(String topic,List<String>msg){
            rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable);
                }
            });
    }

    //单向发送，只发送不需要消费者应答
    public void SendOneMessage(String topic,String msg){
        rocketMQTemplate.sendOneWay(topic,msg);
    }

    public void DelaySendMessage(String topic,String msg){
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msg).build(),1000,3);
    }

    public void RocketTx(Account account){
        rocketMQTemplate.sendMessageInTransaction("consumer-topic",
                MessageBuilder.withPayload(account)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
                        .setHeader("my_data", account)
                        .build(), account
        );
    }
}

