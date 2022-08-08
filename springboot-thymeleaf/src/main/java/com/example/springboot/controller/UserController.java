package com.example.springboot.controller;


import com.example.springboot.dao.UserModel;
import com.example.springboot.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user1")
    public List<UserModel> user(){
        return userRepository.findAll(Sort.by("id").descending());
    }

     @PostMapping("/user2")
    public UserModel user(UserModel user){
        return userRepository.save(user);
     }

     @DeleteMapping("/user3")
    public String deleteUserById(String id){
        userRepository.deleteById(id);
        return "success";
     }

}
