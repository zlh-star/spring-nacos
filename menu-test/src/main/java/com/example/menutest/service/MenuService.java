package com.example.menutest.service;

import com.example.menutest.bo.MenuBo;
import com.example.menutest.bo.roleMenuBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuService {
    /**
     * 获取所有的子节点
     * @param menuId
     * @return
     */
    public List<MenuBo> getNextChildMenu(String menuId);

    public List<MenuBo> getNextChildMenuList(List<MenuBo> menuBoList);

    /**
     * 获取所有的菜单
     * @return
     */
    public List<MenuBo> getAllMenuTitle();

    public List<MenuBo> menuList(MenuBo menuBo);

    public List<roleMenuBo> roleMenuList(String menuTitle);
}
