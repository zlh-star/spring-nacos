package com.example.springeasyexcelinput.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springeasyexcelinput.dto.UserDto;
import com.example.springeasyexcelinput.service.UserMapper;
import com.example.springeasyexcelinput.service.UserserviceImpl;
import com.example.springeasyexcelinput.service.Uservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

@Api(value = "批量导入测试")
@RestController
public class TestController {
    @Autowired
    private UserMapper uservice;


    //大约可60万条
    @RequestMapping(value = "/input",method = RequestMethod.POST)
    @ApiOperation(value = "导入测试",notes = "批量")
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
        //mybatis-plus去重
        QueryWrapper<UserDto> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("distinct username,password,sex,birth,email");
        //stream去重
        userDtoList1=uservice.selectList(null).stream().distinct().collect(Collectors.toList());
        System.out.println(userDtoList1);
        userDtoList= uservice.selectList(queryWrapper);
        return userDtoList;
//        return 0;
    }

//    @ApiOperation(value = "导出excel",tags = "浏览器导出")
//    @RequestMapping(value = "/export",method = RequestMethod.POST)
//    public void exportMembers1(HttpServletResponse response, String fileName, String pageNum, String pageSize ) throws IOException {
//        List<UserDto> pageList = new ArrayList<>();
//        List<UserDto> members = uservice.selectList(null);
//        int count=0;
//        int pageno=Integer.parseInt(pageNum);
//        int pagesize=Integer.parseInt(pageSize);
//        if(members!=null&&members.size()>0){
//            count=members.size();
//            int pageIndex=(pageno-1)*pagesize;
//            int toIndex=pageno*pagesize;
//            if(toIndex>count){
//                toIndex=count;
//            }
//            pageList=members.subList(pageIndex,toIndex);
//        }
//        // 设置文本内容
//        response.setContentType("application/vnd.ms-excel");
//        // 设置字符编码
//        response.setCharacterEncoding("utf-8");
//        // 设置响应头
//        String fileName1 = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
//        response.setHeader("Content-disposition", "attachment;filename="+fileName1+".xlsx");
//        EasyExcel.write(response.getOutputStream(), UserDto.class).sheet().doWrite(pageList);
//    }
}
