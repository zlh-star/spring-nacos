package com.example.springbootwebfluxmongodb.controller;

import com.example.springbootwebfluxmongodb.mapper.User;
import com.example.springbootwebfluxmongodb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
@RequestMapping("/user")
@Api(value = "用户测试")
public class UserController {
    @Autowired
    private UserService userService;


    @ApiOperation(value="返回用户列表界面")
    @PostMapping(value="/userList")
    public String userList(Model model) {

        return "userList";
    }

    /**
     * 新增页面
     * @return
     */
    @GetMapping("/add")
    public String user() {
        return "user";
    }

    /**
     * 保存
     * @param user
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Mono<User> save(User user) {
        return userService.save(user);
    }

    /**
     * 通过用户名删除用户
     * @param
     * @return
     */
    @DeleteMapping("/username")
    @ResponseBody
    public Mono<User> deleteByUsername(String name) {

        return userService.deleteByUsername(name);
    }

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "根据用户Id删除用户")
    public Mono<Void> delete(String id){
        return userService.findById(id)
                .flatMap(x->
                userService.deleteById(x.getId()));
    }

    /**
     * 通过用户名获取用户
     * Mono返回
     * @param name
     * @return
     */
    @GetMapping("/findByUsername")
    @ResponseBody
    @ApiOperation(value = "查找用户",nickname ="查找不到时返回一个新的User对象")
    public Mono<User> findByUsername(String name) {
        //查找不到时返回一个新的User对象
        return userService.findUserByName(name)
                .map(x-> x)
                .defaultIfEmpty(new User());
    }

    /**
     * 第一种方式，数据延迟获取，一次只返回一条，可以减轻带宽压力
     * Flux，返回一次或多次
     * @return
     */
    @GetMapping(value="/allUser", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "查找所有用户",nickname = "每条数据之间间隔两秒")
    public Flux<User> findAll() {
        //每条数据之间延迟2秒
        return userService.findAll().delayElements(Duration.ofSeconds(2));
    }

    /**
     * 第二种方式，返回list<User>集合
     * Flux，返回一次或多次
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查找所有用户")
    public Flux<User> list(){
        Flux<User> flux = userService.findAll().map(x->{
            User user = new User();
            BeanUtils.copyProperties(x,user);
            return user;
        });
        return flux;
    }

}
