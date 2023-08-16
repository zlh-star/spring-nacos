package com.example.springcloudzuul.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "test")
//@Tag(name = "Spring Boot3",description = "测试")
@RestController
public class TestController {

    @ApiOperation(value = "/test",tags = "测试")
//   @ApiResponse(description = "测试", content = @Content(mediaType = "application/json"))
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(){
        System.out.println("success");
    }
}
