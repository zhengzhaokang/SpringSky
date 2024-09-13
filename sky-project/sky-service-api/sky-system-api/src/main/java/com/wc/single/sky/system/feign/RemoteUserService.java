package com.wc.single.sky.system.feign;

import com.wc.single.sky.common.core.domain.R;
import com.wc.single.sky.system.domain.SysUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户 Feign服务层
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
//@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteUserFallbackFactory.class, configuration = APIQualifierInterceptor.class)
public interface RemoteUserService
{
    @GetMapping("user/getUserById/{userId}")
    public SysUser selectSysUserByUserId(@PathVariable("userId") long userId);

    @GetMapping("user/find/{username}")
    public SysUser selectSysUserByUsername(@PathVariable("username") String username);

    @PostMapping("user/update/login")
    public R updateUserLoginRecord(@RequestBody SysUser user);

    /**
     * 查询拥有当前角色的所有用户
     * @param roleIds
     * @return

     */
    @GetMapping("user/hasRoles")
    public Set<Long> selectUserIdsHasRoles(@RequestParam("roleIds") String roleIds);

    /**
     * 查询所有当前部门中的用户
     * 
     * @param deptIds
     * @return

     */
    @GetMapping("user/inDepts")
    public Set<Long> selectUserIdsInDepts(@RequestParam("deptIds") String deptIds);

    /**
     * 查询所有用户
     *
     * @param user
     * @return
     * @Author lovefamily
     */
    @GetMapping("user/userList")
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根据用户查询相同GEO的用户列表
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @PostMapping("user/geoUserList")
    public List<SysUser> selectUserListByCommonGeos(@RequestBody SysUser user, @RequestHeader("token") String token, @RequestHeader("current_id") String current_id);

}
