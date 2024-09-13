package com.wc.single.sky.common.util;


import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.handler.sql.BaseSqlHandler;
import com.wc.single.sky.system.domain.SysUserDataScope;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dhc
 * *
 */
public class BuildSqlUtil extends BaseSqlHandler {
    public  static final String SQL = "sql";
    public  static final String SUFFIX = "suff";
    private static final String LIMIT = "limit";
    private static final String GROUP_BY = "group by";
    private static final String ORDER_BY = "order by";

    public  static final String JOIN_SQL = "join_sql";
    public  static final String JOIN_KEY = "join_key";
    public  static final String JOIN_CONDITION = "join_condition";
    public static final String GTN_TYPE_CODE = "gtn_type_code";
    public static final String AND = " and ";


    /**
     * 截取sql 后缀
     * @param sql
     * @return
     */
    public  static Map<String,String> getSuffixSql(String sql) {
        Map<String, String> sqlMap = new HashMap<>();
        // 对sql中的limit和order by做处理
        String suffixSql = "";
        int lastGroupByIndex = sql.lastIndexOf(GROUP_BY);
        if (lastGroupByIndex > 0) {
            suffixSql = sql.substring(lastGroupByIndex);
            sql = sql.substring(0, lastGroupByIndex);
        }
        int lastOrderByIndex = sql.lastIndexOf(ORDER_BY);
        if (lastOrderByIndex > 0) {
            suffixSql = sql.substring(lastOrderByIndex);
            sql = sql.substring(0, lastOrderByIndex);
            // 如果suffixSql中包含select，就无法确定加上数据权限sql之后是正确的，这里退出sql处理
            if (suffixSql.indexOf("select") > -1) {
                throw new QdpException("add controller sql error");
            }
        }
        // 对sql中的limit做处理
        int lastLimitIndex = sql.lastIndexOf(LIMIT);
        if (lastLimitIndex > 0 && "".equals(suffixSql)) {
            suffixSql = sql.substring(lastLimitIndex);
            sql = sql.substring(0, lastLimitIndex);
            // 如果suffixSql中包含? ，空格 之外的其他字符，就无法确定加上数据权限sql之后是正确的，这里退出sql处理
            char[] arr = suffixSql.toCharArray();
            for (int i = 5; i < arr.length; i++) {
                if (arr[i] != 44 && arr[i] != 32 && arr[i] != 63) {
                    throw new QdpException("add control sql error");
                }
            }
        }
        sqlMap.put(SQL, sql);
        sqlMap.put(SUFFIX, suffixSql);
        return sqlMap;
    }


    /**
     * 有where条件的sql
     *
     * @param scopes
     * @param alias
     * @param hasWhere
     * @return
     */
    public static String getWhereSql(Map<String, List<SysUserDataScope>> scopes, String alias, boolean hasWhere, boolean openIndex) {
        StringBuilder sql = new StringBuilder();
        Iterator<Map.Entry<String, List<SysUserDataScope>>> iterator = scopes.entrySet().iterator();
        int fieldCount = 0;
        while (iterator.hasNext()) {
            if (fieldCount > 0) {
                sql.append(AND);
            }
            Map.Entry<String, List<SysUserDataScope>> entry = iterator.next();
            if (isSelectAll(entry.getValue())) {
                continue;
            }
            if (!openIndex) {
                sql.append(buildInCondition(entry.getValue(), entry.getKey(), alias));
            } else {
                sql.append(buildFullTextIndexSql(entry.getValue(), entry.getKey(), alias));
            }
            fieldCount++;
        }
        if (hasWhere && StrUtil.isNotBlank(sql.toString())) {
            sql.append(AND);
        }
        return sql.toString();
    }







    /**
     * 构建全文索引条件
     * @param dataScopes
     * @param key
     * @param alias
     * @return
     */
    private static String buildFullTextIndexSql(List<SysUserDataScope> dataScopes, String key,String alias) {
        Set<String> dataScopeValues = dataScopes.stream().map(item -> item.getDataScopeValue()).collect(Collectors.toSet());
        StringBuilder sql = new StringBuilder();
        int varIndex = 0;
        if (null != dataScopeValues) {
            if (dataScopeValues.size() > 1) {
                sql.append(" ( ");
            }
            for (String value : dataScopeValues) {
                if (StrUtil.isBlank(value)) {
                    continue;
                }
                if (varIndex > 0) {
                    sql.append(" or ");
                }
                //不去空格会报错
                value = value.replace(" ", "");
                sql.append(buildFullTextIndexSql(key, alias, value));
                varIndex++;
            }
            if (dataScopeValues.size() > 1) {
                sql.append(" ) ");
            }
        }
        return sql.toString();
    }

    /**
     * 构建单个key
     * @param key
     * @param alias
     * @param value
     * @return
     */
    private  static String buildFullTextIndexSql(String key,String alias,String value) {
        StringBuilder sql = new StringBuilder();
        sql.append(" to_tsvector('english', ");
        sql.append(buildKey(key, alias));
        sql.append(")");
        sql.append(" @@ ");
        sql.append(" to_tsquery('english',");
        sql.append(formatSqlVal(value));
        sql.append(")");
        return sql.toString();
    }
    /**
     * 构建条件的key
     * @param key
     * @param alias
     * @return
     */
    private static String buildKey(String key, String alias) {
        StringBuilder sql = new StringBuilder();
        if (StrUtil.isNotBlank(alias)) {
            if(GTN_TYPE_CODE.equals(key)){
                sql.append(" replace(");
            }
            sql.append(" " + alias + ".");
            sql.append(key);
            if (GTN_TYPE_CODE.equals(key)) {
                sql.append(",' ','')");
            }
        } else if (StrUtil.isBlank(alias)) {
            if(GTN_TYPE_CODE.equals(key)){
                sql.append(" replace(");
            }
            sql.append(" " + key);
            if(GTN_TYPE_CODE.equals(key)){
                sql.append(",' ','')");
            }
        }
        return sql.toString();
    }






}
