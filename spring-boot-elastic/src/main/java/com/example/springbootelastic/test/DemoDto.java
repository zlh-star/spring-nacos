package com.example.springbootelastic.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "test")
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

    public String getId() {
        return id;
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
