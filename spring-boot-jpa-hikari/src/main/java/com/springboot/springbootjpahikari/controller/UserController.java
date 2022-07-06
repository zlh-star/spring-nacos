package com.springboot.springbootjpahikari.controller;

import com.springboot.springbootjpahikari.model.UserModel;
import com.springboot.springbootjpahikari.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "进行演示")
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * 查询用户列表
     *
     * @return
     */
    @GetMapping("/user1")
    @ApiOperation(value = "查询")
    @ApiModelProperty(value = "")
    public List<UserModel> user() {

        return userRepository.findAll(Sort.by("id").descending());
    }

    /**
     * 新增或更新用户信息
     */
    @PostMapping("/user2")
    @ApiOperation(value = "插入数据")
    public UserModel user(UserModel user) {

        return userRepository.save(user);
    }

    /**
     * 根据id删除用户
     */
    @DeleteMapping("/user3")
    @ApiOperation(value = "删除账号")
    public String deleteUserById(String id) {
        userRepository.deleteById(id);
        return "success";
    }
}
