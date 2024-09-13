package com.wc.single.sky.common.auth.table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.utils.XmlUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取table的配置
 */
public class TableXmlConfig {

    private TableXmlConfig() {
    }

    private static Map<String, Table> tableMap = new ConcurrentHashMap<>();

    /***
     * 获取配置
     * @param clazz
     * @param tableName
     * @return
     */
    public static Table getTableConfig(Class<?> clazz, String tableName) {
        if (StrUtil.isBlank(tableName) || null == clazz) {
            return null;
        }
        if (tableMap.containsKey(tableName)) {
            return tableMap.get(tableName);
        }else{
            try {
                List<Map<String,Object>> list = XmlUtils.parseXml(clazz, "data_permission.xml");
                if (CollUtil.isEmpty(list)) {
                    return null;
                }
                for (Map<String, Object> map : list) {
                    setTableMap(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tableMap.get(tableName);
    }

    private static void setTableMap(Map<String, Object> map) {
        Object table = map.get("tableInfo");
        Object joinTable = map.get("joinInfo");
        if (null != table) {
            Table tb = BeanUtil.mapToBean((Map<String, String>) table, Table.class, true);
            if (null != joinTable && null != tb) {
                Set<JoinTable> joinTables = new LinkedHashSet<>();
                List<Map<String, String>> jTables = (List<Map<String, String>>) joinTable;
                if (CollUtil.isNotEmpty(jTables)) {
                    for (Map<String, String> m : jTables) {
                        JoinTable jTable = BeanUtil.mapToBean(m, JoinTable.class, true);
                        joinTables.add(jTable);
                    }
                }
                tb.setJoinTables(joinTables);
            }
            if (null != tb && StrUtil.isNotBlank(tb.getName())) {
                tableMap.put(tb.getName(), tb);
            }
        }
    }
}
