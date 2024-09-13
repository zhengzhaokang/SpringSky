package com.wc.single.sky.common.util;

import com.wc.single.sky.common.handler.sql.BaseSqlHandler;
import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.domain.SysUserDataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BuildAccrualSqlUtil extends BaseSqlHandler {

    @Autowired
    UserDataScopeUtil userDataScopeUtil;

    public String getAccrualDataScopeSql() {
        StringBuilder sql = new StringBuilder();
        SysUser sysUser = userDataScopeUtil.getLoginUser();
        if (null == sysUser || sysUser.isAdmin()) {
            return "";
        }
        Map<String, List<SysUserDataScope>> dataScopeMap = userDataScopeUtil.getUserDataScope(sysUser,"");
        int index = 0;
        for (Map.Entry<String, List<SysUserDataScope>> entry : dataScopeMap.entrySet()) {
            String key = entry.getKey();
            List<SysUserDataScope> value = entry.getValue();
            if ("business_group".equals(key) || key.contains("geo") || "gtn_type_code".equals(key) || "segment_code".equals(key) || isSelectAll(value)) {
                continue;
            }
            if (index > 0) {
                sql.append(" and ");
            }
            String tempValue= value.stream().map(tempDataScope -> tempDataScope.getDataScopeValue().trim()).collect(Collectors.joining(","));
            String[] split = tempValue.split(",");
            sql.append("\"").append(key).append("\" in ").append("(");
            for (int i = 0; i < split.length; i++) {
                if (i == split.length - 1) {
                    sql.append("''").append(split[i]).append("''");
                } else {
                    sql.append("''").append(split[i]).append("'',");
                }
            }
            sql.append(")");
            index++;
        }
        return sql.toString();
    }

}
