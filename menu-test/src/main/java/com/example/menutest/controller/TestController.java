package com.example.menutest.controller;

import com.example.menutest.bo.MenuBo;
import com.example.menutest.config.EntityUtils;
import com.example.menutest.config.Result;
import com.example.menutest.service.MenuService;
import com.example.menutest.service.ServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "菜单测试")
@RestController
@RequestMapping("/testController")
public class TestController {

    @Autowired
    private ServiceImpl service;

    @Autowired
    private MenuService menuService;


    @ApiOperation(value = "test3",tags = "test3")
    @RequestMapping(value = "/test3",method = RequestMethod.POST)
    public Object test3(){

        MenuBo menuBo=new MenuBo();
        menuBo.setMenuId("1");
        menuBo.setMenuTitle("ceshi");
        Map<String,Object>test= EntityUtils.entityToMap(menuBo);
        EntityUtils.mapToEntity(test,MenuBo.class);
        return test;
//        List<MenuBo>test1= EntityUtils.mapToEntity(test,MenuBo.class);
    }

    @ApiOperation(value = "递归",tags = "递归算法")
    @RequestMapping(value = "/digui",method = RequestMethod.POST)
    public Object digui(){
        List<MenuBo> boList=new ArrayList<>();
        List<MenuBo> menuBoList=menuService.getAllMenuTitle();
        menuBoList.forEach(menuBo -> {
            if(menuBo.getMenuParentId()==null|| "".equals(menuBo.getMenuParentId())){
                boList.add(menuBo);
            }
        });
        List<MenuBo> list=boList.stream().sorted(Comparator.comparingInt(MenuBo::getMenuIndex)).collect(Collectors.toList());
        digui1(list);
        return Result.wrapResult(boList);
    }
    private void digui1(List<MenuBo> boList){
        boList.forEach(menu->{
            List<MenuBo> menuBos=menuService.getNextChildMenu(menu.getMenuId());
            if(menuBos!=null&&menuBos.size()>0){
                List<MenuBo> list1=menuBos.stream().sorted(Comparator.comparingInt(MenuBo::getMenuIndex)).collect(Collectors.toList());
                menu.setChildrenList(list1);
                digui1(list1);
            }
        });
    }

    @ApiOperation(value = "递归",tags = "向上递归")
    @RequestMapping(value = "digui2",method = RequestMethod.GET)
    public Object digui2 (String parentId){
        List<MenuBo> boList=new ArrayList<>();
        digui21(parentId,boList);
        List<MenuBo> boList1= service.buildTree(boList);
        return boList1;
    }

    private void digui21(String parentId,List<MenuBo> list){
       MenuBo menuBo= menuService.getParentMenu(parentId);
       if(menuBo!=null){
           list.add(menuBo);
           digui21(menuBo.getMenuParentId(),list);
       }
    }

    @ApiOperation(value = "测试",notes = "左连接测试", httpMethod = "POST")
    @RequestMapping(value = "/roleMenuList", method = RequestMethod.POST)
    public Object roleMenuList(String menuTitle){
        if(StringUtils.isEmpty(menuTitle)){
            return Result.wrapResult("菜单名称不能为空");
        }
        try {
            return Result.wrapResult(menuService.roleMenuList(menuTitle));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.wrapResult(0);
    }

    @ApiOperation(value = "获取所有菜单在分级", httpMethod = "POST")
    @RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
    public Object selectMenuList(){
        List<MenuBo> list=menuService.getAllMenuTitle();
        List<MenuBo> list1=new ArrayList<>();
        if(list!=null&&list.size()>0){
            list1=service.buildTree(list);
        }
        return Result.wrapResult(list1);
    }

    @ApiOperation(value = "去重", httpMethod = "POST")
    @RequestMapping(value = "/MenuList", method = RequestMethod.POST)
    public Object menuList(){
        List<MenuBo> list=new ArrayList<>();
        List<MenuBo> list2=new ArrayList<>();
        MenuBo menuBo=new MenuBo();
        menuBo.setMenuId("9");
        menuBo.setMenuTitle("菜单");
        list.add(menuBo);
        list2.add(menuBo);
        MenuBo menuBo1=new MenuBo();
        menuBo1.setMenuId("2");
        menuBo1.setMenuTitle("菜单1");
        list.add(menuBo1);
        MenuBo menuBo3=new MenuBo();
        menuBo3.setMenuId("3");
        menuBo3.setMenuTitle("菜单2");
        list.add(menuBo3);
        list2.add(menuBo3);
        System.out.println("合并"+list);
//        List<MenuBo> list5=menuService.getAllMenuTitle();
        List<MenuBo> menuBoList=list.stream().distinct().collect(Collectors.toList());
        System.out.println("menuBoList:"+menuBoList);
        //根据某一字段属性去重并排序
        List<MenuBo> list1=list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->
                new TreeSet<>(Comparator.comparing(MenuBo::getMenuId))),ArrayList::new));
        //取交集
        List<MenuBo> list3=list.stream().filter(list2::contains).collect(Collectors.toList());
        //按照指定字段排序
        List<MenuBo> list4=list.stream().sorted(Comparator.comparing(MenuBo::getMenuId)).collect(Collectors.toList());
        System.out.println("list4："+list4);
        System.out.println("交集："+list3);
        System.out.println("去重之后："+list1);
        return Result.wrapResult(list1);
    }

    @ApiOperation(value = "获取所有菜单并分级", httpMethod = "POST")
    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public Object getMenu(){
        List<MenuBo> list=menuService.getAllMenuTitle();
        List<MenuBo> boList=menuService.getNextChildMenuList(list);
        return Result.wrapResult(boList);

    }

    @ApiOperation(value = "获取所有菜单并分页", httpMethod = "POST")
    @RequestMapping(value = "/menuList", method = RequestMethod.POST)
    public Object menuList(@RequestBody MenuBo menuBo){
        List<MenuBo> stringList=new ArrayList<>();
            stringList=menuService.menuList(menuBo);
        return Result.wrapResult(stringList);
    }


    @ApiOperation(value = "迭代器的试用", httpMethod = "POST")
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
        return Result.wrapResult(boList);
    }

    @ApiOperation(value = "获取一级二级菜单（分级）", httpMethod = "POST")
    @RequestMapping(value = "/getNextMenu", method = RequestMethod.POST)
    public Object getNextMenu(){
        List<MenuBo> list=menuService.getAllMenuTitle();
        List<MenuBo> menuBos=new ArrayList<>();
        list.forEach(menuBo -> {
            if(menuBo.getMenuParentId()==null|| "".equals(menuBo.getMenuParentId())){
                menuBos.add(menuBo);
            }
        });
        for (MenuBo menuBo:menuBos){
            List<MenuBo>list1=menuService.getNextChildMenu(menuBo.getMenuId());
            menuBo.setChildrenList(list1);
        }
        return Result.wrapResult(menuBos);
        }


    private List<MenuBo> getMenu(MenuBo menuBo1, List<MenuBo> boList){
        List<MenuBo> menuBoList=new ArrayList<>();
        for(MenuBo menuBo: boList){
            menuService.getNextChildMenu(menuBo.getMenuId());
            menuBoList.addAll(getMenu(menuBo,boList));
        }
        menuBo1.setChildrenList(menuBoList);
//        getMenu(menuBo1,menuBoList);
        return boList;
    }

}
