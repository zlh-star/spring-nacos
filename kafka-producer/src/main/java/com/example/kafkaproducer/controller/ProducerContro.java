package com.example.kafkaproducer.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "producer")
@RestController
public class ProducerContro {

    private final static String TOPIC_NAME = "Test";     // topic 的名称

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    // 发送 kafka 消息
    @GetMapping("/kafka/send/{message}")
    public String sendMessage(@PathVariable String message){
        kafkaTemplate.send(TOPIC_NAME, "key", message);
        return "OK";
    }
}
