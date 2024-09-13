package com.wc.single.sky.system.feign;

import com.wc.single.sky.system.domain.SysRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 角色 Feign服务层
 * 
 * @Author lovefamily
 * @date 2022年1月6日 17:45:44
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
//@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteRoleFallbackFactory.class)
public interface RemoteRoleService
{
    @GetMapping("role/get/{roleId}")
    public SysRole selectSysRoleByRoleId(@PathVariable("roleId") long roleId);

    /**
     * 查询所有角色
     *
     * @param sysRole
     * @return
     * @Author lovefamily
     */
    @GetMapping("role/rolelist")
    public List<SysRole> selectRoleList(@RequestBody SysRole sysRole);

    /**
     * 查询所有角色
     *
     * @return
     * @Author lovefamily
     */
    @GetMapping("role/allList")
    public List<SysRole> selectRoleAll();


}
