package com.example.springbootoauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Oauth2")
@RequestMapping("/oauth")
@RestController
public class testController {

    @ApiOperation(value = "",tags = "")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Object test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @ApiOperation(value = "",tags = "")
    @RequestMapping(value = "/token",method = RequestMethod.GET)
    public Object token(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
