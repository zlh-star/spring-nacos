package com.example.springbootrabbitmq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SimpleSend {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    public Object send() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "Hello Spring Boot " + simpleDateFormat.format(new Date());
        amqpTemplate.convertAndSend("simple", message);
        logger.info("消息推送成功！");
        return message;
    }

    public Object send(int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "Hello Spring Boot " +i+ simpleDateFormat.format(new Date());
        amqpTemplate.convertAndSend("simpleOneToMany", message);
        logger.info("消息推送成功！");
        return message;
    }

    public Object send1(int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "Hello Spring Boot " +i+ simpleDateFormat.format(new Date());
        amqpTemplate.convertAndSend("simpleOneToMany", message);
        logger.info("推送成功！");
        return message;
    }
}
