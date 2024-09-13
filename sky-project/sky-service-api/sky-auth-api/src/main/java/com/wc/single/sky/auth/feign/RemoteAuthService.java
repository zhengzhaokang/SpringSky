package com.wc.single.sky.auth.feign;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.SysUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

//@FeignClient(name = "${otfp.service.auth.uri}", fallbackFactory = RemoteAuthFallbackFactory.class)
public interface RemoteAuthService {

    @PostMapping("create/token")
    public ResultDTO<Map<String, Object>> createToken(@RequestBody SysUser sysUser);
}
