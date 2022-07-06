package com.example.springboot.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccAllotPassBo implements Serializable {
    private String account;
    private String password;
    private String id;
    private String shortname;
    private String fullname;
}
