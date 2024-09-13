package com.wc.single.sky.system.annotation;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQdpFeignClients
{
    String[] value() default {};

    String[] basePackages() default {"com.lenovo.qdp"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}