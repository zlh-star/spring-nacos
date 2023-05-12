package com.example.rocketmqproducer.test;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.rocketmqproducer.dto.Account;
import com.example.rocketmqproducer.dto.RocTran;
import com.example.rocketmqproducer.dto.RocketMqTransactionLogMapper;
import com.example.rocketmqproducer.dto.UserMapper;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RocketMQTransactionListener
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {
    @Resource
    private RocketMqTransactionLogMapper rocketMqTransactionLogMapper;

    @Resource
    private UserMapper userMapper;
    /**
     * 消费成功则进行提交事务，否则进行回滚删除
     * @param msg
     * @param arg
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();
//        System.out.println(headers.get("my_data"));
        byte[] payloadByte = (byte[]) msg.getPayload();
        String payload = new String(payloadByte);
        System.out.println("payload = " + payload);

        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("transactionId = " + transactionId);

        String myData = Objects.requireNonNull(headers.get("my_data")).toString();
        Account account=JSONObject.parseObject(payload,Account.class);
        List<Account> accountList=new ArrayList<>();
        accountList.add(account);
        System.out.println("myData = " + myData);
        String argData = arg.toString();
        System.out.println("argData = " + argData);

        try {
            changeStatuswithRocketMqLog(account.getId(),accountList,transactionId);
            //可以消费该消息
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            // 继续查询该消息的状态
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    /**
     * 根据本地日志判断消息是否发送完整，是否可进行消费
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        LambdaQueryWrapper<RocTran> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RocTran::getTransactionId,transactionId);
        RocTran rocTran=rocketMqTransactionLogMapper.selectOne(queryWrapper);
        if (rocTran == null) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void changeStatuswithRocketMqLog(String id,List<Account> accountList,String transactionId){
        accountList.forEach(account -> {
            userMapper.insert(account);
        });
        //插入事务表
        rocketMqTransactionLogMapper.insert(
                RocTran.builder()
                        .id(id)
                        .transactionId(transactionId)
                        .log("执行下一步操作")
                        .build()
        );
    }
}

