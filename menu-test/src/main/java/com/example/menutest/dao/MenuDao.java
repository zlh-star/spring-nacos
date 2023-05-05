package com.example.menutest.dao;

import com.example.menutest.bo.MenuBo;
import com.example.menutest.bo.roleMenuBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {
    /**
     * 获取所有的子节点
     * @param menuId
     * @return
     */
    public List<MenuBo> getNextChildMenu(String menuId);

    MenuBo getParentMenu(String menuParentId);

    /**
     * 获取所有的菜单
     * @return
     */
    public List<MenuBo> getAllMenuTitle();

    public List<MenuBo> menuList(MenuBo menuBo);

    public List<roleMenuBo> roleMenuList(String menuTitle);
}
