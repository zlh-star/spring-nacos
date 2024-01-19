package com.example.rocketmqproducer.test;

import com.alibaba.fastjson.JSON;
import com.example.rocketmqproducer.dto.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Api(value = "RocketMq-Test")
@RestController
public class TestController {
    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Autowired
    private ProducerDemo producerDemo;

    @ApiOperation(value = "/shengchan1",tags = "生产1")
    @RequestMapping(value = "/shengchan1",method = RequestMethod.POST)
    public void shengchan1() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
//        DefaultMQProducer producer = new DefaultMQProducer("producer_transaction");
        TransactionMQProducer producer=new TransactionMQProducer("producer_transaction");
        producer.setNamesrvAddr("localhost:9876");

        producer.start();
        Message message = new Message("transaction_topic", "zhaolinhai".getBytes(StandardCharsets.UTF_8));

        producer.send(message);
        producer.shutdown();
    }

    @ApiOperation(value = "/shengchan",tags = "生产")
    @RequestMapping(value = "/shengchan",method = RequestMethod.POST)
    public void shengchan () throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer_grp_01");
// 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
// 启动生产者
        producer.start();
// 发送消息
        Message message = new Message("tp_demo_01", "zhaolinhai".getBytes(StandardCharsets.UTF_8));
        producer.send(message);
// 关闭生产者
        producer.shutdown();
    }

    @ApiOperation(value = "test",tags = "发送消息")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(){
        String msg = UUID.randomUUID().toString();
        producerDemo.sendMessage(topic , msg);
    }

    @ApiOperation(value = "sync",tags = "同步发送")
    @RequestMapping(value = "/sync",method = RequestMethod.POST)
    public void sync(){
        List<String> list=new ArrayList<>();
        String msg1="zhaolinhai";
        String msg = UUID.randomUUID().toString();
        list.add(msg);
        list.add(msg1);
        producerDemo.syncSendMessage(topic , list);
    }

    @ApiOperation(value = "Async",tags = "异步发送")
    @RequestMapping(value = "/Async",method = RequestMethod.POST)
    public void Async(){
        List<String> list=new ArrayList<>();
        String msg1="zhaolinhai";
        String msg = UUID.randomUUID().toString();
        list.add(msg);
        list.add(msg1);
        producerDemo.AsyncSendMessage(topic , list);
    }

    @ApiOperation(value = "SendOneWay",tags = "单项发送")
    @RequestMapping(value = "/SendOneWay",method = RequestMethod.POST)
    public void SendOneWay(){
        String msg = UUID.randomUUID().toString();
        producerDemo.SendOneMessage(topic,msg);
    }

    @ApiOperation(value = "delaySend",tags = "延时发送")
    @RequestMapping(value = "/delaySend",method = RequestMethod.POST)
    public void delaySend(){
        String msg = UUID.randomUUID().toString();
        producerDemo.DelaySendMessage(topic,msg);
    }


    @ApiOperation(value = "testRocketTx",tags = "分布式")
    @RequestMapping(value = "/testRocketTx",method = RequestMethod.POST)
    public void testRocketTx() {
        try {
            Account account=new Account();
            account.setId("1");
            account.setNickName("zhaolinhai");
            account.setAge("24");
            //判断该条消息是否可以进行消费，否则回滚删除
            producerDemo.RocketTx(account);
            List<String> mes=new ArrayList<>();
            mes.add(JSON.toJSONString(account));
            //消费该消息
            producerDemo.AsyncSendMessage(topic,mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
