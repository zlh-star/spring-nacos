package com.example.provider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.provider.mapper.User;
import com.example.provider.service.HBaseService;
import io.swagger.annotations.ApiOperation;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class HbaseController {

    @Resource
    private HBaseService baseService;


    @ApiOperation(value = "/tableName",tags = "创建表")
    @RequestMapping(value = "/tableName",method = RequestMethod.POST)
    public void tableName(@RequestParam("name")String name){
        String[] a={"info"};
        baseService.createTable(name,a);
    }

    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public void change(@RequestParam("value")String value)  {
        try {
            baseService.changeStatus(TableName.valueOf(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "/deleteColumn",tags = "删除一行")
    @RequestMapping(value = "/deleteColumn",method = RequestMethod.POST)
    public void deleteColumn (){
        baseService.deleteRow("test","abc");
    }

    @RequestMapping(value = "/getScanner",method = RequestMethod.GET)
    public Object getScanner(){
        ResultScanner results=baseService.getScanner("test");
        List<String> list=new ArrayList<>();
        List<User> list1=new ArrayList<>();
        for (Result result: results){
            String value= Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("1")));
            list.add(value);
        }
        JSONArray jsonArray= JSON.parseArray(list.toString());
        list1=JSON.parseArray(jsonArray.toString(), User.class);
        return list1 ;
//        Result result=baseService.getRow("zlh1","abc");
//        byte[] a=null;
//        if(result!=null){
//            a=result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("1"));
//        }
//        return Bytes.toString(a);
    }

    @RequestMapping(value = "/testRow",method = RequestMethod.POST)
    public String testRow(){
        //        String[] ab={"info2","info3"};
//        baseService.createTable("zlh1",ab);
//        baseService.putRow("zlh1","abc","info2","1","zhaolinhai");
        List<Put> putList=new ArrayList<>();
        byte[] a=Bytes.toBytes("abc");
        try {
            User user=User.builder()
                    .id("1")
                    .name("zlh")
                    .age(25)
                    .build();
            Put put=new Put(a);
            put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("1"),Bytes.toBytes(JSONObject.toJSONString(user)));
            putList.add(put);
            baseService.putRows("test",putList);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/testRows",method = RequestMethod.POST)
    public String testRows(){
        //        String[] ab={"info2","info3"};
//        baseService.createTable("zlh1",ab);
//        baseService.putRow("zlh1","abc","info2","1","zhaolinhai");
        List<Put> putList=new ArrayList<>();
        byte[] a=Bytes.toBytes("abc");
        try {
            Put put=new Put(a);
            put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("2"),Bytes.toBytes("asdfghjklertyui"));
            putList.add(put);
            baseService.putRows("test",putList);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() throws IOException {
//        baseService.putRow("zlh","1","info1","1","zhaolinhai");
//        SubstringComparator substringComparator=new SubstringComparator();
        //存在一个问题，同一列簇下的信息也会被模糊查询出
        List<String> list=new ArrayList<>();
        Filter filter=new ValueFilter(CompareOperator.EQUAL,new SubstringComparator("zhao"));
        FilterList filters=new FilterList();
        filters.addFilter(filter);
        ResultScanner results=baseService.getScanner("zlh","abc","abcd",filters);
        results.forEach(result -> {
            String value=Bytes.toString(result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("1")));
            list.add(value);
        });
        System.out.println(list);
//        Scan scan=new Scan();
//        scan.setFilter(filter);
//        System.out.println(scan);
        //精确到列簇下的某一qualifier,模糊查
        Filter filter1=new SingleColumnValueFilter(Bytes.toBytes("info"),Bytes.toBytes("1")
                ,CompareOperator.EQUAL,new SubstringComparator("lin"));
        //使用BinaryComparator进行精确值查询
        Filter filter2=new SingleColumnValueFilter(Bytes.toBytes("info"),Bytes.toBytes("1")
                ,CompareOperator.EQUAL,new BinaryComparator(Bytes.toBytes("hai")));
        Result result=baseService.getRow("zlh","abc");
        byte[] a=null;
        if(result!=null){
            a=result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("1"));
        }
        return Bytes.toString(a);
    }

    @RequestMapping(value = "/deleteColumnFamily",method = RequestMethod.DELETE)
    public String deleteColumnFamily(){
        try {
            baseService.deleteColumnFamily(TableName.valueOf("zlh"),Bytes.toBytes("info1"));
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/deleteTable",method = RequestMethod.DELETE)
    public String deleteTable(){
        List<TableName> list=new ArrayList<>();
        try {
            list.add(TableName.valueOf("zlh"));
            baseService.delTableAsync(list);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
