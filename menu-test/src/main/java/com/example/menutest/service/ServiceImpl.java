package com.example.menutest.service;

import com.example.menutest.bo.MenuBo;
import com.example.menutest.bo.roleMenuBo;
import com.example.menutest.dao.MenuDao;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("MenuService")
public class ServiceImpl implements MenuService  {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuBo> getNextChildMenu(String menuId) {
        return menuDao.getNextChildMenu(menuId);
    }

    @Override
    public List<MenuBo> getNextChildMenuList(List<MenuBo> menuBoList) {
        List<MenuBo> menuBoList1=new ArrayList<>();
        List<MenuBo> boList=new ArrayList<>();
        for (MenuBo menuBo:menuBoList){
            if(menuBo.getMenuParentId()==null|| menuBo.getMenuParentId().equals("")){
                menuBoList1.add(menuBo);
            }
        }
        menuBoList1.forEach(menuBo1->{
            MenuBo list1=list(menuBo1,menuBoList);
            boList.add(list1);
        });
        return boList;
    }

    @Override
    public MenuBo getParentMenu(String menuParentId) {
        return menuDao.getParentMenu(menuParentId);
    }

    private MenuBo list(MenuBo menuBo1,List<MenuBo> menuBos){
        List<MenuBo> boList=new ArrayList<>();
        menuBos.forEach(menuBo -> {
            if(menuBo1.getMenuId().equals(menuBo.getMenuParentId())){
                boList.add(list(menuBo,menuBos));
            }
        });
        menuBo1.setChildrenList(boList);
        return menuBo1;
    }

    @Override
    public List<MenuBo> getAllMenuTitle() {
        return menuDao.getAllMenuTitle();
    }

    @Override
    public List<MenuBo> menuList(MenuBo menuBo) {
        return menuDao.menuList(menuBo);
    }

    @Override
    public List<roleMenuBo> roleMenuList(String menuTitle) {
        return menuDao.roleMenuList(menuTitle);
    }


    public List<MenuBo> buildTree(List<MenuBo> menuBoList) {
        // 获取所有最顶层机构
        List<MenuBo> topList = new ArrayList<>();
        for (MenuBo menuBo : menuBoList) {
            // 父ID为null时为最顶级
            if (menuBo.getMenuParentId()==null||menuBo.getMenuParentId()=="") {
                topList.add(menuBo);//将最根节点的数据添加到topList
            }
        }
        // 获取最顶级的下级
//       topList.sort(Comparator.comparingInt(MenuBo::getMenuIndex).reversed());
        //根据字段去重并排序
        List<MenuBo> boList=topList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->
                new TreeSet<>(Comparator.comparingInt(MenuBo::getMenuIndex).reversed())),ArrayList::new));
        List<MenuBo> resultList = new ArrayList<>();
        for (MenuBo menuBo : boList) {
            MenuBo treeChildren = getTreeChildren(menuBo, menuBoList);
//            List<MenuBo> menuBoList1=menuDao.getNextChildMenu(menuBo.getMenuId());
            resultList.add(treeChildren);
//            result.addAll(menuBoList1);
        }
        // 返回结果
        return resultList;
    }

    private MenuBo getTreeChildren(MenuBo menuBo, List<MenuBo> menuBoList) {
        // 子级集合
        List<MenuBo> childrenList = new ArrayList<>();
        // 遍历所有数据,获取当前menuBo直接下级
        for (MenuBo menuBo1 : menuBoList) {
            // 判断是否为menuBo的子级
            if (menuBo.getMenuId().equals(menuBo1.getMenuParentId())) {
                // 获取所有菜单并分级
                childrenList.add(getTreeChildren(menuBo1, menuBoList));
                //获取所有顶级菜单及他们的下级菜单，并分级
//                childrenList=menuDao.getNextChildMenu(menuBo1.getMenuParentId());
            }
        }
        // 循环结束,设置menuBo的子级
//        List<MenuBo> list4=childrenList.stream().sorted(Comparator.comparingInt(MenuBo::getMenuIndex)).collect(Collectors.toList());
        //根据字段属性去重并排序
        List<MenuBo> list4=childrenList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->
                new TreeSet<>(Comparator.comparingInt(MenuBo::getMenuIndex).reversed())),ArrayList::new));
        menuBo.setChildrenList(list4);
        return menuBo;
    }
}

