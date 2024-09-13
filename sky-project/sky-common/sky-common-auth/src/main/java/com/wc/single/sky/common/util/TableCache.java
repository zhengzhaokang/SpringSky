package com.wc.single.sky.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.system.domain.dto.GenTableColumnDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TableCache {

    private static Map<String, List<GenTableColumnDTO>> map;

    private TableCache() {
    }

    public static boolean put(String tableName, List<GenTableColumnDTO> data) {
        if (StrUtil.isBlank(tableName) || CollUtil.isEmpty(data)) {
            return Boolean.FALSE;
        }
        if (MapUtil.isEmpty(map)) {
            map = new ConcurrentHashMap<>();
        }
        map.put(tableName, data);
        return Boolean.TRUE;
    }

    public static List<GenTableColumnDTO> get(String tableName) {
        if (MapUtil.isEmpty(map)) {
            return new ArrayList<>();
        }
        return map.get(tableName);
    }

    public static void clearAll() {
        map = null;
    }
}
