package com.example.demo.test;


import com.example.dubboapi.service.HelloService;
import com.example.dubboapi.service.Person;
import com.example.dubboapi.service.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Api(value = "测试")
@RestController
public class HelloController {
    @DubboReference
    private HelloService helloService;

//    @Autowired
//    MongoMapper mongoMapper;

//    @GetMapping("/hello")
//    public String hello() {
//        return helloService.hello("Dubbo!");
//    }
    @ApiOperation(value = "测试",notes = "dubbo-mybatisPlus")
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public void Test(){
        User user=new User();
        user.setId("9");
        user.setNickName("小海");
        user.setAge("22");
        helloService.hello(user);
    }

    @ApiOperation(value = "测试Mongo",notes = "dubbo-mybatisPlus-Mongo")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Mono<Person> Insert(Person person){
     return helloService.save(person);
//         helloService.save(person);
    }

}
