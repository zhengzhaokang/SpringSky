package com.wc.single.sky.common.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局的数据权限注解 作用 namespace 级别
 * 规则 ：如果没有添加@DataPermissions 默认用此注解的值
 * 如果同时添加 @DataPermissions 和 @GlobalDataPermission  则默认使用的是@DataPermissions
 * @author dhc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalDataPermission {

    String tableAlias() default "";
    /**
     * 可以指定表的名称 不指定默认第一个 表是主表
     *
     * @return
     */
    String tableName() default "";

    /**
     * 是否开启join
     * @return
     */
    boolean openJoin() default false;


}
