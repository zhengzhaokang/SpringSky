package com.wc.single.sky.common.handler.user;

import com.wc.single.sky.system.domain.SysUser;

/**
 * * @Author: daihc1
 */
public interface LoginUserHandler {

    SysUser getLoginUser();

    /**
     * * 主要给异步任务用 获取当前登录用户
     *
     * @param user *
     */
    void setLoginUser(SysUser user);

    void clear();


}
