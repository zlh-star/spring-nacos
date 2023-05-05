package com.example.ealen.controller;

import com.example.ealen.config.Response;
import com.example.ealen.config.Result;
import com.example.ealen.domain.entity.OauthAccount;
import com.example.ealen.service.OauthAccountService;
import com.example.ealen.service.impl.OauthAccountUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "Oauth2")
@RequestMapping("/oauth1")
@RestController
public class Oauth2Controller {

    @Autowired
    OauthAccountService accountService;


    @Qualifier("oauthAccountUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @ApiOperation(value = "",tags ="" )
    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public Response<Object> oauth(@RequestBody OauthAccount oauthAccount){
//        OauthAccount oauthAccount=new OauthAccount();
//        oauthAccount.setClientId("qwe");
//        oauthAccount.setPassword("123456");
//        oauthAccount.setCreatedTime(new Date());
//        oauthAccount.setUsername(username);
        userDetailsService.loadUserByUsername(oauthAccount.getUsername());
        return Result.wrapResult(0);
    }
}
