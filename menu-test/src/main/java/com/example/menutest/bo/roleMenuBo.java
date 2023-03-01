package com.example.menutest.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色菜单配置类")
public class roleMenuBo {

    @ApiModelProperty(value ="菜单ID",name = "menuId")
    private String roleId;

    @ApiModelProperty(value ="显示条数",name = "pageSize")
    private String menuId;

    @ApiModelProperty(value ="菜单名称",name = "menuTitle")
    private String menuTitle;//菜单名称

    @ApiModelProperty(value ="菜单路径",name = "menuPath")
    private String menuPath;// 菜单路径

    @ApiModelProperty(value ="",name = "menuObjPath")
    private String menuObjPath;//菜单权限

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenuObjPath() {
        return menuObjPath;
    }

    public void setMenuObjPath(String menuObjPath) {
        this.menuObjPath = menuObjPath;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @ApiModelProperty(value ="显示条数",name = "pageSize")
    private String tenantId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
