package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.system.feign.RemoteMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class RemoteMenuFallbackFactory implements RemoteMenuService
{
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        return null;
    }
}
