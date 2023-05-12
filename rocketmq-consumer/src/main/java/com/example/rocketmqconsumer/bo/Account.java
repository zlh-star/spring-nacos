package com.example.rocketmqconsumer.bo;

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
@TableName("user2")
@Builder
public class Account {

    @TableId(value = "id")
    private String id;

    @TableField("nickName")
    private String nickName;

    @TableField("age")
    private String age;
}
