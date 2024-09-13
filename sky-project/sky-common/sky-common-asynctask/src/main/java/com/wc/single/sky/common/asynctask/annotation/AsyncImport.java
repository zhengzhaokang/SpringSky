package com.wc.single.sky.common.asynctask.annotation;

import com.wc.single.sky.common.asynctask.constant.AsyncBusinessType;

import java.lang.annotation.*;

/**
 * 该注解只能用在service 的实现层
 *
 * @author qiaodf2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AsyncImport {
    /**
     * 定义的样式对象
     */
    Class<?> excelClass();

    /**
     * 业务类型 在这个里面定义
     * {@link AsyncBusinessType}
     */
    String businessType();

    /**
     * 是否走异步的阈值
     */
    int maximum() default 10;

    /**
     * 并行线程数
     * 如果方法要求在一个事务里面那么threadNum=1 and pageSize=0,用一个线程去处理整个数据
     * 如果没有这方面的要求，就可以用多个线程去执行任务。
     */
    int threadNum() default 1;

    /***
     * 等于0 查询所有
     * 如果方法要求在一个事务里面那么threadNum=1 and pageSize=0,用一个线程去处理整个数据
     * 如果没有这方面的要求，就可以用多个线程去执行任务。
     */
    int pageSize() default 0;
}
