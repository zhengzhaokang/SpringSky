package com.wc.single.sky.common.redis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wc.single.sky.common.redis.config.FastjsonCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
    protected final Log logger = LogFactory.getLog(getClass());


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60L * 60L * 24L;
    public static final long   ONE_WEEK     = 7 * 60L * 60L * 24L;;

    /**
     * 插入缓存默认时间
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 插入缓存
     *
     * @param key    键
     * @param value  值
     * @param expire 过期时间(s)
     */
    public void set(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 插入缓存
     *
     * @param key      键
     * @param value    值
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value, expire, timeUnit);
    }

    /**
     * key不存在时，插入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean setNx(String key, Object value) {
        return setNx(key, value, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * key不存在时，插入缓存
     *
     * @param key    键
     * @param value  值
     * @param expire 过期时间（秒）
     * @return
     */
    public Boolean setNx(String key, Object value, long expire) {
        return setNx(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * key不存在时，插入缓存
     *
     * @param key      键
     * @param value    值
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    public Boolean setNx(String key, Object value, long expire, TimeUnit timeUnit) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        return bucket.setIfAbsent(value, Duration.of(timeUnit.toNanos(expire), ChronoUnit.NANOS));
    }


    /**
     * key存在时，插入缓存
     */
    public Boolean setIfPresent(String key, Object value, long expire, TimeUnit timeUnit) {
        RBucket<Object> bucket = redissonClient.getBucket(key);

        return bucket.setIfExists(value, expire, timeUnit);
    }

    public void put(String h, String hk, Object hv) {
        put(h, hk, hv, DEFAULT_EXPIRE);
    }

    public void put(String h, String hk, Object hv, long expire) {
        RMap<Object, Object> rMap = redissonClient.getMap(h, StringCodec.INSTANCE);
        rMap.put(hk, JSON.toJSONString(hv, SerializerFeature.WriteClassName));
        rMap.expire(Duration.of(expire, ChronoUnit.SECONDS));
    }

    public void putAll(String h, Map<String, Object> map) {
        putAll(h, map, DEFAULT_EXPIRE);
    }

    public void putAll(String h, Map<String, Object> map, long expire) {
        RMap<Object, Object> rMap = redissonClient.getMap(h, StringCodec.INSTANCE);
        map.forEach((s, o) -> rMap.put(s, JSON.toJSONString(o, SerializerFeature.WriteClassName)));
        rMap.expire(Duration.of(expire, ChronoUnit.SECONDS));
    }

    public void add(String key, Object... values) {
        add(DEFAULT_EXPIRE, key, values);
    }

    public Boolean add(long expire, String key, Object... values) {
        RSet set = redissonClient.getSet(key);
        boolean b = set.addAll(Arrays.stream(values)
                .collect(Collectors.toList()));
        set.expire(Duration.of(expire, ChronoUnit.SECONDS));
        return b;
    }

    public void addList(String key, Object... values) {
        addList(DEFAULT_EXPIRE, key, values);
    }

    public Boolean addList(long expire, String key, Object... values) {
        RList<Object> list = redissonClient.getList(key);
        boolean b = list.addAll(Arrays.stream(values)
                .collect(Collectors.toList()));
        list.expire(Duration.of(expire, ChronoUnit.SECONDS));
        return b;
    }

    public void addAllList(String key, Object... values) {
        addAllList(DEFAULT_EXPIRE, key, values);
    }

    public Boolean addAllList(long expire, String key, Object... values) {
        RList<Object> list = redissonClient.getList(key);
        boolean b = list.addAll(Arrays.stream(values)
                .collect(Collectors.toList()));
        list.expire(Duration.of(expire, ChronoUnit.SECONDS));
        return b;
    }

    public Long increment(String key, long delta) {
        return increment(key, delta, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    public Long increment(String key, long delta, long expire, TimeUnit timeUnit) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        long l = atomicLong.addAndGet(delta);
        atomicLong.expire(Duration.of(timeUnit.toNanos(expire), ChronoUnit.NANOS));
        return l;
    }

    public Long decrement(String key, long delta) {
        return decrement(key, delta, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    public Long decrement(String key, long delta, long expire, TimeUnit timeUnit) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        long l = atomicLong.addAndGet(-delta);
        atomicLong.expire(Duration.of(timeUnit.toNanos(expire), ChronoUnit.NANOS));
        return l;
    }

    /**
     * 返回字符串结果
     * 想拿字符串 就的放字符串
     *
     * @param key 键
     * @return
     */
    @Deprecated
    public String get(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key, FastjsonCodec.INSTANCE);
        if (bucket.get() == null) {
            return null;
        }
        return bucket.get().toString();
    }

    public void hDelete(String key) {
        redissonClient.getMap(key, StringCodec.INSTANCE)
                .delete();
    }

    /**
     * 返回指定类型结果
     *
     * @param key   键
     * @param clazz 类型class
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        String s = bucket.get();
        return fromJson(s, clazz);
    }

    public List<String> getList(String key) {
        List<Object> list = redissonClient.getList(key, StringCodec.INSTANCE).readAll();
        return list.stream().map(o -> (String) o).collect(Collectors.toList());
    }

    public List<String> mGet(List<String> keys) {
        RBuckets buckets = redissonClient.getBuckets(StringCodec.INSTANCE);
        Map<String, String> map = buckets.get(keys.toArray(new String[0]));
        return new ArrayList<>(map.values());
    }

    @Deprecated
    public <T> List<T> getList(String key, Class<T> clazz) {
        RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        String s = bucket.get();
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return JSONArray.parseArray(s, clazz);
    }

    public Object get(String h, String hk) {
        return JSON.parseObject((String) redissonClient.getMap(h, StringCodec.INSTANCE)
                .get(hk), Object.class, Feature.SupportAutoType);
    }

    public Map<String, Object> entries(String key) {
        Map<Object, Object> allMap = redissonClient.getMap(key, StringCodec.INSTANCE).readAllMap();
        Map<String, Object> map = new HashMap<>();
        allMap.forEach((o, o2) -> map.put(o.toString(), JSON.parseObject(o2.toString(), Object.class, Feature.SupportAutoType)));
        return map;
    }

    public <T> Map<String, T> entries(String key, Class<T> clazz) {
        Map<Object, Object> allMap = redissonClient.getMap(key, StringCodec.INSTANCE).readAllMap();
        Map<String, T> map = new HashMap<>();
        allMap.forEach((o, o2) -> map.put(o.toString(), JSON.parseObject(o2.toString(), clazz, Feature.SupportAutoType)));
        return map;
    }

    public Set<Object> members(String key) {
        return redissonClient.getSet(key).readAll();
    }

    public boolean isMember(String key, Object value) {
        return redissonClient.getSet(key).contains(value);
    }

    public Set<Object> intersect(Collection<String> keys) {
        RSet<Object> set = redissonClient.getSet(keys.iterator().next());
        return set.readIntersection(keys.toArray(new String[0]));
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void delete(String key) {
        redissonClient.getKeys().delete(key);
    }

    public boolean isContains(String key) {
        return redissonClient.getKeys().countExists(key) > 0;
    }

    /**
     * * 这个性能比较好
     *
     * @param key
     * @return
     */
    public boolean isExists(String key) {
        return redissonClient.getKeys().countExists(key) > 0;
    }

    /**
     * 删除缓存
     *
     * @param keys 键集合
     */
    public void delete(Collection<String> keys) {
        redissonClient.getKeys().delete(keys.toArray(new String[0]));
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public Object execute(DefaultRedisScript<Object> redisScript, List<String> keyList, Object... args) {
        return redisTemplate.execute(redisScript, keyList, args);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return redissonClient.getKeys().countExists(key) > 0;
    }


    public void leftPush(String key, Object obj) {
        redissonClient.getDeque(key).addFirst(obj);
    }


    public void send(String channel, Object obj) {
        redisTemplate.convertAndSend(channel, obj);
    }

    /**
     * 删除缓存
     *
     * @param pattern 键集合
     */
    public void pipeline(String pattern) {
        String param = pattern + "*";
        Set<String> keys = getValuesForStringByScan(param);
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (String key : keys) {
                connection.del(key.getBytes());
            }
            return null;
        });
    }

    /**
     * 获取匹配的所有key，使用scan避免阻塞
     *
     * @param patten 匹配keys的规则
     * @return 返回获取到的keys
     */
    public Set<String> getValuesForStringByScan(String patten) {
        Set<String> res = new HashSet<>();
        redisTemplate.execute(connect -> {
            Set<String> binaryKeys = new HashSet<>();
            Cursor<byte[]> cursor = connect.scan(new ScanOptions.ScanOptionsBuilder().match(patten).count(10000).build());
            while (cursor.hasNext() && binaryKeys.size() < 10000) {
                binaryKeys.add(new String(cursor.next()));
            }
            res.addAll(binaryKeys);
            return binaryKeys;
        }, true);
        return res;
    }
}