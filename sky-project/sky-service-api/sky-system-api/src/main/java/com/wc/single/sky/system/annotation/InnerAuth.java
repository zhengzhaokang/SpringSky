package com.wc.single.sky.system.annotation;

import java.lang.annotation.*;

/**
 * 内部认证注解
 * 
 * @author lovefamily
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth
{
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;
}