package com.example.hbasetest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.hbasetest.bean.User;
import com.example.hbasetest.config.HBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "HBASE")
@RestController
public class TestController {

    @Resource
    private HBaseService baseService;



    @ApiOperation(value = "getScanner",tags = "获取Scanner")
    @RequestMapping(value = "/getScanner",method = RequestMethod.GET)
    public Object getScanner(){
        ResultScanner results=baseService.getScanner("test");
//        Map<String,Object> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        List<User> list1=new ArrayList<>();
        for (Result result: results){
//            String key= Arrays.toString(result.getRow());
//            map.put(key,result);
            String value= Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("1")));
//           list1= JSON.parseArray(value, User.class);
            list.add(value);
        }
        JSONArray jsonArray= JSON.parseArray(list.toString());
        list1=JSON.parseArray(jsonArray.toString(), User.class);
//        list.forEach(a->{
//
//        });
//        List<Map.Entry> maps = new ArrayList<>(map.entrySet());
        return list1 ;
//        Result result=baseService.getRow("zlh1","abc");
//        byte[] a=null;
//        if(result!=null){
//            a=result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("1"));
//        }
//        return Bytes.toString(a);
    }

    @ApiOperation(value = "testRow",tags = "存入实体类")
    @RequestMapping(value = "/testRow",method = RequestMethod.POST)
    public Object testRow(){
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
//            HBaseUtil.createTable("zlh","info1");
            Put put=new Put(a);
            put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("1"),Bytes.toBytes(JSONObject.toJSONString(user)));
            putList.add(put);
            baseService.putRows("test",putList);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "testRows",tags = "存入")
    @RequestMapping(value = "/testRows",method = RequestMethod.POST)
    public Object testRows(){
        //        String[] ab={"info2","info3"};
//        baseService.createTable("zlh1",ab);
//        baseService.putRow("zlh1","abc","info2","1","zhaolinhai");
        List<Put> putList=new ArrayList<>();
        byte[] a=Bytes.toBytes("abc");
        try {
//            HBaseUtil.createTable("zlh","info1");
            Put put=new Put(a);
            put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("1"),Bytes.toBytes("asdfghjklertyui"));
            putList.add(put);
            baseService.putRows("test",putList);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @ApiOperation(value = "test",tags = "获取")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Object test() throws IOException {
//        baseService.putRow("zlh","1","info1","1","zhaolinhai");
//        SubstringComparator substringComparator=new SubstringComparator();
        //存在一个问题，同一列簇下的信息也会呗模糊查询出
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

    @ApiOperation(value = "deleteColumnFamily",tags = "列簇删除")
    @RequestMapping(value = "/deleteColumnFamily",method = RequestMethod.DELETE)
    public Object deleteColumnFamily(){
        try {
            baseService.deleteColumnFamily(TableName.valueOf("zlh1"),Bytes.toBytes("info2"));
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation(value = "deleteTable",tags = "表删除")
    @RequestMapping(value = "/deleteTable",method = RequestMethod.DELETE)
    public Object deleteTable(){
        List<TableName> list=new ArrayList<>();
        try {
            list.add(TableName.valueOf("zlh1"));
            baseService.delTableAsync(list);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
