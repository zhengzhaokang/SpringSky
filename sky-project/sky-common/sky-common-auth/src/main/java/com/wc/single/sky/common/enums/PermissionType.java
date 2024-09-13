package com.wc.single.sky.common.enums;

/**
 *  数据权限的实现方式
 *   SQL_INJECTION sql 注入
 *   SQL_APPEND 拦截器 修改sql
 */
public enum PermissionType {
    SQL_INJECTION("sql_injection"),
    SQL_APPEND("sql_append");

    PermissionType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

}
