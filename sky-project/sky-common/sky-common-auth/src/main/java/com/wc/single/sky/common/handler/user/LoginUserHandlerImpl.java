package com.wc.single.sky.common.handler.user;

import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.system.feign.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * * 主要给异步任务用 获取当前登录用户
 * * 用完一定要记得清除 不然会内存泄漏
 * *
 */
@Component
public class LoginUserHandlerImpl implements LoginUserHandler {

    private static ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    Logger logger = LoggerFactory.getLogger(LoginUserHandlerImpl.class);


    RemoteUserService remoteUserService;

    @Autowired
    @Lazy
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }


    @Override
    public SysUser getLoginUser() {
        return threadLocal.get();
    }

    @Override
    public void setLoginUser(SysUser sysUser) {

        threadLocal.set(sysUser);
    }


    @Override
    public void clear() {
        threadLocal.remove();
    }


}
