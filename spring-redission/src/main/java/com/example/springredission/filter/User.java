package com.example.springredission.filter;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "user3")
public class User {

    @TableId(value = "userId")
    private String userId;

    @TableField(value = "userName")
    private String userName;

    @TableField(value = "token")
    private String token;

    @TableField(value = "password")
    private String password;

    private String kickout;

    public String getPassword() {
        return password;
    }

    public String getKickout() {
        return kickout;
    }

    public void setKickout(String kicout) {
        this.kickout = kicout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

