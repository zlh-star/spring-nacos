package com.example.test1streamconsumerrabbit.service;

//import com.example.streamrabbitmqconsumer.bean.DemoDto;

import com.alibaba.fastjson.JSONObject;
import com.example.test1streamconsumerrabbit.config.DemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

//@EnableBinding(Sink.class)
//@EnableBinding(MessageCha.class)
@Component
@Slf4j
public class MessageListener {

    @Autowired
    private AuditService auditService;

//    @StreamListener(MessageCha.MYINPUT)
//    public void input(String message){
//        auditService.insertLog(JSONObject.parseArray(message, DemoDto.class));
//        System.out.println("获取到的消息是："+message);
//    }
    @Bean
    public Consumer<String> myinput(){
        return message-> auditService
                .insertLog(JSONObject.parseArray(message, DemoDto.class));
    }
}
