package com.example.dubboapi.service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user2")
public class User implements Serializable {
    @TableId(value = "id")
    private String id;

    @TableField("nickName")
    private String nickName;

    @TableField("age")
    private String age;


}
