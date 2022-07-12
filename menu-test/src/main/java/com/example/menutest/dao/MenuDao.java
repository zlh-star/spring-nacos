package com.example.menutest.dao;

import com.example.menutest.bo.MenuBo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {
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
