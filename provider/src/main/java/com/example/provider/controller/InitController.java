package com.example.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public String init(String name){
        System.out.println("provider is ready!"+name);
        return "provider is ready!"+name;
    }
}
