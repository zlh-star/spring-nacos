package com.example.springbootredis.controller;

import com.example.springbootredis.service.User;
import com.example.springbootredis.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@RestController
@Slf4j
@Api(value = "用户管理测试")//协议集描述
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate<String, Serializable> redisCacheTemplate;
//    @Autowired
//    UserService userService;
    @GetMapping("/test")
    @ApiOperation(value = "获取对象",notes = "获取当前对象",tags = "根据redis缓存获取当前的对象")
    public void test() {
        stringRedisTemplate.opsForValue().set("baidu", "https://www.baidu.com/");

        log.info("当前获取对象：{}",stringRedisTemplate.opsForValue().get("baidu"));

        redisCacheTemplate.opsForValue().set("baidu.com", new User(1L, "baidu", 18));

        User user = (User) redisCacheTemplate.opsForValue().get("baidu.com");

        log.info("当前获取对象：{}", user);
    }
    @GetMapping("/test1")
    @ApiOperation(value ="保存用户" ,notes ="根据当前用户的所有信息进行保存",tags = "进行用户保存")
    public User test1(User user){
        return userService.save(user);
    }


    @GetMapping("/getUrl")
    @ApiOperation(value ="获取sessionId",notes = "根据url进行获取",tags = "获取的SessionId为相关csdn")
    public String getSession(HttpServletRequest request){
        String url=(String)request.getSession().getAttribute("url");
        if(StringUtils.isEmpty(url)){
            request.getSession().setAttribute("url","https://blog.csdn.net");
        }
        log.info("获得的sessionId的内容为",request.getSession().getAttribute("url"));
        return request.getRequestedSessionId();
    }

//    @ApiOperation(value = "取",tags = "测试")
//    @RequestMapping(value = "/select",method = RequestMethod.POST)
//    public Object select(@RequestParam Long id){
//        List<User> userList= (List<User>) userService.getUser(id);
//        return userList;
//    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "删除用户",notes = "根据角色id对用户进行删除",tags = "在redis中进行保存")
    public void deleteUser(User user){

//        stringRedisTemplate.opsForValue().set("baidu", "https://www.baidu.com/");
//
//        log.info("当前获取对象：{}",stringRedisTemplate.opsForValue().get("baidu"));
//
//        redisCacheTemplate.opsForValue().set("baidu.com", new User(1L, "baidu", 18));
//
//        User user = (User) redisCacheTemplate.opsForValue().get("baidu.com");
//
//        log.info("当前获取对象：{}", user);
//        List<User> list1= (List<User>) userService.getUser(id);
        userService.delete(user);
    }

}
