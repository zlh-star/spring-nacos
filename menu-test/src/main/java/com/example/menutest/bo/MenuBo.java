package com.example.menutest.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "菜单配置对象bo")
public class MenuBo {

    @ApiModelProperty(value ="菜单ID",name = "menuId")
    private String menuId;// 菜单ID

    @ApiModelProperty(value ="上级菜单ID",name = "menuParentId")
    private String menuParentId;// 上级菜单ID

    @ApiModelProperty(value ="菜单索引",name = "menuIndex")
    private int menuIndex;// 菜单索引

    @ApiModelProperty(value ="菜单名称",name = "menuTitle")
    private String menuTitle;//菜单名称

    @ApiModelProperty(value ="菜单路径",name = "menuPath")
    private String menuPath;// 菜单路径

    @ApiModelProperty(value ="菜单图标",name = "menuIcon")
    private String menuIcon;// 菜单图标

    @ApiModelProperty(value ="菜单类型",name = "menuKind")
    private String menuKind;// 菜单类型

    @ApiModelProperty(value ="",name = "menuObjPath")
    private String menuObjPath;//菜单权限

    @ApiModelProperty(value ="",name = "menuAppName")
    private String menuAppName;

    @ApiModelProperty(value ="菜单状态",name = "menuState")
    private String menuState;// 菜单状态

    @ApiModelProperty(value ="是否为权限项",name = "actionAuthorized")
    private String actionAuthorized;// 是否为权限项

    @ApiModelProperty(value ="是否为普通项",name = "actionNormal")
    private String actionNormal;// 是否为普通项

    @ApiModelProperty(value ="是否为系统项",name = "actionSystemic")
    private String actionSystemic;// 是否为系统项

    @ApiModelProperty(value ="是否为安全项",name = "actionSafe")
    private String actionSafe;// 是否为安全项

    @ApiModelProperty(value ="是否为审计项",name = "actionAudit")
    private String actionAudit;// 是否为审计项

    @ApiModelProperty(value ="",name = "actionType")
    private String actionType;

    @ApiModelProperty(value ="起始位置",name = "pageNo")
    private int pageNo;

    @ApiModelProperty(value ="显示条数",name = "pageSize")
    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private String isLeaf;// 是否是叶节点

    private List<MenuBo> childrenList;

    public List<MenuBo> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<MenuBo> childrenList) {
        this.childrenList = childrenList;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    @Override
    public String toString() {
        return "MenuBo{" +
                "menuId='" + menuId + '\'' +
                ", menuParentId='" + menuParentId + '\'' +
                ", menuIndex='" + menuIndex + '\'' +
                ", menuTitle='" + menuTitle + '\'' +
                ", menuPath='" + menuPath + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", menuKind='" + menuKind + '\'' +
                ", menuObjPath='" + menuObjPath + '\'' +
                ", menuAppName='" + menuAppName + '\'' +
                ", menuState='" + menuState + '\'' +
                ", actionAuthorized='" + actionAuthorized + '\'' +
                ", actionNormal='" + actionNormal + '\'' +
                ", actionSystemic='" + actionSystemic + '\'' +
                ", actionSafe='" + actionSafe + '\'' +
                ", actionAudit='" + actionAudit + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

//    public String getMenuIndex() {
//        return menuIndex;
//    }
//
//    public void setMenuIndex(String menuIndex) {
//        this.menuIndex = menuIndex;
//    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

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

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuKind() {
        return menuKind;
    }

    public void setMenuKind(String menuKind) {
        this.menuKind = menuKind;
    }

    public String getMenuObjPath() {
        return menuObjPath;
    }

    public void setMenuObjPath(String menuObjPath) {
        this.menuObjPath = menuObjPath;
    }

    public String getMenuAppName() {
        return menuAppName;
    }

    public void setMenuAppName(String menuAppName) {
        this.menuAppName = menuAppName;
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
    }

    public String getActionAuthorized() {
        return actionAuthorized;
    }

    public void setActionAuthorized(String actionAuthorized) {
        this.actionAuthorized = actionAuthorized;
    }

    public String getActionNormal() {
        return actionNormal;
    }

    public void setActionNormal(String actionNormal) {
        this.actionNormal = actionNormal;
    }

    public String getActionSystemic() {
        return actionSystemic;
    }

    public void setActionSystemic(String actionSystemic) {
        this.actionSystemic = actionSystemic;
    }

    public String getActionSafe() {
        return actionSafe;
    }

    public void setActionSafe(String actionSafe) {
        this.actionSafe = actionSafe;
    }

    public String getActionAudit() {
        return actionAudit;
    }

    public void setActionAudit(String actionAudit) {
        this.actionAudit = actionAudit;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
