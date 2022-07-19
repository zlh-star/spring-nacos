package com.example.springbootmybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootmybatisplus.mapper.User;
import com.example.springbootmybatisplus.mapper.UserMapper;
import com.example.springbootmybatisplus.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userMapper.selectList(null);
    }

    @PostMapping("/insertUser")
    public Integer insertUser(User user) {
        return userMapper.insert(user);
    }

    @PostMapping("/updateUser")
    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }

    @DeleteMapping("/deleteUser")
    public Integer deleteUser(String id) {
        return userMapper.deleteById(id);
    }

    @GetMapping("/getUser")
    public List<User> getUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .isNotNull("nickName")
                .ge("age", 20);
        return userMapper.selectList(queryWrapper);
    }

    @GetMapping("/findPage")
    public IPage<User> findPage() {
        Page<User> page = new Page<>(1, 3);
        return userMapper.selectPage(page, null);
    }

    @GetMapping("/getAllByService")
    public List<User> getAllByService() {
        return userService.list();
    }

    @PostMapping("/insertUserByService")
    public Boolean insertUserByService(User user) {
        return userService.save(user);
    }

    @PostMapping("/updateUserByService")
    public Boolean updateUserByService(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("id", user.getId());
        queryWrapper.ne("nickName",user.getNickName());
        queryWrapper.ge("age",user.getAge());

        return userService.update(user, queryWrapper);
    }

    @DeleteMapping("/deleteUserByService")
    public Boolean deleteUserByService(String id) {
        return userService.removeById(id);
    }

//    @ApiModelProperty(value = "分页",notes = "分页插件")
//    @ApiOperation(value = "分页",notes = "mybatisPlus分页插件")
//    @GetMapping("/findPage")
//    public Object findPage(User user) {
//        Page<User> page = new Page<>(user.getBegin(),user.getEnd());
//        Page<User> modelPage=userMapper.selectPage(page, null);
//        return modelPage;
//    }
}
