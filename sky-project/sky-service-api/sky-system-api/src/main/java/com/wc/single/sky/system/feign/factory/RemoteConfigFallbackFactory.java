package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.system.feign.RemoteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteConfigFallbackFactory implements RemoteConfigService
{

    @Override
    public String getConfigKey(String configKey) {
        return null;
    }
}
