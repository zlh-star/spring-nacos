package com.example.kafkaconsumer.controller;

import io.swagger.annotations.Api;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "consumer")
@RestController
public class ConsumerContro {

    @KafkaListener(topics = "Test", groupId = "TestGroup")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack){
        try {
//            int a=1/0;
            System.out.println("KafkaConsumer 收到的值为：" + record.value());
            System.out.println(record);
            ack.acknowledge();
        } catch (Exception e) {
            ack.acknowledge();
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "Test.DLT", groupId = "TestGroup.DLT")
    public void deadOnMessage(ConsumerRecord<?, ?> record, Acknowledgment ack){
        System.out.println("死信消息为：" + record.value());
        System.out.println(record);
        ack.acknowledge();
    }

//    @KafkaListener(topics = "Test", containerFactory = "myKafkaListenerContainerFactory")
//    public void message(ConsumerRecord<?, ?> record, Acknowledgment ack){
//        System.out.println("消息为：" + record.value());
//        System.out.println(record);
//        ack.acknowledge();
//    }
}
