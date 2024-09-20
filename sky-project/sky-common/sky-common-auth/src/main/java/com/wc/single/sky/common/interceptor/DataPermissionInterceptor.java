package com.wc.single.sky.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.auth.annotation.DataPermissions;
import com.wc.single.sky.common.auth.annotation.GlobalDataPermission;
import com.wc.single.sky.common.auth.table.Table;
import com.wc.single.sky.common.auth.table.TableXmlConfig;
import com.wc.single.sky.common.enums.PermissionType;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.handler.sql.JoinSqlHandler;
import com.wc.single.sky.common.handler.sql.SqlHandler;
import com.wc.single.sky.common.util.AnnotationUtil;
import com.wc.single.sky.common.util.BuildSqlUtil;
import com.wc.single.sky.common.util.DataScopeSpringContextHolder;
import com.wc.single.sky.common.util.UserDataScopeUtil;
import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.domain.SysUserDataScope;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;

/**
 * @author daihuaicai
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Component
public class DataPermissionInterceptor implements Interceptor {

    Logger logger = LoggerFactory.getLogger(DataPermissionInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();
    @Autowired
    UserDataScopeUtil userDataScopeUtil;
    private static final String WHERE = "where";

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        SysUser sysUser = userDataScopeUtil.getLoginUser();
        if ((null != sysUser && sysUser.isAdmin()) || null == sysUser) {
            log.info("data permission is Admin");
             return invocation.proceed();
        }
        // 获取sql信息
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        log.info("原sql为: " + sql);
        // 获取元数据
        MetaObject metaResultSetHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
        MappedStatement mappedStatement = (MappedStatement) metaResultSetHandler.getValue("delegate.mappedStatement");
        String sqlType = mappedStatement.getSqlCommandType().toString();
        //只过滤select查询的其他都不过滤
        if (!sqlType.equals(SqlCommandType.SELECT.toString())) {
            return invocation.proceed();
        }
        //获取调用的方法
        String id = mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        if (methodName.contains("_COUNT")) {
            methodName = methodName.replace("_COUNT", "");
        }
        if (methodName.contains("_mpCount")) {
            methodName = methodName.replace("_mpCount", "");
        }
        log.info("调用方法为: " + id);
        Class<?> clazz = Class.forName(className);
        Method method = getMethod(clazz, methodName);
        Map<String, Object> annotationData = getAnnotationColumn(clazz, method, sql);
        if (null == annotationData || !analyseAnnotationData(annotationData)) {
            return invocation.proceed();
        }
        //重构sql
        String newSql = stitchingStatement(clazz, sql, annotationData, sysUser);
        log.info("重构sql为: " + newSql);
        Class<?> boundClass = boundSql.getClass();
        Field field = boundClass.getDeclaredField("sql");
        ReflectionUtils.makeAccessible(field);
        field.set(boundSql, newSql);
        return invocation.proceed();
    }

    /**
     * 判断是否要继续
     * @param map
     * @return
     */
    private boolean analyseAnnotationData(Map<String, Object> map) {
        if (null == map || StrUtil.isBlank(MapUtils.getString(map, AnnotationUtil.TABLE_NAME))) {
            return Boolean.FALSE;
        }
        String permissionControlType = MapUtils.getString(map, AnnotationUtil.PERMISSION_CONTROL_TYPE);
        if (PermissionType.SQL_INJECTION.getType().equals(permissionControlType)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }



    /**
     *
     * @param clss
     * @param method
     * @param sql 解析sql 默认第一个表名称是主表
     * @return
     */
    private Map<String, Object> getAnnotationColumn(Class<?> clss, Method method,String sql) {
        GlobalDataPermission globalDataPermission = AnnotationUtil.getGlobalDataPermission(clss);
        DataPermissions dataPermissions = AnnotationUtil.getDataPermissionsAnnotation(method);
        if (null == dataPermissions && null == globalDataPermission) {
            //代表没有加注解
            return null;
        }
        return AnnotationUtil.getAnnotationColumn(dataPermissions, globalDataPermission, sql);
    }



    /**
     * 复杂sql有待优化
     *
     * @param sql
     * @return
     */
    private String stitchingStatement(Class<?> clazz, String sql, Map<String, Object> annotationData, SysUser user) {
        if (StrUtil.isBlank(sql)) {
            return sql;
        }
        //获取用户数据权限
        Map<String,List<SysUserDataScope>> dataScope = userDataScopeUtil.getUserDataScope(user,MapUtils.getString(annotationData,
                DataScopeSpringContextHolder.HANDLER_NAME));
        if (null == dataScope || dataScope.size() <= 0) {
            //没有数据权限返回一个空的sql
            return buildNotDataScopeSql(sql);
        }

        boolean isOpenJoin = MapUtils.getBoolean(annotationData, AnnotationUtil.JOIN);
        String tableName = MapUtils.getString(annotationData, AnnotationUtil.TABLE_NAME);
        String alias = MapUtils.getString(annotationData,AnnotationUtil.TABLE_ALIAS);
        //判断是否需要join
        if (isOpenJoin) {
            return buildJoinSql(sql, clazz, tableName, dataScope, alias);
        }else{
            return buildNotJoinSql(sql, tableName, dataScope, alias,annotationData);
        }
    }

    private String buildNotDataScopeSql(String sql) {
        boolean hasWhere = StringUtils.indexOfIgnoreCase(sql, WHERE) == -1 ? Boolean.FALSE : Boolean.TRUE;
        StringBuilder stringBuilder = new StringBuilder(sql);
        if (!hasWhere) {
            stringBuilder.append(" where ");
            stringBuilder.append(" 1=2 ");
        } else{
            stringBuilder.insert(StringUtils.indexOfIgnoreCase(sql, WHERE) + WHERE.length(), " 1=2").toString();
            stringBuilder.insert(StringUtils.indexOfIgnoreCase(stringBuilder.toString(), "1=2") + "1=2".length(), " and ").toString();
        }
        return stringBuilder.toString();
    }

    /**
     * 不需要join 的sql 拼接
     * @param sql
     * @param
     * @param tableName
     * @param dataScope
     * @param alias
     * @return
     */
    private String buildNotJoinSql(String sql,String tableName, Map<String, List<SysUserDataScope>> dataScope, String alias,Map<String,Object> annotationData) {
        boolean hasWhere = StringUtils.indexOfIgnoreCase(sql, WHERE) == -1 ? Boolean.FALSE : Boolean.TRUE;
        boolean openFullTextIndex = MapUtils.getBoolean(annotationData, AnnotationUtil.FULL_TEXT_INDEX);
        userDataScopeUtil.handlerUserDataScope(dataScope, tableName);
        String dataSql = SqlHandler.getWhereSql(dataScope, alias, hasWhere,openFullTextIndex);
        if (StrUtil.isBlank(dataSql)) {
            return sql;
        }
        if (hasWhere && StrUtil.isNotBlank(dataSql)) {
            StringBuilder stringBuilder = new StringBuilder(sql);
            stringBuilder.insert(StringUtils.indexOfIgnoreCase(sql, WHERE) + WHERE.length(), dataSql).toString();
            return stringBuilder.toString();
        }
        if (!hasWhere && StrUtil.isNotBlank(dataSql)) {
            return buildNotHasWhereSql(sql, dataSql, "");
        }
        return sql;
    }
    /**
     * 构建join sql
     * @param sql
     * @param clazz
     * @param tableName
     * @param dataScope
     * @param alias
     * @return
     */
    private  String buildJoinSql(String sql, Class<?> clazz, String tableName, Map<String, List<SysUserDataScope>> dataScope, String alias) {
        if (StrUtil.isBlank(sql) || null == clazz || StrUtil.isBlank(tableName) || null == dataScope) {
            return "";
        }
        Table table = TableXmlConfig.getTableConfig(clazz, tableName);
        if (null == table) {
            throw new QdpException("Please Configure data permissions with table :" + tableName);
        }
        userDataScopeUtil.handlerJoinUserDataScope(dataScope, tableName, table);
        Map<String, Object> condition = JoinSqlHandler.buildJoinCondition(dataScope, table, alias);
        if (null == condition || condition.size() <= 0) {
            return sql;
        }
        String joinSql = MapUtils.getString(condition, BuildSqlUtil.JOIN_SQL);
        String joinCondition = MapUtils.getString(condition, BuildSqlUtil.JOIN_CONDITION);
        boolean hasWhere = StringUtils.indexOfIgnoreCase(sql, WHERE) == -1 ? Boolean.FALSE : Boolean.TRUE;
        if (!hasWhere) {
            return buildNotHasWhereSql(sql, joinCondition, joinSql);
        }else{
            StringBuilder stringBuilder = new StringBuilder(sql);
            stringBuilder.insert(StringUtils.indexOfIgnoreCase(sql, WHERE) - 1, joinSql);
            if (StrUtil.isNotBlank(joinCondition)) {
                stringBuilder.insert(StringUtils.indexOfIgnoreCase(sql, WHERE) + WHERE.length(), joinCondition);
            }
            return stringBuilder.toString();
        }
    }

    /**
     * 拼接sql
     * @param sql
     * @param condition
     * @param joinSql
     * @return
     */
    private static String buildNotHasWhereSql(String sql, String condition,String joinSql) {
        if (StrUtil.isBlank(condition)) {
            return sql;
        }
        Map<String, String> map = SqlHandler.getSuffixSql(sql);
        //后缀
        String suff = map.get(BuildSqlUtil.SUFFIX);
        //前缀
        String pre = map.get(BuildSqlUtil.SQL);
        StringBuilder newSql = new StringBuilder();
        newSql.append(pre);
        if (StrUtil.isNotBlank(joinSql)) {
            newSql.append(joinSql);
        }
        if (StrUtil.isNotBlank(condition)) {
            newSql.append(" where ");
            newSql.append(condition + " ");
        }
        if (StrUtil.isNotBlank(suff)) {
            newSql.append(suff);
        }
        return newSql.toString();
    }

    /**
     *获取方法
     * @param clazz
     * @param methodName
     * @return
     */
    private Method getMethod(Class<?> clazz,String methodName) {
        Method[] methods = clazz.getMethods();
        Method method = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        return method;
    }


    @Override
    public Object plugin(Object target) {
        log.info("数据权限初始化*-*-*-");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        logger.info("数据权限拦截器--");
    }
}
