package com.example.springbootredis.service;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户演示",description = "参数描述")
public class User implements Serializable {
    private static final long serialVersionUID = 662692455422902539L;
    @ApiModelProperty(example = "1",notes = "用户id")
    private Long id;
    @ApiModelProperty(example = "用户名",notes ="用户name" )
    private String name;
    @ApiModelProperty(example = "用户年龄",notes = "用户age")
    private int age;

}
