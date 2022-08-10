package com.example.springeasyexcelinput.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user1")
public class UserDto {
    @TableField(value = "username")
    @ExcelProperty(value = "用户名")
    private String username;

    @TableField(value = "password")
    @ExcelProperty(value = "密码")
    private String password;

    @TableField(value = "sex")
    @ExcelProperty(value = "性别")//0：男或1：女
    private String sex;

    @TableField(value = "birth")
    @ExcelProperty(value = "生日")
    private String birth;

    @TableField(value = "email")
    @ExcelProperty(value = "邮件地址")
    private String email;
}
