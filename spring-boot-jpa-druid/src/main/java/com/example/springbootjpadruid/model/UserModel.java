package com.example.springbootjpadruid.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test1")   //数据库表名
public class UserModel {
    @Id  //主键字段
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", nullable = false, length = 36)
    private String id;
    @Column(nullable = true, unique = true)
    private String nickName;
    @Column(nullable = false)
    private int age;
}
