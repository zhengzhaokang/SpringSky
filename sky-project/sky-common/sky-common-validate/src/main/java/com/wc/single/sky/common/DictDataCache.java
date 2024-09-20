package com.wc.single.sky.common;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.system.domain.SysDictData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DictDataCache {


    private static  Map<String, List<SysDictData>> map;

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private DictDataCache() {
    }

    public static Map<String, List<SysDictData>> getMap() {

        return map;
    }
    public static void put(String key, List<SysDictData> list) {
        writeLock.lock();
        try {
            if (null == map) {
                map = new ConcurrentHashMap<>();
            }
            map.put(key, list);
        }finally {
            writeLock.unlock();
        }
    }

    public static boolean containsKey(String key) {
        if (StrUtil.isBlank(key) || MapUtil.isEmpty(map)) {
            return Boolean.FALSE;
        }
        return   map.containsKey(key);
    }
    public static void clearCache() {
        writeLock.lock();
        try {
            if (!MapUtil.isEmpty(map)) {
                map = null;
            }
        }finally {
            writeLock.unlock();
        }
    }

    public static List<SysDictData> get(String key) {
        readLock.lock();
        try {
            if (StrUtil.isBlank(key) || MapUtil.isEmpty(map)) {
                return new ArrayList<>();
            }
            if (!map.containsKey(key)) {
                return new ArrayList<>();
            }
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }
}
