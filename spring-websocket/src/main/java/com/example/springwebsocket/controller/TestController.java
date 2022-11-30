package com.example.springwebsocket.controller;

import com.example.springwebsocket.test.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/webSocket")
public class TestController {

    @Autowired
    private WebSocket webSocket;
    @PostMapping("/sentMessage")
    public void sentMessage(String userId,String message){
        try {
            webSocket.sendMessageByUserId(userId,message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

