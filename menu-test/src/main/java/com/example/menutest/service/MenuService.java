package com.example.menutest.service;

import com.example.menutest.bo.MenuBo;

import java.util.List;

public interface MenuService {
    /**
     * 获取所有的子节点
     * @param menuId
     * @return
     */
    public List<MenuBo> getNextChildMenu(String menuId);

    /**
     * 获取所有的菜单
     * @return
     */
    public List<MenuBo> getAllMenuTitle();
}
