package com.example.springbootzidian.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springbootzidian.dic.RedisDicUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;



import java.util.HashMap;
import java.util.Map;

@Data
@TableName("sys_dict")
//@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {



    private static final long serialVersionUID = 1L;
    //解决mybatis-plus和easyExcel的冲突问题
    @JsonIgnoreProperties(ignoreUnknown = true)
    @TableField(exist = false)
    private Map<Integer,CellStyle> cellStyleMap = new HashMap<Integer, CellStyle>();


    @TableId(type = IdType.UUID)
    private String id; //主键

    private Integer sex;//性别


    @ExcelProperty(value = "性别（必填）", index = 5)
    @TableField(exist = false)
    private String sexStr; //用于展示性别
    /**
     * 进行数字字典的替换
     * @return
     */
    public String getSexStr() {

        return RedisDicUtil.transForm("sex",this.sex);
    }
}
