package com.wc.single.sky.common.util;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.auth.annotation.DataPermissions;
import com.wc.single.sky.common.auth.annotation.GlobalDataPermission;
import net.sf.jsqlparser.JSQLParserException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解处理
 */
public class AnnotationUtil{

    private AnnotationUtil() {
    }

    public static final String TABLE_NAME = "tableName";
    public static final String TABLE_ALIAS="tableAlias";
    public static final String JOIN = "openJoin";
    public static final String PERMISSION_CONTROL_TYPE = "permission_type";
    public static final String DB_NAME = "dbName";
    public static final String DIMENSION_NAME = "dimensionName";
    public static final String FULL_TEXT_INDEX = "full_text";
    public static final String HANDLER_NAME = "handlerName";

    public static  Annotation getAnnotationByArray(Annotation[] annotations, Class claz) {
        for (Annotation annotation : annotations) {
            if (null == annotation || annotation.annotationType() == null) {
                continue;
            }
            if (annotation.annotationType().equals(claz)) {
                return annotation;
            }
        }
        return null;
    }

    public static  Annotation getAnnotationsByClass(Method method, Class claz) {
        Annotation[] annotations = method.getAnnotations();
        if (null == annotations || annotations.length <= 0) {
            return null;
        }
        return getAnnotationByArray(annotations, claz);
    }

    /**
     * 获取方法上的权限注解
     * @param method
     * @return
     */
    public static DataPermissions getDataPermissionsAnnotation(Method method) {
        Annotation annotation = getAnnotationsByClass(method, DataPermissions.class);
        DataPermissions dataPermissions = null;
        if (null != annotation) {
            dataPermissions = (DataPermissions) annotation;
        }
        return dataPermissions;
    }

    /**
     *
     * @param claz 目标class文件
     *
     * @return
     */
    public static  GlobalDataPermission getGlobalDataPermission(Class claz) {
        Annotation annotation = getAnnotationByArray(claz.getAnnotations(), GlobalDataPermission.class);
        GlobalDataPermission globalDataPermission = null;
        if (null != annotation) {
            globalDataPermission = (GlobalDataPermission) annotation;
        }
        return globalDataPermission;
    }

    /**
     *
     * @param dataPermissions
     * @param globalDataPermission
     * @return
     */
    public static Map<String, Object> getAnnotationColumn(DataPermissions dataPermissions, GlobalDataPermission globalDataPermission,String sql) {
        if (null == dataPermissions && globalDataPermission == null) {
            return new HashMap<>();
        }
        Map<String, Object> map = new HashMap<>();
        String tableName = "";
        String tableAlias = "";
        if (dataPermissions != null) {
            tableName = dataPermissions.tableName();
            tableAlias = dataPermissions.tableAlias();
            map.put(JOIN, dataPermissions.openJoin());
            map.put(PERMISSION_CONTROL_TYPE, dataPermissions.permissionType().getType());
            map.put(FULL_TEXT_INDEX, dataPermissions.openFullTextIndex());
            map.put(HANDLER_NAME, dataPermissions.handlerName());
        } else {
            tableName = globalDataPermission.tableName();
            tableAlias = globalDataPermission.tableAlias();
            map.put(JOIN, globalDataPermission.openJoin());
        }
        if (StrUtil.isBlank(tableName) && StrUtil.isNotBlank(sql)) {
            try {
                //默认sql 第一个表名称
                tableName = SqlParseUtil.getFirstTableName(sql);
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        if (StrUtil.isBlank(tableAlias)&&StrUtil.isNotBlank(sql)) {
            try {
                //默认第一个表的别名
                tableAlias = SqlParseUtil.getFirstTableAlias(sql);
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        map.put(TABLE_NAME, tableName);
        map.put(TABLE_ALIAS, tableAlias);
        return map;
    }

}
