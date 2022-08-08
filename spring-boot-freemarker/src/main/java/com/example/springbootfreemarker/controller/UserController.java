package com.example.springbootfreemarker.controller;

import com.example.springbootfreemarker.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/index")
    public String index(Model model){
        User user=new User();
        user.setName("zhaolinhai");
        user.setAge("22");
        user.setEmail("2248416786@@139.com");
        model.addAttribute("user",user);
        return "index";
    }
}
