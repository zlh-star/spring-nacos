package com.example.redisdelaydelete.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.redisdelaydelete.config.Result;
import com.example.redisdelaydelete.mapper.User;
import com.example.redisdelaydelete.mapper.UserMapper;
import com.example.redisdelaydelete.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Api(tags = "delay")
@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("client")
    private RedissonClient redissonClient;

    @ApiOperation(value = "/insert",tags = "新增")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(@RequestBody User user){
       userService.insert(user);
    }

    @ApiOperation(value = "/update",tags = "更新")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object update(@RequestBody User user) throws InterruptedException {
        RLock rLock=redissonClient.getLock("zhao");
        rLock.lock();
        try {
            userService.update(user);
            System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
            log.info("加锁成功，执行后续代码。线程 ID：{}" , Thread.currentThread().getId());
//            Thread.sleep(10000);
        } catch (Exception e) {
            return e;
        } finally {
            rLock.unlock();
            // 3.解锁
            System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
            log.info("Finally，释放锁成功。线程 ID：{}" , Thread.currentThread().getId());
        }
        return true;
    }

    @ApiOperation(value = "/getUser",tags = "获取")
    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    public void getUser(@RequestParam String age){
       userService.getUser(age);
    }

}
