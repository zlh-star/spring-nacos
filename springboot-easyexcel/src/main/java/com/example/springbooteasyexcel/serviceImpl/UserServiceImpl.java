package com.example.springbooteasyexcel.serviceImpl;

import com.example.springbooteasyexcel.dao.User;
import com.example.springbooteasyexcel.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("Uservice")
public class UserServiceImpl implements Uservice {

    @Override
    public List<User> getAllUser() {
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setId(1);
        user.setUsername("zabrina");
//        Date date=new Date();
        user.setBirthday(getDate(2022,8,1));
        user.setGender(1);
        userList.add(user);
        return userList;
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
