package com.wc.single.sky.common.handler.sql;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.system.domain.SysUserDataScope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author dhc
 */
public class SqlHandler extends BaseSqlHandler {
    public  static final String SQL = "sql";
    public  static final String SUFFIX = "suff";
    private static final String LIMIT = "limit";
    private static final String GROUP_BY = "group by";
    private static final String ORDER_BY = "order by";

    public  static final String JOIN_SQL = "join_sql";
    public  static final String JOIN_KEY = "join_key";
    public  static final String JOIN_CONDITION = "join_condition";


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
            //  判断suffixSql是否合法需要优化
            if (suffixSql.indexOf("select") >= 0) {
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
     * @param scopes
     * @param alias
     * @param hasWhere
     * @return
     */
    public  static String getWhereSql(Map<String, List<SysUserDataScope>> scopes, String alias, boolean hasWhere, boolean openIndex) {
        StringBuilder sql = new StringBuilder();
        Iterator<Map.Entry<String,List<SysUserDataScope>>> iterator=scopes.entrySet().iterator();
        int fieldCount = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, List<SysUserDataScope>> entry = iterator.next();
            if (isSelectAll(entry.getValue())) {
                continue;
            }
            if (fieldCount > 0) {
                sql.append(" and ");
            }
            if (!openIndex) {
                sql.append("(");
                sql.append(buildInCondition(entry.getValue(), entry.getKey(), alias));
                //拼接 or  为null的条件
                sql.append(isNullSql(entry.getValue(), entry.getKey()));
                sql.append(")");
            } else {
                sql.append(FullTextSqlHandler.buildFullTextIndexSql(entry.getValue(), entry.getKey(), alias));
            }
            fieldCount++;
        }
        if (hasWhere && StrUtil.isNotBlank(sql.toString())) {
            sql.append(" and ");
        }
        return sql.toString();
    }



}
