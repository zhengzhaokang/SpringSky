package com.wc.single.sky.common.redis.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.redis.annotation.DistributedLock;
import com.wc.single.sky.common.redis.common.LockType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;


/**
 * DistributedLock注解切面类
 *
 * @author :daihc
 * @date :2022-12-28
 **/
@Aspect
@Component
@Slf4j
public class RedissonDistributedLockAspect {

	@Autowired
	private RedissonClient redissonClient;



	@Pointcut("@annotation(com.wc.single.sky.common.redis.annotation.DistributedLock)")
	public void lockPoint() throws UnsupportedOperationException {
		//切面
	}

	@Around("lockPoint()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		DistributedLock lockAction = method.getAnnotation(DistributedLock.class);
		String key = getKey(method, pjp.getArgs(),lockAction);
		RLock lock = getLock(key, lockAction);
		boolean isLocked = lock.isLocked();
		//指定时间内重复提交失败
		if (isLocked && !lockAction.submitAgain()) {
			log.debug("get lock failed [{}]", key);
			log.info(String.format("get lock failed [%s]", LocalDateTime.now()));
			//todo  此处不应该直接返回null 后续应改为返回ResultDTO
			return null;
		}
		lock.tryLock(lockAction.waitTime(), lockAction.leaseTime(), lockAction.unit());
		//得到锁,执行方法，释放锁
		log.debug("get lock success [{}]", key);
		try {
			log.info(String.format("获取锁%s执行时间为:%s", key, LocalDateTime.now()));
			return pjp.proceed();
		} catch (Exception e) {
			log.error("execute locked method occured an exception", e.getMessage());
			if (lock.isLocked() && lock.isHeldByCurrentThread()) {
					lock.unlock();
					log.debug("release lock [{}]", key);
			}
			throw new RuntimeException(e);
		} finally {
			if (lock.isLocked() && lock.isHeldByCurrentThread() && !lockAction.isDelay()) {
				lock.unlock();
				log.debug("release lock [{}]", key);
			}
		}
	}

	private String getKey(Method method, Object[] args, DistributedLock lock) {
		String methodName = method.getName();
		int index = lock.argIndex();
		String attribute = lock.lockForAttribute();
		if (StringUtils.isEmpty(attribute)) {
			return lock.lockForKey();
		}
		Object obj = args[index];
		if (null == obj) {
			throw new RuntimeException("DistributedLock  Args is  Null ArgIndex Is " + index);
		}
		JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
		String[] attributes = attribute.split(",");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("DistributedLock:");
		stringBuilder.append(methodName);
		stringBuilder.append(":");
		for (String attr : attributes) {
			String value = jsonObject.getString(attr);
			if (StringUtils.isEmpty(value)) {
				log.error("锁的key 为空");
				throw new RuntimeException("DistributedLock  Key Is  Null Attribute Is " + attr);
			}
			stringBuilder.append(value);
			stringBuilder.append(":");
		}
		return stringBuilder.toString();
	}


	private RLock getLock(String key,DistributedLock lockAction) {
		switch (lockAction.lockType()) {
			case LockType.REENTRANT_LOCK:
				return redissonClient.getLock(key);

			case LockType.FAIR_LOCK:
				return redissonClient.getFairLock(key);

			case LockType.READ_LOCK:
				return redissonClient.getReadWriteLock(key).readLock();

			case LockType.WRITE_LOCK:
				return redissonClient.getReadWriteLock(key).writeLock();

			default:
				throw new RuntimeException("do not support lock type:" + lockAction.lockType());
		}
	}
}

