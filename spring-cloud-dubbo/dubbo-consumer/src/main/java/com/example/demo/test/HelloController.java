package com.example.demo.test;

import com.example.dubboapi.service.HelloService;
import com.example.dubboapi.service.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "测试")
@RestController
public class HelloController {
    @DubboReference
    private HelloService helloService;

//    @GetMapping("/hello")
//    public String hello() {
//        return helloService.hello("Dubbo!");
//    }
    @ApiOperation(value = "测试",notes = "dubbo-mybatisPlus")
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public void Test(){
        User user=new User();
        user.setId("6");
        user.setNickName("zhaolinhai");
        user.setAge("22");
        helloService.hello(user);
    }

}
