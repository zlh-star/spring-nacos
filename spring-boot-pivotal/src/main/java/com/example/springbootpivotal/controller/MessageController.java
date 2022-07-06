package com.example.springbootpivotal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(value = "ceshi")
public class MessageController {

    @GetMapping("/hello")
    @ApiOperation(value = "ceshi")
    @ApiModelProperty("ceshi")
    public String getMessage(@RequestParam(value = "name") String name){
            String rsp = "Hi " + name + " : responded on - " + new Date();
            System.out.println(rsp);
            return rsp;
    }

}
