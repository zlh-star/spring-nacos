package com.example.springboot.dao.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public String username;
    public String password;
    public String sex;
    public String birth;
    public String email;
}
