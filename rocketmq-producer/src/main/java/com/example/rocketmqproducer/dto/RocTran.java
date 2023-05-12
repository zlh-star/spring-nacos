package com.example.rocketmqproducer.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rocketmq_transaction")
@Builder
public class RocTran {

    @TableId(value = "id")
    private String id;

    @TableField("transaction_id")
    private String transactionId;

    @TableField("log")
    private String log;
}
