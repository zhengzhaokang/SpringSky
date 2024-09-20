package com.wc.single.sky.common.auth.annotation;


import com.wc.single.sky.common.enums.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限
 * 作用 sqlSession级别
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermissions {

    /**
     * 指定表名 不指定默认第一个表是主表
     *
     * @return
     */
    String tableName() default "";

    /**
     * 表的别名
     * @return
     */
    String tableAlias() default "";
    /**
     * 是否开启join方式过滤权限
     * @return
     */
    boolean openJoin() default false;

    /**
     * 提供两种实现方式 一种是sql注入 一种是拦截器拼接sql
     * 复杂sql建议使用 SQL_INJECTION
     * 一般简单场景使用默认的 SQL_APPEND 即可
     * @return
     */
    PermissionType permissionType() default PermissionType.SQL_APPEND;

    /**
     * 开启全文索引  适用于多选检索
     * @return
     */
    boolean openFullTextIndex() default  false;

    /**
     * 自定义数据权限处理器
     * @return
     */
    String handlerName() default "";
}
