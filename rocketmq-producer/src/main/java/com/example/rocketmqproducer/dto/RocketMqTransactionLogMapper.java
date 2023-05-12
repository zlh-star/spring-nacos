package com.example.rocketmqproducer.dto;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RocketMqTransactionLogMapper extends BaseMapper<RocTran> {
}
