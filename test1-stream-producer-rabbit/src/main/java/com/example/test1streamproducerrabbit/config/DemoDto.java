package com.example.test1streamproducerrabbit.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "test1")
@ApiModel(value = "实体类")
public class DemoDto {

    @Field(type= FieldType.Keyword,index = false)
    @ApiModelProperty(value = "id",notes = "账号")
    private String id;
    // 整个name不被分词，切不创建索引
    // Keyword表示不被分词
    @Field(type= FieldType.Keyword,index = false)
    @ApiModelProperty(value = "name",notes = "用户名")
    private String name;

    @Field(type= FieldType.Keyword,index = false)
    @ApiModelProperty(value = "name1",notes = "姓名")
    private String name1;

    @Field(type= FieldType.Keyword,index = false)
    @ApiModelProperty(value = "date",notes = "日期")
    private Date date;

    @Field(type= FieldType.Keyword,index = false)
    private int pageNo;

    @Field(type= FieldType.Keyword,index = false)
    private int pageSize;

    /**
     * 登录时间
     */
    @ApiModelProperty(value="登录时间", name="loginTime", dataType="Date",example="yyyy-MM-dd HH:mm:ss")
    @Field(type= FieldType.Keyword,index = false)
    private String loginTime;
    /**
     * 登出时间
     */
    @ApiModelProperty(value="登出时间", name="logoffTime" , dataType="Date",example="yyyy-MM-dd HH:mm:ss")
    @Field(type= FieldType.Keyword,index = false)
    private String logoffTime;


//    public DemoDto(long l, String s, String 体育, Date date) {
//    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoffTime() {
        return logoffTime;
    }

    public void setLogoffTime(String logoffTime) {
        this.logoffTime = logoffTime;
    }

    public String getId() {
        return id;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
