package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.simple.SimpleSend;
import com.rabbitmq.client.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Api(value = "rabbitmq")
@RestController
@RequestMapping("/rabbitMQ")
public class RabbitmqController {

    @Autowired
    private SimpleSend simpleSend;

//    @ApiOperation(value = "测试",tags = "测试rabbitmq")
//    @RequestMapping(value = "test",method = RequestMethod.POST)
//    public Object testRabbitmq(){
//        for(int i=0;i<=10;i++){
//             simpleSend.send1(i);
//             simpleSend.send(i);
//        }
//        return 0;
//    }

    @ApiOperation(value = "发送",tags = "rabbitmq发送")
    @RequestMapping(value = "test",method = RequestMethod.POST)
    public void testRabbit(){
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("rabbitmq");
        factory.setPassword("rabbitmq");

        Connection connection=null;
        Channel channel=null;
        try {
            connection=factory.newConnection();
            channel=connection.createChannel();
            channel.queueDeclare("transactionQueue",true,
                    false,false,null);
            /**创建交换机
             * */
            channel.exchangeDeclare("directTransactionExchange",
                    "direct",true);
            /**将交换机和队列进行绑定
             * */
            channel.queueBind("transactionQueue",
                    "directTransactionExchange","transactionRoutingKey");
            /**发送消息
             * */
            String message="赵林海进行事务测试消息";
            /**启动事务，启动事务以后所有写入队列
             * 中的消息，必须显示调用事务的txCommit()函数
             * 来提交事务获取txRollback()回滚事务
             * */
            /**开启事务
             * */
            channel.txSelect();
            /**发送消息到队列
             * */
            channel.basicPublish("directTransactionExchange",
                    "transactionRoutingKey",null,
                    message.getBytes("utf-8"));
            /**提交事务
             * */
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally{
            if(channel!=null){
                try {
                    /**回滚事务
                     * */
                    channel.txRollback();
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "接收",tags = "rabbitmq接收")
    @RequestMapping(value = "/rabbitmq",method = RequestMethod.POST)
    public void testRabbitmq(){
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("rabbitmq");
        factory.setPassword("rabbitmq");
        Connection connection=null;
        Channel channel=null;
        try {
            connection=factory.newConnection();
            channel=connection.createChannel();
            channel.queueDeclare("transactionQueue",true,
                    false,false,null);
            /**创建交换机
             * */
            channel.exchangeDeclare("directTransactionExchange",
                    "direct",true);
            /**将交换机和队列进行绑定
             * */
            channel.queueBind("transactionQueue",
                    "directTransactionExchange","transactionRoutingKey");
            /**开启事务
             * 当消费者开启事务后，即使不作为事务的
             * 提交，那么依然可以获取队列中的事务的消息
             * 并且消息从队列中移除掉
             * 注意:
             * 此时的事务暂时对消息的接收者没有影响
             * */
            channel.txSelect();
            /**获取消息
             * */
            channel.basicConsume("transactionQueue",
                    true,"",
                    new DefaultConsumer(channel){
                        /**获取队列中消息函数
                         * */
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body) throws IOException {
                            String message =new String(body);
                            System.out.println(message);
                        }
                    });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
