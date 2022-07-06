package com.springboot.springbootjpahikari.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "实体类")
@Table(name = "test")   //数据库表名
public class UserModel {
    @Id  //主键字段
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", nullable = false, length = 36)
    @ApiModelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "")
    @Column(nullable = true, unique = true)
    private String nickName;

    @ApiModelProperty(value = "")
    @Column(nullable = false)
    private int age;
}
