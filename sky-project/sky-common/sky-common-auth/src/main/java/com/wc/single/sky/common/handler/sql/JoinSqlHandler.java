package com.wc.single.sky.common.handler.sql;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.auth.table.JoinTable;
import com.wc.single.sky.common.auth.table.Table;
import com.wc.single.sky.system.domain.SysUserDataScope;

import java.util.*;

/**
 * join sql
 */
public class JoinSqlHandler extends BaseSqlHandler {


    public  static final String JOIN_SQL = "join_sql";
    public  static final String JOIN_KEY = "join_key";
    public  static final String JOIN_CONDITION = "join_condition";


    /**
     * 构建join 条件
     * @param scopes
     * @param table
     * @return
     */
    public static Map<String,Object> buildJoinCondition(Map<String, List<SysUserDataScope>> scopes, Table table, String annotitionAlias) {
        if (null == scopes || null == table || CollUtil.isEmpty(table.getJoinTables())) {
            return new HashMap<>();
        }
        Set<JoinTable> joinTables = getJoinTables(table);
        String alias = StrUtil.isBlank(table.getAlias()) ? annotitionAlias : table.getAlias();
        Map<String, Object> joinMap = new HashMap<>();
        StringBuilder joinSql = new StringBuilder();
        StringBuilder joinCondition = new StringBuilder();
        Set<String> joinKeys = new HashSet<>();
        int count = 0;
        for (JoinTable joinTable : joinTables) {
            if (null == joinTable || StrUtil.isBlank(joinTable.getJoinTableName()) || StrUtil.isBlank(joinTable.getJoinKey()) || StrUtil.isBlank(joinTable.getDimension())) {
                continue;
            }
            String dimension = joinTable.getDimension();
            String[] dimesions = dimension.split(",");
            for (String di : dimesions) {
                if (StrUtil.isNotBlank(dimension) && !isSelectAll(scopes.get(di))) {
                    if (count > 0) {
                        joinCondition.append(" and ");
                    }
                    joinCondition.append(buildInCondition(scopes.get(di), di, joinTable.getJoinAlias()));
                    //join过的 就把这个维度删除掉防止重复拼接条件
                    removeMap(scopes, di);
                    count++;
                }
            }
          /*  joinSql.append(getJoinSql(joinTable, alias));
            joinKeys.add(joinTable.getJoinKey());*/
        }
        //把不需要join的也拼接进去
        if (scopes.size() > 0) {
            appendNotNeedJoin(joinCondition, scopes, alias);
        }
        joinMap.put(JOIN_SQL, joinSql.toString());
        joinMap.put(JOIN_KEY, joinKeys);
        joinMap.put(JOIN_CONDITION, joinCondition.toString());
        return joinMap;
    }

    private static Set<JoinTable> getJoinTables(Table table) {
        JoinTable joinTable = new JoinTable();
        joinTable.setJoinTableName(table.getName());
        joinTable.setJoinAlias(table.getAlias());
        joinTable.setDimension(table.getDimension());
        joinTable.setJoinKey(table.getJoinKey());
        Set<JoinTable> joinTables = table.getJoinTables() == null ? new HashSet<>() : table.getJoinTables();
        joinTables.add(joinTable);
        return joinTables;
    }

    private static void appendNotNeedJoin(StringBuilder joinCondition, Map<String, List<SysUserDataScope>> scopes, String alias) {
        for (Map.Entry<String, List<SysUserDataScope>> entry : scopes.entrySet()) {
            if (joinCondition.length() > 0) {
                joinCondition.append(" and ");
                joinCondition.append(buildInCondition(entry.getValue(), entry.getKey(), alias));
            }
        }
    }

    /**
     * 拼接 join sql
     * @param joinTable
     * @param alias
     * @return
     */
    private static String getJoinSql(JoinTable joinTable, String alias) {
        StringBuilder joinSql = new StringBuilder();
        joinSql.append(" inner join ");
        joinSql.append(" " + joinTable.getJoinTableName());
        joinSql.append(" " + joinTable.getJoinAlias());
        joinSql.append(" on ");
        if (StrUtil.isNotBlank(alias)) {
            joinSql.append(" " + alias + ".");
        }
        joinSql.append(joinTable.getJoinKey());
        joinSql.append(" = ");
        joinSql.append(joinTable.getJoinAlias() + ".");
        joinSql.append(joinTable.getJoinKey());
        return joinSql.toString();
    }
}
