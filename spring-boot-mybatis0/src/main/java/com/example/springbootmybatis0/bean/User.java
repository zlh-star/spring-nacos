package com.example.springbootmybatis0.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Documented;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户演示类",description = "请求参数类")
public class User {

    @ApiModelProperty(example = "1",notes = "用户Id")
    private String id;

    @ApiModelProperty(example = "zhaolinhai",notes = "用户名")
    private String myName;

    @ApiModelProperty(example = "20",notes = "用户年龄")
    private String age;
}
