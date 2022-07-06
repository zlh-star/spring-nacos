package com.example.springcloudconsumerokhttp.controller;

import com.example.springcloudconsumerokhttp.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloRemote helloRemote;

    @GetMapping("/hello")
    public String hello() {
        return helloRemote.hello();
    }
}
