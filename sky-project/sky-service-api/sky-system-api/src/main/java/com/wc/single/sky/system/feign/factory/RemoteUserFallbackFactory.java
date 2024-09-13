package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.domain.R;
import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.feign.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class RemoteUserFallbackFactory implements RemoteUserService
{
    @Override
    public SysUser selectSysUserByUserId(long userId) {
        return null;
    }

    @Override
    public SysUser selectSysUserByUsername(String username) {
        return null;
    }

    @Override
    public R updateUserLoginRecord(SysUser user) {
        return null;
    }

    @Override
    public Set<Long> selectUserIdsHasRoles(String roleIds) {
        return null;
    }

    @Override
    public Set<Long> selectUserIdsInDepts(String deptIds) {
        return null;
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return null;
    }

    @Override
    public List<SysUser> selectUserListByCommonGeos(SysUser user, String token, String current_id) {
        return null;
    }
}
