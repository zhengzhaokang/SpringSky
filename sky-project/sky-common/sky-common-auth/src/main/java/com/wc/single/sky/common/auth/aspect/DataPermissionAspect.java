
package com.wc.single.sky.common.auth.aspect;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.auth.annotation.DataPermissions;
import com.wc.single.sky.common.auth.table.Table;
import com.wc.single.sky.common.auth.table.TableXmlConfig;
import com.wc.single.sky.common.core.domain.BaseDTO;
import com.wc.single.sky.common.enums.PermissionType;
import com.wc.single.sky.common.handler.sql.JoinSqlHandler;
import com.wc.single.sky.common.handler.sql.SqlHandler;
import com.wc.single.sky.common.util.AnnotationUtil;
import com.wc.single.sky.common.util.BuildSqlUtil;
import com.wc.single.sky.common.util.UserDataScopeUtil;
import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.domain.SysUserDataScope;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 数据权限 注入sql方式实现 这种模式下 需要自己处理逻辑
 *
 * @author daihuaicai
 */

@Aspect
@Component
public class DataPermissionAspect {

    @Autowired
    UserDataScopeUtil userDataScopeUtil;

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";
    /**
     * 数据权限存在 join 的情况 必须注入两个条件  where 前边是joinTable where 后边是 条件
     */
    public static final String JOIN_TABLE = "joinTableName";

    /**
     * @param point
     * @throws Throwable
     */
    @Before("@annotation(com.wc.single.sky.common.auth.annotation.DataPermissions)")
    public void around(JoinPoint point) throws Throwable {
        SysUser sysUser = userDataScopeUtil.getLoginUser();
        if (null == sysUser || sysUser.isAdmin()) {
            return;
        }
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        //如果类型不是BaseDTO的就不执行下边的逻辑
        if (!validParamType(((MethodSignature) signature).getParameterTypes())) {
            return;
        }
        DataPermissions annotation = method.getAnnotation(DataPermissions.class);
        if (null == annotation) {
            return;
        }
        Map<String, Object> annotationData = AnnotationUtil.getAnnotationColumn(annotation, null, "");
        if (MapUtil.isEmpty(annotationData)) {
            return;
        }
        //判断数据权限的控制类型
        PermissionType permissionType = annotation.permissionType();
        if (permissionType != PermissionType.SQL_INJECTION) {
            return;
        }
        BaseDTO baseDTO = getBaseDTO(point);
        if (null == baseDTO) {
            return;
        }
        Map<String, List<SysUserDataScope>> dataScope = userDataScopeUtil.getUserDataScope(sysUser, MapUtils.getString(annotationData, "handlerName"));
        String tableName = MapUtils.getString(annotationData, AnnotationUtil.TABLE_NAME);
        String alias = MapUtils.getString(annotationData, AnnotationUtil.TABLE_ALIAS);
        if (StrUtil.isBlank(tableName)) {
            return;
        }
        boolean isOpenJoin = MapUtils.getBoolean(annotationData, AnnotationUtil.JOIN);
        if (!isOpenJoin) {
            setDataScopeIsNotJoin(baseDTO, dataScope, tableName, alias, annotationData);
        } else {
            Class<?> clazz = point.getTarget().getClass();
            Table table = TableXmlConfig.getTableConfig(clazz, tableName);
            if (null == table || CollectionUtils.isEmpty(table.getJoinTables())) {
                return;
            }
            setDataScopeIsJoin(baseDTO, dataScope, tableName, alias, table);
        }
    }


    private void setDataScopeIsJoin(BaseDTO baseDTO, Map<String, List<SysUserDataScope>> dataScope, String tableName, String alias, Table table) {
        userDataScopeUtil.handlerJoinUserDataScope(dataScope, tableName, table);
        Map<String, Object> condition = JoinSqlHandler.buildJoinCondition(dataScope, table, alias);
        if (MapUtil.isEmpty(condition)) {
            return;
        }
        String joinSql = MapUtils.getString(condition, BuildSqlUtil.JOIN_SQL);
        String joinCondition = MapUtils.getString(condition, BuildSqlUtil.JOIN_CONDITION);
        baseDTO.getParams().put(JOIN_TABLE, joinSql);
        baseDTO.getParams().put(DATA_SCOPE, joinCondition);
    }

    /**
     * 设置
     *
     * @param baseDTO
     * @param dataScope
     * @param tableName
     * @param alias
     */
    private void setDataScopeIsNotJoin(BaseDTO baseDTO, Map<String, List<SysUserDataScope>> dataScope, String tableName, String alias, Map<String, Object> annotationData) {
        userDataScopeUtil.handlerUserDataScope(dataScope, tableName);
        boolean openFullTextIndex = MapUtils.getBoolean(annotationData, AnnotationUtil.FULL_TEXT_INDEX);
        String dataScopeSql = SqlHandler.getWhereSql(dataScope, alias, true, openFullTextIndex);
        baseDTO.getParams().put(DATA_SCOPE, dataScopeSql);
    }

    /**
     * 存在返回true 不存在返回false
     *
     * @param params
     * @return
     */
    private boolean validParamType(Class[] params) {
        if (null == params || params.length <= 0) {
            return Boolean.FALSE;
        }
        for (Class cl : params) {
            return isSubclass(cl, BaseDTO.class);
        }
        return Boolean.FALSE;
    }


    public static boolean isSubclass(Class<?> clazz, Class<?> superClass) {
        if (clazz == null || superClass == null) {
            return false;
        }

        if (superClass.isAssignableFrom(clazz)) {
            return true;
        }

        Class<?> parentClass = clazz.getSuperclass();
        return isSubclass(parentClass, superClass);
    }

    /**
     * 获取BaseDTO
     *
     * @param point
     * @return
     */
    private BaseDTO getBaseDTO(JoinPoint point) {
        Object[] args = point.getArgs();
        BaseDTO baseDTO = null;
        for (Object obj : args) {
            if (obj instanceof BaseDTO) {
                baseDTO = (BaseDTO) obj;
            }
        }
        return baseDTO;
    }

}

