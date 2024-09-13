package com.wc.single.sky.common.handler.scope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.domain.SysUserDataScope;
import org.springframework.beans.factory.BeanNameAware;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 支持自定义对数据权限做特殊处理
 * 这样做的目的是为了方便拓展 在运行阶段对数据权限做特殊处理
 * @author  daihuaicai
 */
public interface DataScopeHandler extends BeanNameAware {

    /**
     * 实现此方法
     * @param loginUser
     * @return
     */
    Map<String, List<SysUserDataScope>> getLoginUserDataScope(SysUser loginUser);

    /**
     * 设置可以为空的
     * @param scopes
     * @return
     */
    default List<SysUserDataScope> setCanBeNullTrue(List<SysUserDataScope> scopes) {
        if (CollUtil.isEmpty(scopes)) {
            return scopes;
        }
        for (SysUserDataScope scope : scopes) {
            scope.setCanBeNull(Boolean.TRUE);
        }
        return scopes;
    }

    default void removeNullValue(List<SysUserDataScope> scopes) {
        if (CollUtil.isEmpty(scopes)) {
            return;
        }
        Iterator<SysUserDataScope> iterator = scopes.iterator();
        while (iterator.hasNext()) {
            SysUserDataScope scope = iterator.next();
            //select All的场景
            if ("0".equals(scope.getSelectAll())) {
                scope.setDataScopeValue("all");
            }
            if (null == scope || StrUtil.isBlank(scope.getDataScopeKey()) || StrUtil.isBlank(scope.getDataScopeValue())) {
                iterator.remove();
            }
        }
    }

}
