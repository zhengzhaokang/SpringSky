package com.wc.single.sky.common.handler.sql;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.system.domain.SysUserDataScope;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全文索引sql
 */
public class FullTextSqlHandler extends BaseSqlHandler {


    private static final String GTN_TYPE_CODE = "gtn_type_code";

    /**
     * 构建全文索引条件
     * @param dataScopes
     * @param key
     * @param alias
     * @return
     */
    public  static String buildFullTextIndexSql(List<SysUserDataScope> dataScopes, String key, String alias) {
        Set<String> dataScopeValues = dataScopes.stream().map(SysUserDataScope::getDataScopeValue).collect(Collectors.toSet());
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
                sql.append(isNullSql(dataScopes, key));
                //拼接 为null的条件
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
