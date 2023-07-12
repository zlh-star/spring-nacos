package com.example.hbasetest.controller;

import com.example.hbasetest.config.HBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.hadoop.hbase.client.*;
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
    public Object test(){
        Result result=baseService.getRow("zlh1","abc");
        byte[] a=null;
        if(result!=null){
            a=result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("1"));
        }
        return Bytes.toString(a);
    }

//    @ApiOperation(value = "",tags = "查")
//    @RequestMapping(value = "/find",method = RequestMethod.GET)
//    public void find() throws IOException {
//
//        Table table = null;
//        try{
//            table = HBaseConn.getTable("FileTable");
//            System.out.println(table.getName());
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }
//
//    }


//    public static void showCell(Result result){
//
//        Cell[] cells = result.rawCells();
//
//        for(Cell cell:cells){
//
//            System.out.println("RowName:"+new String(CellUtil.cloneRow(cell))+" ");
//
//            System.out.println("Timetamp:"+cell.getTimestamp()+" ");
//
//            System.out.println("column Family:"+new String(CellUtil.cloneFamily(cell))+" ");
//
//            System.out.println("row Name:"+new String(CellUtil.cloneQualifier(cell))+" ");
//
//            System.out.println("value:"+new String(CellUtil.cloneValue(cell))+" ");
//
//        }
//
//    }
}
