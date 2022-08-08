package com.example.springboottest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student")
public class Student {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private Integer sex;

    private Integer classId;

}
