package com.example.springredission.test;

import com.example.springredission.dao.UserMapper;
import com.example.springredission.filter.JWTUtils;
import com.example.springredission.filter.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseUtils {

    public static String getToken(){
        User user=new User();
        user.setUserId("1");
        user.setUserName("zlh");
        user.setPassword("1");
        return JWTUtils.createToken(user);
    }

    public static String getCurrentUserId(){
        return "1";
    }
}
