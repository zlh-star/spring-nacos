package com.example.springbootwebfluxmongodb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootwebfluxmongodb.mapper.User;
import com.example.springbootwebfluxmongodb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Controller
@RequestMapping("/user")
@Api(value = "用户测试")
@Slf4j
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
    public Mono<User> findAll() {
        //每条数据之间延迟2秒
//        List<Flux<User>> userList=new ArrayList<>();
//        JSONArray jsonArray=new JSONArray();
//        Sort sort=Sort.by(Sort.Direction.DESC,"age");
//        Pageable pageable= (Pageable) PageRequest.of(1,2,sort);
//       JSON.toJSONString(userService.findAll().delayElements(Duration.ofSeconds(2)));
//        List<Mono<List<User>>> userList=null;
        List<User> userList=new ArrayList<>();
        Mono<List<User>> listMono=userService.findAll().map(x -> {
            User user1=new User();
            BeanUtils.copyProperties(x,user1);

            return user1;
        }).collectList();
//        log.info("所有用户为：{}",userList);
        User user=new User();
       Mono<User> userMono= listMono.map(a->{
            BeanUtils.copyProperties(a,user);
            userList.add(user);
            return user;
        });
        return userMono;
//        return userList;
//       Mono<List<User>>listMono=
//             userService.findAll().flatMapIterable(user -> {
//                   userList.add(user);
//                   return userList;
//               }).map(x->x).collectList();
//        return userList;
//        JSONArray jsonArray=new JSONArray();
//        jsonArray.add(listMono);
//       return listMono;
//        System.out.println(userService.findAll().delayElements(Duration.ofSeconds(2)));
//        userList.add(userService.findAll().delayElements(Duration.ofSeconds(2)));
//        jsonArray.addAll(userList);
//        List<User> users=JSONObject.parseArray(jsonArray.toString(),User.class);

//        List<User> userList= JSONObject.parseArray(JSON.toJSONString(userService.findAll().delayElements(Duration.ofSeconds(2))),User.class);
//        return users;
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
//        List<Flux<User>>fluxes=new LinkedList<>();
//        fluxes.add(flux);
      return flux;
    }

}
