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
import java.util.ListIterator;

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

    @ApiOperation(value = "获取所有下级", httpMethod = "POST")
    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public Object getMenu(){
        List<MenuBo> list=menuService.getAllMenuTitle();
        List<MenuBo> boList=menuService.getNextChildMenuList(list);
        return boList;

    }

    @ApiOperation(value = "获取菜单下级", httpMethod = "POST")
    @RequestMapping(value = "/nextMenu", method = RequestMethod.POST)
    public Object nextMenu(){
        List<MenuBo> list=menuService.getAllMenuTitle();
        ListIterator<MenuBo> list1=list.listIterator();
        List<MenuBo> boList=new ArrayList<>();
        while (list1.hasNext()){
            MenuBo menuBo=list1.next();
            if(menuBo.getMenuParentId()==null||menuBo.getMenuParentId()==""){
                menuService.getNextChildMenu(menuBo.getMenuId());
            }if(menuService.getNextChildMenu(menuBo.getMenuId())==null){
                list1.remove();
            }
            boList.add(menuBo);
//            menuBo.setChildrenList(boList);
//            return menuBo;
        }
        return boList;
    }

}
