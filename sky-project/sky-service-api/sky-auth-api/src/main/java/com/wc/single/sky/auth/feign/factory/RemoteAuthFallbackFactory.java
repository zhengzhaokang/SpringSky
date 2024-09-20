package com.wc.single.sky.auth.feign.factory;

import com.wc.single.sky.auth.feign.RemoteAuthService;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RemoteAuthFallbackFactory implements RemoteAuthService {
    @Override
    public ResultDTO<Map<String, Object>> createToken(SysUser sysUser) {
        return null;
    }
}
