package com.springboot.springbootswagger.controller;

import com.springboot.springbootswagger.model.User;
import com.springboot.springbootswagger.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(value = "用户管理演示")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserById/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据用户 id 获取用户信息", tags = "查询用户信息类")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息", tags = "查询用户信息类")
    public Object getAllUsers() {
        Long count=null;
        List<User> list =userService.getAllUsers();
        //筛选出名字为hai的用户
        count=list.stream().filter(p-> "hai".equals(p.getNickName())).count();
        //取出所用用户的名字
        List<String> list1=list.stream().map(User::getNickName).collect(Collectors.toList());
        Map<String,String> map=new HashMap<>();
        map.put("id","1");
        map.put("name","zhaolinhai");
        map.put("age","22");
        //将map转list
        List<String> list2=map.values().stream().filter("22"::equalsIgnoreCase).collect(Collectors.toList());
        System.out.println("list2:"+list2);
        System.out.println("list1:"+list1);
        //获取所用用户名，形成字符串，并用逗号隔开
        String name=list.stream().map(User::getNickName).collect(Collectors.joining(","));
        System.out.println("name:"+name);
        //将所有用户通过id逆序排列
        List<User> stringList=list.stream().filter(p->null!=p.getId()).sorted(Comparator.comparing(User::getId).reversed()).collect(Collectors.toList());
        System.out.println("stringList:"+stringList);
        return count;
    }

    @PostMapping("/operateLists")
    @ApiOperation(value = "两个list取交叉并集合及去重", notes = "交叉并集及去重", tags = "交叉并集及去重")
    public void operateLists(){
        List<String> list1=new ArrayList<>(Arrays.asList("1","2","3","4"));
        List<String> list2=new ArrayList<>(Arrays.asList("2","4","5","6"));
        //交集
        List<String> list3=list1.stream().filter(list2::contains).collect(Collectors.toList());
        //以list2为基准，对list1取差集
        List<String> list4=list1.stream().filter(p->!list2.contains(p)).collect(Collectors.toList());
        //以list1为基准，对list2取差集
        List<String> list5=list2.stream().filter(p->!list1.contains(p)).collect(Collectors.toList());
        //list1和list2并集,(parallelStream(),并行，线程安全)
        List<String> list6=list1.parallelStream().collect(Collectors.toList());
        List<String> list8=list2.parallelStream().collect(Collectors.toList());
        list8.addAll(list6);
        //去重并集
        List<String> list7=list8.stream().distinct().collect(Collectors.toList());
        System.out.println("(交集)list3:"+list3);
        System.out.println("(以list2为基准，对list1取差集)list4:"+list4);
        System.out.println("(以list1为基准，对list2取差集)list5:"+list5);
        System.out.println("(list1和list2并集)list8:"+list8);
        System.out.println("(去重并集)list7:"+list7);
    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "新增/修改用户信息")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "删除用户信息", notes = "根据用户 id 删除用户信息")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "success";
    }
}
