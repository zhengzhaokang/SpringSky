package com.wc.single.sky.system.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.system.domain.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class SysMenuImportDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private static final String DELETE_NOT = "N";
    private static final String DELETE = "Y";
    private static final String DELETE_FLAG_STATUS_NOT = "0";
    private static final String DELETE_FLAG_STATUS = "1";

    private static final String DISPLAY = "显示";
    private static final String HIDE = "隐藏";
    private static final String DISPLAY_NUM = "0";
    private static final String HIDE_NUM = "1";

    private static final String MULU = "目录";
    private static final String CAIDAN = "菜单";
    private static final String ANNIU = "按钮";
    private static final String MULU_NUM = "M";
    private static final String CAIDAN_NUM = "C";
    private static final String ANNIU_NUM = "F";


    /**
     * 菜单ID
     */
    @Excel(name = "Menu ID")
    private Long menuId;

    /**
     * 父菜单ID
     */
    @Excel(name = "Parent ID")
    private Long parentId;

    /**
     * 父菜单名称
     */
    @Excel(name = "Parent Menu Name")
    private String parentName;

    /**
     * 菜单名称
     */
    @Excel(name = "Menu Name")
    private String menuName;

    /**
     * 菜单URL
     */
    @Excel(name = "Menu Key")
    private String menuKey;

    /**
     * 组件
     */
    @Excel(name = "Component")
    private String component;
    /**
     * 显示顺序
     */
    @Excel(name = "Order Num")
    private String orderNum;

    /**
     * 类型:0目录,1菜单,2按钮
     */
    @Excel(name = "Menu Type")
    private String menuType;

    /**
     * 链接地址
     */
    @Excel(name = "Path")
    private String path;
    /**
     * 权限字符串
     */
    @Excel(name = "Perms")
    private String perms;
    /**
     * 菜单状态:0显示,1隐藏
     */
    @Excel(name = "Visible")
    private String visible;

    /**
     * 菜单图标
     */
    private String icon;


    /**
     * 打开方式 (_blank新窗口)
     */
    private String target;


    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 隐藏子菜单
     */
    private Boolean hiddenChildren;

    /**
     * 隐藏 PageHeader 组件中的页面带的 面包屑和页面标题栏
     */
    private Boolean hiddenHeader;

    /**
     * 子菜单
     */
    private List<SysMenu> children = new ArrayList<SysMenu>();
    @Excel(name = "Deletion")
    private String deleteFlag;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {

        if (MULU.equals(menuType)) {
            this.menuType = MULU_NUM;
        } else if (CAIDAN.equals(menuType)) {
            this.menuType = CAIDAN_NUM;
        } else if (ANNIU.equals(menuType)) {
            this.menuType = ANNIU_NUM;
        } else if (MULU_NUM.equals(menuType)) {
            this.menuType = MULU;
        } else if (CAIDAN_NUM.equals(menuType)) {
            this.menuType = CAIDAN;
        } else if (ANNIU_NUM.equals(menuType)) {
            this.menuType = ANNIU;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        if (StringUtils.isNotBlank(visible)) {
            if (DISPLAY.equals(visible)) {
                this.visible = DISPLAY_NUM;
            } else if (HIDE.equals(visible)) {
                this.visible = HIDE_NUM;
            } else if (DISPLAY_NUM.equals(visible)) {
                this.visible = DISPLAY;
            } else if (HIDE_NUM.equals(visible)) {
                this.visible = HIDE;
            }
        }
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Boolean getHiddenChildren() {
        return hiddenChildren;
    }

    public void setHiddenChildren(Boolean hiddenChildren) {
        this.hiddenChildren = hiddenChildren;
    }

    public Boolean getHiddenHeader() {
        return hiddenHeader;
    }

    public void setHiddenHeader(Boolean hiddenHeader) {
        this.hiddenHeader = hiddenHeader;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        if (StringUtils.isNotBlank(deleteFlag)) {
            if (DELETE_NOT.equals(deleteFlag)) {
                this.deleteFlag = DELETE_FLAG_STATUS_NOT;
            } else if (DELETE.equals(deleteFlag)) {
                this.deleteFlag = DELETE_FLAG_STATUS;
            } else if (DELETE_FLAG_STATUS_NOT.equals(deleteFlag)) {
                this.deleteFlag = DELETE_NOT;
            } else if (DELETE_FLAG_STATUS.equals(deleteFlag)) {
                this.deleteFlag = DELETE;
            }

        }

    }
}
