package com.wc.single.sky.common.redis.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * * 分布式锁的工具类
 */
@Component
public class RedissonUtils {

    @Autowired
    private RedissonClient redissonClient;

    private static final long WAIT_TIME = 1L;
    private static final long LEASE_TIME = 1L;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        RLock lock = getLock(key);
        if (lock == null) {
            return Boolean.FALSE;
        }
        return lock.tryLock(waitTime, leaseTime, unit);
    }

    public boolean tryLock(String key, long waitTime, TimeUnit unit) throws InterruptedException {

        return tryLock(key, waitTime, LEASE_TIME, unit);
    }

    public boolean tryLock(String key, long waitTime) throws InterruptedException {
        return tryLock(key, waitTime, LEASE_TIME, UNIT);
    }

    public boolean tryLock(String key) throws InterruptedException {
        return tryLock(key, WAIT_TIME, LEASE_TIME, UNIT);
    }

    public void unLock(RLock lock) {
        if (null != lock && lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }

    }
}
