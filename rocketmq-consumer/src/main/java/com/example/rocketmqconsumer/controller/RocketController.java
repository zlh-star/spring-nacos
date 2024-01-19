package com.example.rocketmqconsumer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Api(value = "xiaofei shixin")
@RestController
public class RocketController {

//    @Autowired
//    @Qualifier("defaultMQConsumer")
//    private DefaultMQPushConsumer defaultMQPushConsumer;

    @ApiOperation(value = "/xiaofeiputong1",tags = "消费普通1")
    @RequestMapping(value = "/xiaofeiputong1",method = RequestMethod.POST)
    public void xiaofeiputong1 () throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultLitePullConsumer defaultLitePullConsumer=new DefaultLitePullConsumer("consumer_defaultLite");
        defaultLitePullConsumer.setNamesrvAddr("localhost:9876");
        defaultLitePullConsumer.subscribe("transaction_topic","*");
        defaultLitePullConsumer.start();
        try {
            List<MessageExt> messages=defaultLitePullConsumer.poll();
            messages.forEach(messageExt -> {
                String a=new String(messageExt.getBody(), StandardCharsets.UTF_8);
                System.out.println("处理消息："+a);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            defaultLitePullConsumer.shutdown();
        }

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_defaultLite");
        consumer.setNamesrvAddr("localhost:9876");
// 设置重试次数为1，快速失败
        consumer.setMaxReconsumeTimes(1);
        consumer.subscribe("transaction_topic","*");
//        List<MessageExt> messages=defaultLitePullConsumer.poll();
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
//                    int a=1/0;
                    for (MessageExt msg : msgs) {
                        System.out.println(msg.getMsgId() + "\t" + new String(msg.getBody()));
                    }
                } catch (Exception e) {
                    //重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

    @ApiOperation(value = "/xiaofeiputong",tags = "消费普通")
    @RequestMapping(value = "/xiaofeiputong",method = RequestMethod.POST)
    public void xiaofeiputong () throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_grp_01");
        consumer.setNamesrvAddr("localhost:9876");
// 设置重试次数为1，快速失败
        consumer.setMaxReconsumeTimes(1);
        consumer.subscribe("tp_demo_01", "*");
// 消费消息
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    int a=1/0;
                    for (MessageExt msg : msgs) {
                        System.out.println(msg.getMsgId() + "\t" + new String(msg.getBody()));
                    }
                } catch (Exception e) {
                    //重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
// 启动消费者
        consumer.start();
    }

    @ApiOperation(value = "/xiaofeisixin",tags = "消费死信")
    @RequestMapping(value = "/xiaofeisixin",method = RequestMethod.POST)
    public void xiaofeisixin () throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_dlq_grp_01");
        consumer.setNamesrvAddr("localhost:9876");
// 指定死信队列主题：%DLQ%+消费组ID
        consumer.subscribe("%DLQ%consumer_grp_01", "*");
// 消费消息
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(msg.getMsgId() + "\t" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
    }

}
