package com.wc.single.sky.common.redis.annotation;

import com.wc.single.sky.common.redis.common.LockType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Redisson锁(Redis分布式锁)注解
 *
 * @author :daihc
 *
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited
public @interface DistributedLock {

	/**
	 * * 拼接锁的key  属性 (多个属性用,隔开)
	 * @return
	 */
	String lockForAttribute() default "";

	/**
	 * * 解析方法参数第几个
	 * @return
	 */
	int argIndex() default 0;

	/**
	 * * 是否可以重复提交  true 可以 false 不可以
	 * * (可以重复提交是指的是同Key下 必须上一次请求执行完了之后可以重复提交)
	 * * 只能限制锁的时间范围内不能重复提交 如想长期判断不能重复提交仍需业务实现
	 * @return
	 */
	boolean submitAgain() default true;

	//指定锁的key
	String lockForKey() default "'default'";

	String lockType() default LockType.REENTRANT_LOCK;

	/** 获取锁等待时间，默认10秒*/
	long waitTime() default 10000L;

	/** 锁自动释放时间，默认10秒*/
	long leaseTime() default 10000L;

	/** 时间单位（获取锁等待时间和持锁时间都用此单位）*/
	TimeUnit unit() default TimeUnit.MILLISECONDS;

	/**
	 * * 延迟过期
	 * @return
	 */
	boolean isDelay() default false;
}
