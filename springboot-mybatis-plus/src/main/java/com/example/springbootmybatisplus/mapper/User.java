package com.example.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user2")
public class User {
    @TableId(value = "id")
    private String id;

    @TableField("nickName")
    private String nickName;

    @TableField("age")
    private String age;

}
