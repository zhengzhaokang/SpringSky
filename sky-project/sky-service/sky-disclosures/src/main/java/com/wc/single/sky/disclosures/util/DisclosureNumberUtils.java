package com.wc.single.sky.disclosures.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DisclosureNumberUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        return dateFormat.format(now);
    }

    public String generateDisclosureNum(int length, String type) {
        String dateTime = getNowTime("yyyyMMdd");
        return type + dateTime + generateNum(dateTime, length, type);
    }

    public String generateNum(String keyName, int length, String type) {
        Integer serialNumber = getSerialNumber(keyName, type);
        return String.format("%0" + length + "d", serialNumber);
    }

    public Integer getSerialNumber(String keyName, String type) {
        keyName = "SerialNumber:" + type + ":" + keyName;
        try {
            RedisAtomicLong entityIdCounter = new RedisAtomicLong(keyName, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
            long incr = entityIdCounter.getAndIncrement();
            if (incr == 0) {// 初始设置过期时间
                // 设置过期时间 24小时
                entityIdCounter.expire(24, TimeUnit.HOURS);
                // 这里取第二次 incr 就是从1开始了,默认从0开始
                incr = entityIdCounter.getAndIncrement();
            }
            log.info(keyName + "==========" + incr);
            return (int) incr;
        } catch (Exception e) {
            log.error("======redis increment======", e);
        }
        return null;
    }
}
