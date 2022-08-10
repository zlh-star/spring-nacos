package com.example.springbooteasyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbooteasyexcel.dao.User;
import com.example.springbooteasyexcel.service.Uservice;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private Uservice uservice;

    @ApiOperation(value = "导出excel",tags = "浏览器导出")
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public void exportMembers1(HttpServletResponse response,String fileName,String pageNum,String pageSize ) throws IOException {
//        List<User> members = uservice.getAllUser();
        List<User> pageList = new ArrayList<>();
        List<User> members = uservice.selectList(null);
//        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
//        for (int i = 0; i < pageSize && curIdx + i < members.size(); i++) {
//            pageList.add(members.get(curIdx + i));
//        }
//        IPage page = new Page<>(pageNum, pageSize);
//        page.setRecords(pageList);
//        page.setTotal(members.size());
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
//            return pageList;
        }
//        return page;
        // 设置文本内容
        response.setContentType("application/vnd.ms-excel");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头
        String fileName1 = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName1+".xlsx");
        EasyExcel.write(response.getOutputStream(), User.class).sheet().doWrite(pageList);
    }
}
