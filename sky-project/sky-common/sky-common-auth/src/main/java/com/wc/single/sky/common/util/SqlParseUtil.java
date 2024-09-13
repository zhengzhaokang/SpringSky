package com.wc.single.sky.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * sql解析器
 */
public class SqlParseUtil {

    static Logger logger = LoggerFactory.getLogger(SqlParseUtil.class);
    private SqlParseUtil() {
    }

    /**
     * 获取第一个表的名称
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    public static  String  getFirstTableName(String sql) throws JSQLParserException {
        if (StrUtil.isBlank(sql)) {
            return "";
        }
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        if (CollUtil.isEmpty(tableList)) {
            return "";
        }
        return tableList.get(0);
    }

    // 获取表及对应的别名
    public static Map<String,String >  getTableAlias(String sql){
        Map<String,String> map = new LinkedHashMap<>();
        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            if (null == selectBody) {
                return map;
            }
            PlainSelect plainSelect = (PlainSelect) selectBody;
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem == null || fromItem.getClass().isAssignableFrom(SubSelect.class)) {
                return map;
            }
            Table table = (Table) plainSelect.getFromItem();
            ;
            if (fromItem != null && fromItem.getAlias() != null) {
                map.put(table.getName(), table.getAlias().getName());
            }
            List<Join> joins = plainSelect.getJoins();
            if (CollUtil.isNotEmpty(joins)) {
                for (Join join : joins) {
                    Table table1 = (Table) join.getRightItem();
                    if (table1.getAlias() != null) {
                        map.put(table1.getName(), table1.getAlias().getName());
                    }
                }
            }
        }catch (Exception e){
            logger.error("解析sql异常", e);
        }
        return map;
    }

    public static String getFirstTableAlias(String sql) throws JSQLParserException {
        String firstTableName = getFirstTableName(sql);
        if (StrUtil.isBlank(firstTableName)) {
            return "";
        }
        Map<String, String> map = getTableAlias(sql);
        if (CollUtil.isEmpty(map)) {
            return "";
        }
        return map.get(firstTableName);
    }


}
