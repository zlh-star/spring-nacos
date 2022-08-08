package com.example.swagger3.test;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试")
public class Test {

    @Operation(summary = "测试")
    @RequestMapping(value = "测试",method = RequestMethod.POST)
    public void test(){
        System.out.println("zhaolinhai");
    }
}
