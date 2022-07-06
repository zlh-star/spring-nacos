package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @RequestMapping("/init")
    public String init(String name){
        System.out.println("provider is ready!"+name);
        return "provider is ready!"+name;
    }
}
