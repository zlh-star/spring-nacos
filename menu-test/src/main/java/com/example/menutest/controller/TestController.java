package com.example.menutest.controller;

import com.example.menutest.bo.MenuBo;
import com.example.menutest.dao.MenuDao;
import com.example.menutest.service.MenuService;
import com.example.menutest.service.ServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "菜单测试")
@RestController
@RequestMapping("/testController")
public class TestController {

    @Autowired
    private ServiceImpl service;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取所有下级", httpMethod = "POST")
    @RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
    public Object selectMenuList(){
        List<MenuBo> list=menuService.getAllMenuTitle();
        List<MenuBo> list1=new ArrayList<>();
        if(list!=null&&list.size()>0){
            list1=service.buildTree(list);
        }
        return list1;
    }

}
