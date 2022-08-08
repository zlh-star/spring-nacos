package com.example.springboot.dao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test")
public class UserModel {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    private String id;
    @Column(nullable = true, unique = true)
    private String nickName;
    @Column(nullable = false)
    private int age;
}