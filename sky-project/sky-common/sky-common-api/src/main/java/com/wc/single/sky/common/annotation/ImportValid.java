package com.wc.single.sky.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dhc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportValid {
    /**
     * 字典表
     * @return
     */
    public String dicType() default "";

    /**
     * masterData 验证
     * @return
     */
    public String masterData() default "";

    /**
     * 是否导入字段
     * @return
     */
    public boolean isImport() default  true;

    /**
     * 字段长度校验
     * @return
     */
    public int length() default 0;

    /**
     * 日期格式校验
     * @return
     */
    public String dateFormat() default "";

    /**
     * 是否必填
     * @return
     */
    public boolean required() default false;

    /**
     * 字段类型
     * @return
     */
    public String fieldType() default "String";

    /**
     * 是否验证字段配置表
     * @return
     */
    public boolean fieldConfig() default false;

    /**
     * 级联关系
     * @return
     */
    public String[] cascadeRelations() default {};

    public String excelName() default "";

    public String dbName() default "";

    /**
     * 当某个字段的值是某些值的时候触发条件
     * @return
     */
    public String[] isInValues() default {};
    public String isInField() default "";

    /**
     * 是否数字
     * @return
     */
    public boolean isNumber() default false;

    /**
     * 是否正整数
     * @return
     */
    public boolean isPositiveNumber() default false;


    public boolean needToSearch() default false;

    /**
     * * 是否是正数
     * @return
     */
    public boolean isPositive() default false;


}
