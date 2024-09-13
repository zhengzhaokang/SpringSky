package com.wc.single.sky.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wc.single.sky.common.jackson.serializer.BigDecimalScaleSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

/**
 * @author guowb1
 * @description BigDecimal转String注解
 * @date 2022/6/23 18:21
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(
        using = BigDecimalScaleSerializer.class
)
public @interface BigDecimalToString {

    /**
     * 保留小数位数
     * @return
     */
    int scale() default 2;

    /**
     * 精度计算模式
     * @return
     */
    RoundingMode roundingMode() default RoundingMode.HALF_DOWN;

    /**
     * 千位分隔
     * @return
     */
    boolean thousandSeparate() default true;
}
