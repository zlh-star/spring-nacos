package com.example.rocketmqproducer.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rocketmqproducer.dto.RocTran;

import com.example.rocketmqproducer.dto.RocketMqTransactionLogMapper;
import com.example.rocketmqproducer.service.rocketMqTransactionLogService;
import org.springframework.stereotype.Service;

@Service
public class rocketMqTransactionLogSerIml extends ServiceImpl<RocketMqTransactionLogMapper, RocTran> implements rocketMqTransactionLogService {
}
