package com.example.springswagger.controller;

import com.example.springswagger.dao.User;
import com.example.springswagger.dao.UserDao;
import com.example.springswagger.dao.UserService;
import com.example.springswagger.service.Impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Api(value = "用户管理演示")
@RestController
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserService userService;

    @GetMapping("/getUserById/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据用户 id 获取用户信息", tags = "查询用户信息类")
    public User getUserById(@PathVariable String id) {
        return userDao.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息", tags = "查询用户信息类")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @GetMapping("/findAll")
    @ResponseBody
    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息", tags = "查询用户信息类")
    public Flux<User> findAll() {
        return userService.findAll().delayElements(Duration.ofSeconds(1))
                .map(x->x)
                .defaultIfEmpty(new User());
    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "新增用户信息")
    public User saveUser(User user) {
        return userDao.saveUser(user);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增用户信息")
    public Mono<User> save(User user) {
        return userServiceImpl.save(user);
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "批量添加用户信息")
    public int addUser(@RequestBody List<User> user) {
        return userDao.addUser(user);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "修改用户信息")
    public String updateUser(User user) {
        return userDao.updateUser(user);
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "删除用户信息", notes = "根据用户 id 删除用户信息")
    public String deleteById(@PathVariable String id) {
        userDao.deleteById(id);
        return "success";
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "删除用户信息", notes = "根据用户 id 删除用户信息")
    public Mono<Void> delete(String id) {
       return userService.findUserById(id)
               .flatMap(x->
                       userService.deleteById(x.getId()));
    }
}
