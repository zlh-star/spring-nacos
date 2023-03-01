package com.example.demo.test;


import com.example.dubboapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "测试")
@RestController
@Slf4j
public class HelloController {
    @DubboReference
    private HelloService helloService;

    @DubboReference
    private UserService userService;

    @DubboReference
    private MonoService monoService;

//    @Autowired
//    MongoMapper mongoMapper;

//    @GetMapping("/hello")
//    public String hello() {
//        return helloService.hello("Dubbo!");
//    }

    @ApiOperation(value = "测试",tags = "Mono改进")
    @RequestMapping(value = "/mono",method = RequestMethod.POST)
    public void Mono(@RequestParam String id){
       monoService.findPersonByName(id);
    }

    @ApiOperation(value = "测试",tags = "改进")
    @RequestMapping(value = "/approve",method = RequestMethod.POST)
    public void approve(){
        User user=new User();
        user.setId("11");
        user.setNickName("asdfgh");
        user.setAge("123456");
        userService.saveUser(user);
    }

    @ApiOperation(value = "测试",notes = "dubbo-mybatisPlus")
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public void Test(){
        User user=new User();
        user.setId("10");
        user.setNickName("小海");
        user.setAge("22");
        helloService.hello(user);
    }

//    @ApiOperation(value = "测试Mongo",notes = "dubbo-mybatisPlus-Mongo")
//    @RequestMapping(value = "/insert",method = RequestMethod.POST)
//    public Mono<Person> Insert(@RequestBody Person person){
//     return helloService.save(person);
////         helloService.save(person);
//    }

}
