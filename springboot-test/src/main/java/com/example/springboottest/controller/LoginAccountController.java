package com.example.springboottest.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Api(value = "用户登录、注册")
public class LoginAccountController {

    @GetMapping("/login")
    @ApiModelProperty(value = "登录",notes ="登陆界面" )
    @ApiOperation(value = "登录",notes ="登陆界面" )
    public ModelAndView login(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/register")
    @ApiModelProperty(value = "注册界面面",notes ="注册中心")
    @ApiOperation(value = "注册界面面",notes ="注册中心")
    public ModelAndView register(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }
}
