package com.wc.single.sky.common.log;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FieldCompare {

    //字段名称
    String name();
    //类型映射
    String properties() default "";

    String dateFormat() default "";

    String join() default "";
}
