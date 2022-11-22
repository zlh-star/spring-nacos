package com.example.mongotest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mongotest.bean.User;
import com.example.mongotest.config.Constant;
import com.example.mongotest.config.Results;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
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

    private static Mongo mongo=null;

    @Autowired
    private MongoTemplate mongoTemplate;

//    @ApiOperation(value = "",tags = "")
//    @RequestMapping(value = "/insert",method = RequestMethod.POST)
//    public void insert(@RequestBody List<User> userList){
//        MongoDatabase mongoDatabase= mongoTemplate.getDb();
//       mongoDatabase.getCollection("test").insertMany(userList);
//        mongoCollection.insertMany(userList);
//    }

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
        //范围查询
        query.addCriteria(Criteria.where("age").gte(22).lte(23));
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

    @ApiOperation(value = "测试批量",tags = "测试")
    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public Object Test1(@RequestBody List<User> userList){
        try {
            BulkOperations operations=mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "test");
            operations.insert(userList).execute();
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }
    @ApiOperation(value = "测试删除",tags = "删除")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object update(@RequestBody List<User> userList){
        String collectionName="test";
        BulkOperations operations=mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
        List<Query> queryList=new ArrayList<>();
        try {
            if(userList!=null&&userList.size()>0){
                userList.forEach(user -> {
                    Query query=new Query();
                    query.addCriteria(Criteria.where("name").is(user.getName()));
//              query.addCriteria(Criteria.where("age").gte(20).lte(22));
                    queryList.add(query);
                    operations.remove(queryList).execute();
                });
                return "成功";
            }
            return "入参list为空";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    @ApiOperation(value = "connectPool",tags = "连接池")
    @RequestMapping(value = "/connectPool",method = RequestMethod.POST)
    public Object connectPool(@RequestBody List<User> userList){
        String collectName="test1";
        try {
            MongoCollection<Document> mongoCollection =mongoTemplate.getCollection(collectName);
            if(userList!=null&&userList.size()>0){
                userList.forEach(user -> {
                    List<Document> documents=new ArrayList<>();
                    Document document=new Document();
                    document.put(user.getId(), JSONObject.toJSON(user));
                    documents.add(document);
                    mongoCollection.insertMany(documents);
                });
                return "success";
            }
            return "入参不为空";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @ApiOperation(value = "按条件查找",tags = "查找")
    @RequestMapping(value = "/findPool",method = RequestMethod.POST)
    public Object findPool(@RequestParam String name){
        String collectName="test";
        MongoCollection<Document> mongoCollection =mongoTemplate.getCollection(collectName);
        Bson bson= Filters.eq("name",name);
//        Bson bson1=Filters.not();
        FindIterable<Document> findIterable=mongoCollection.find(bson);
        MongoCursor<Document> documentMongoCursor=findIterable.iterator();
        List<Document> documents=new ArrayList<>();
        while (documentMongoCursor.hasNext()){
            Document document=documentMongoCursor.next();
            documents.add(document);
        }
        return JSONArray.toJSON(documents);
    }
}
