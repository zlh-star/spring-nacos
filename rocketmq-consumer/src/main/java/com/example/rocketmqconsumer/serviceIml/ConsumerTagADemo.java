package com.example.rocketmqconsumer.serviceIml;

import com.example.rocketmqconsumer.bo.Account;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@RocketMQMessageListener(topic = "${rocketmq.consumer.topic}",consumerGroup ="${rocketmq.consumer.group}")
@Component
@Slf4j
public class ConsumerTagADemo implements RocketMQListener<Account> {

    public static final String DATE_AND_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss" ;

//    @Override
//    public void onMessage(String o) {
//        Date start = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_AND_TIME_FORMAT);
//        String formatDate = dateFormat.format(start);
//        System.out.println("time"+formatDate);
//        System.out.println("ConsumerTagADemo onMessage="+o);
//    }

    @Override
    public void onMessage(Account account) {
        Date start = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_AND_TIME_FORMAT);
        String formatDate = dateFormat.format(start);
        log.info("success");
        System.out.println("time"+formatDate);
        System.out.println("ConsumerTagADemo onMessage="+account);
    }
}

