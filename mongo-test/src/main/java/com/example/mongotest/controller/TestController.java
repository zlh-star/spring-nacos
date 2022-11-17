package com.example.mongotest.controller;

import com.example.mongotest.bean.User;
import com.example.mongotest.config.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(value = "mongo测试")
@RestController
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation(value ="mongo测试新增" ,tags = "新增")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public Object Test(){
        List<User> userList=new ArrayList<>(Arrays.asList(new User("3","rewqw",44)));
        List<String> test=new ArrayList<>(Arrays.asList("1","2","3"));
        User user=new User();
        user.setId("8");
        user.setName("argtiuytr");
        user.setAge(22);
        userList.add(user);
        User user1=new User();
        user1.setId("2");
        user1.setName("yrte");
        user1.setAge(22);
        userList.add(user1);
        try {
            userList.forEach(user2 -> {
                mongoTemplate.insert(user2);
            });
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }
    @ApiOperation(value = "查询",tags = "按条件查找")
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public Object find(@RequestBody User user){
        int pageSize=2;
        int currentPage=1;
        Query query=new Query();
//        query.addCriteria(Criteria.where("_id").is(id));
        long total=mongoTemplate.count(query,User.class);
        int total1=Integer.parseInt(String.valueOf(total));
        //模糊查询
        Pageable pageable= PageRequest.of(user.getPageNo(),user.getPageSize());
        query.addCriteria(Criteria.where("name").regex(user.getName()));
        //以年龄为基准，倒序排列
        query.with(Sort.by(Sort.Direction.DESC,"age"));
        query.with(pageable);
//        query.limit(user.getPageSize());
//        query.skip(user.getPageNo());
        List<User> userList= mongoTemplate.find(query,User.class);
        return userList;
    }

    @ApiOperation(value = "移除",tags ="删除数据")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Object delete(@RequestParam String id){
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        try {
            mongoTemplate.remove(query,User.class);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }
}
