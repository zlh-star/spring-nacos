package com.example.provider2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class initController {
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public String init(String name){
        System.out.println("provider2 is ready!"+name);
        return "provider2 is ready!"+name;

    }
}
