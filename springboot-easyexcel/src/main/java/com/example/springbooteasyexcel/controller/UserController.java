package com.example.springbooteasyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.example.springbooteasyexcel.dao.User;
import com.example.springbooteasyexcel.service.Uservice;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private Uservice uservice;

    @ApiOperation(value = "导出excel",tags = "浏览器导出")
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public void exportMembers1(HttpServletResponse response) throws IOException {
        List<User> members = uservice.getAllUser();
        // 设置文本内容
        response.setContentType("application/vnd.ms-excel");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), User.class).sheet("成员列表").doWrite(members);
    }
}
