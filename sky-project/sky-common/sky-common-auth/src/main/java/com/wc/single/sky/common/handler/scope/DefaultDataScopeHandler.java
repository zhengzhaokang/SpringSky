package com.wc.single.sky.common.handler.scope;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.handler.sql.BaseSqlHandler;
import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.domain.SysUserDataScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 默认的数据权限处理器
 * @author daihuaicai
 */
@Component(value = "defaultDataScopeHandler--")
@Slf4j
public class DefaultDataScopeHandler implements DataScopeHandler {

    /**
     * 默认的数据权限处理器
     * @param sysUser
     * @return
     */
    @Override
    public Map<String, List<SysUserDataScope>> getLoginUserDataScope(SysUser sysUser) {
        if (null == sysUser || sysUser.isAdmin() || CollectionUtils.isEmpty(sysUser.getSysUserDataScopeList())) {
            return new HashMap<>();
        }
        List<SysUserDataScope> userDataScopes = sysUser.getSysUserDataScopeList();
        Map<String, List<SysUserDataScope>> sysUserDataScopeGroupMap = userDataScopes.stream().collect(Collectors.groupingBy(SysUserDataScope::getDataScopeKey));
        for (Map.Entry<String,List<SysUserDataScope>> entry : sysUserDataScopeGroupMap.entrySet()) {
            if (StrUtil.isBlank(entry.getKey())) {
                continue;
            }
            if (BaseSqlHandler.getCanBeNullSet().contains(entry.getKey())) {
                setCanBeNullTrue(entry.getValue());
            }
            //删除空值
            removeNullValue(entry.getValue());
        }
        return sysUserDataScopeGroupMap;
    }


    @Override
    public void setBeanName(String name) {
        log.info("beanName is {}",name);
    }
}
