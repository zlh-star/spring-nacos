package com.example.springeasyexcelinput.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springeasyexcelinput.dto.UserDto;
import com.example.springeasyexcelinput.service.UserserviceImpl;
import com.example.springeasyexcelinput.service.Uservice;
import com.example.springeasyexcelinput.service.WaterMarkHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "批量导入导出测试")
@RestController
public class TestController {

    @Autowired
    private Uservice uservice;

//    @Autowired
//    private UserMapper userMapper;

    @Value("${water.switch}")
    private String waterswitch;


    //大约可1,033,147,176,106.771万条
    //测试10万条需要3分钟左右
    //测试15万条需要5分钟左右
    @RequestMapping(value = "/input",method = RequestMethod.POST)
    @ApiOperation(value = "导入测试",tags = "批量导入excel数据")
    public Object excelInput(MultipartFile file) throws IOException {
        InputStream inputStream=file.getInputStream();
        List<UserDto> userDtoList=new ArrayList<>();
        List<UserDto> userDtoList1=new ArrayList<>();
        try {
            EasyExcel.read(inputStream, UserDto.class,new UserserviceImpl(uservice))
                    .excelType(ExcelTypeEnum.XLSX).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        //mybatis-plus去重
//        QueryWrapper<UserDto> queryWrapper=new QueryWrapper<>();
//        queryWrapper.select("distinct username,password,sex,birth,email");
//        //stream去重
//        userDtoList1=uservice.selectList(null).stream().distinct().collect(Collectors.toList());
//        System.out.println(userDtoList1);
//        userDtoList= uservice.selectList(queryWrapper);
//        return userDtoList;
        return "成功";
    }

    @ApiOperation(value = "浏览器导出",tags = "浏览器导出excel")
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public void exportMembers1(HttpServletResponse response, String fileName, String pageNum, String pageSize ) throws IOException {
        List<UserDto> pageList = new ArrayList<>();
        List<UserDto> members = uservice.selectList(null);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("省份号码")
                .append(" ").append("组织号码")
                .append(" ").append("测试水印");
        String ceshi=stringBuilder.toString();
        int count=0;
        int pageno=Integer.parseInt(pageNum);
        int pagesize=Integer.parseInt(pageSize);
        if(members!=null&&members.size()>0){
            count=members.size();
            int pageIndex=(pageno-1)*pagesize;
            int toIndex=pageno*pagesize;
            if(toIndex>count){
                toIndex=count;
            }
            pageList=members.subList(pageIndex,toIndex);
        }
        // 设置文本内容
        response.setContentType("application/vnd.ms-excel");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头
        String fileName1 = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName1+".xlsx");
        if("true".equals(waterswitch)){
            EasyExcel.write(response.getOutputStream(), UserDto.class)
                    .inMemory(true)
                    .registerWriteHandler(new WaterMarkHandler("测试","水印"))
                    .sheet().doWrite(pageList);
        }
        EasyExcel.write(response.getOutputStream(), UserDto.class)
//                .inMemory(true)
//                .registerWriteHandler(new WaterMarkHandler("测试","水印"))
                .sheet().doWrite(pageList);
    }
}
