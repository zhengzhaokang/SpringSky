package com.wc.single.sky.common.handler.sql;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.system.domain.SysUserDataScope;

import java.util.*;
import java.util.stream.Collectors;
public class BaseSqlHandler {



    public static final String SALES_ORG = "sales_org_code";
    public static final String SALES_OFFICE = "sales_office_code";
    public static final String SEGMENT = "segment_code";
    public static final String GTN_TYPE = "gtn_type_code";
    //可以为null的维度
    protected static final Set<String> CAN_BE_NULL_SET = new LinkedHashSet<>();
    static {
        CAN_BE_NULL_SET.add(SEGMENT);
        CAN_BE_NULL_SET.add(SALES_ORG);
        CAN_BE_NULL_SET.add(GTN_TYPE);
        CAN_BE_NULL_SET.add(SALES_OFFICE);
    }

    public static Set<String> getCanBeNullSet() {
        return CAN_BE_NULL_SET;
    }
    public  static String strToDbin(String str){

        return String.format("'%s'", org.apache.commons.lang3.StringUtils.join(str.split(","),"','"));

    }

    public  static String formatSqlVal(String str) {

        return String.format("'%s'", str, "','");
    }

    /**
     * 判断是否全选
     * @param sysUserDataScopes
     * @return
     */
    public  static boolean isSelectAll(List<SysUserDataScope> sysUserDataScopes) {
        if (CollUtil.isEmpty(sysUserDataScopes)) {
            return false;
        }
        return isSelectAll(sysUserDataScopes.get(0));
    }

    public static boolean isSelectAll(SysUserDataScope scope) {
        String selectAll = scope == null || scope.getSelectAll() == null ? "1" : scope.getSelectAll();
        if (selectAll.equals("0")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    /**
     * 拼接允许为null的条件
     * @param scopes
     * @param key
     * @return
     */
    public  static String isNullSql(List<SysUserDataScope> scopes, String key) {
        if (CollUtil.isEmpty(scopes)) {
            return "";
        }
        StringBuilder isNullSql = new StringBuilder("");
        SysUserDataScope scope = scopes.get(0);
        if (null != scope && scope.getCanBeNull()) {
            isNullSql.append(" or ");
            isNullSql.append(" " + key);
            isNullSql.append(" is null");
            isNullSql.append(" or ");
            isNullSql.append(" " + key);
            isNullSql.append(" = ");
            isNullSql.append(" " + "'");
            isNullSql.append("" + "'");
        }
        return isNullSql.toString();
    }
    /**
     * 构建in 条件
     * @param dataScopes
     * @param key
     * @param alias
     * @return
     */
    protected static String buildInCondition(List<SysUserDataScope> dataScopes, String key,String alias) {
        StringBuilder sql = new StringBuilder();
        if (StrUtil.isNotBlank(alias)) {
            sql.append(" " + alias + ".");
            sql.append(key);
        } else if (StrUtil.isBlank(alias)) {
            sql.append(" " + key);
        }
        sql.append(" in (");
        String tempValue= dataScopes.stream().map(tempDataScope -> tempDataScope.getDataScopeValue().trim()).collect(Collectors.joining(","));
        sql.append(strToDbin(tempValue));
        sql.append(") ");
        return sql.toString();
    }

    /**
     * 删除map的某个key
     * @param map
     * @param key
     */
    protected static void removeMap(Map<String, List<SysUserDataScope>> map, String key) {
        if (null == map || StrUtil.isBlank(key)) {
            return;
        }
        Iterator<Map.Entry<String, List<SysUserDataScope>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<SysUserDataScope>> next = iterator.next();
            if (next.getKey().equals(key)) {
                iterator.remove();
            }
        }
    }

}
