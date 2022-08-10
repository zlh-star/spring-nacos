package com.example.springbooteasyexcel.dao;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springbooteasyexcel.serviceImpl.GenderConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user3")
public class User {

    /**
     * EasyExcel使用：导出时忽略该字段
     */
    @ExcelIgnore
    @TableId(value = "id")
    private Integer id;

    @ExcelProperty("用户名")
    @ColumnWidth(20)
    @TableField(value = "username")
    private String username;

    /**
     * EasyExcel使用：日期的格式化
     */
    @ColumnWidth(20)
    @ExcelProperty("出生日期")
    @DateTimeFormat("yyyy-MM-dd")
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * EasyExcel使用：自定义转换器
     */
    @ColumnWidth(10)
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    @TableField(value = "gender")
    private Integer gender;
}
