package com.example.springbootwebfluxmongodb.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@ApiModel(value = "用户演示",description = "请求参数")
public class User {
    @Id
    @ApiModelProperty(example = "1",notes = "用户ID")
    private String id;

    @Indexed(unique = true)
    @ApiModelProperty(example = "zhaolinhai",notes = "用户名")
    private String name;
    @ApiModelProperty(example = "20",notes = "用户年龄")
    private String age;
}
