package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.system.domain.SysRole;
import com.wc.single.sky.system.feign.RemoteRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteRoleFallbackFactory implements RemoteRoleService
{
    @Override
    public SysRole selectSysRoleByRoleId(long roleId) {
        return null;
    }

    @Override
    public List<SysRole> selectRoleList(SysRole sysRole) {
        return null;
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return null;
    }
}
