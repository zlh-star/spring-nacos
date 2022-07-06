package com.example.springswagger.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户演示类", description = "请求参数类")
@Document(collection="user")
public class User {
//    @Indexed(unique = true)
    @Id
    @ApiModelProperty(example = "1", notes = "用户ID")
    private String id;
    @ApiModelProperty(example = "geekdigging", notes = "用户名")
    private String nickName;
//    @ApiModelProperty(example = "1570689455000", notes = "创建时间")
//    private Date createDate;
    @ApiModelProperty(example = "18", notes = "用户年龄")
    private String age;
}
