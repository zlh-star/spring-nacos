package com.example.springboot.controller;

import com.example.springboot.bean.CourseModel;
import com.example.springboot.bean.IUserModel;
import com.example.springboot.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class TestController {
    @Autowired
    public UserDao userDao;
    @GetMapping("/loginsuccess")
    public String String(){
        return "login";
    }
    @GetMapping("/registersuccess")
    public String Register(){
        return "register";
    }
    @RequestMapping("/form")
    public String form(String username,String password){
//      User user=new User();
//      user.setUsername(username);
//      user.setPassword(password);
        String loginuser=userDao.loginUser(username, password);
        if("username".equals(loginuser)){
            return "detail";
        }else {
            return null;
        }

    }
    @GetMapping("/insertUser")
    public List<String> insertUser(String username,String password,String sex,String birth,String eamil){
        List<String> users=userDao.insertUser(username,password,sex,birth,eamil);
        if(CollectionUtils.isEmpty(users)){
            return Collections.EMPTY_LIST;
        }
        return users;
    }

    @GetMapping("/test")
    public String hello(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        IUserModel userModel = new IUserModel();
        userModel.setId(1L);
        userModel.setName("Spring Boot");
        userModel.setAge(18);
        map.put("user", userModel);


        List<CourseModel> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CourseModel courseMode = new CourseModel();
            courseMode.setId(i);
            courseMode.setName("Spring Boot：" + i);
            courseMode.setAddress("课程地点：" + i);
            list.add(courseMode);
        }

        map.put("list", list);
        map.put("flag", true);

        request.setAttribute("data", map);
        return "test";
    }
}
