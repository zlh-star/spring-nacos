package com.example.testwebsocket.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user2")
@Document(indexName = "test2")
public class User {
    @TableId(value = "id")
    @Field(type= FieldType.Keyword,index = false)
    private String id;

    @TableField("nickName")
    @Field(type= FieldType.Keyword,index = false)
    private String nickName;

    @TableField("age")
    @Field(type= FieldType.Keyword,index = false)
    private String age;

}
