package com.example.springboottest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("account")
@Document(collection = "account")
@ApiModel(value = "用户演示类",description = "请求参数类")
public class UserModel {
    @Id
    @ApiModelProperty(example = "123",notes = "账号Id")
    @TableId(value = "accountId")
    private String accountId;

    @ApiModelProperty(example = "zhaolinhai",notes = "用户名")
    @TableField("accountName")
    private String accountName;

    @ApiModelProperty(example = "123456",notes = "账号密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(example = "2022-04-26",notes = "创建时间")
    @TableField("createData")
    private Date createData;

}
