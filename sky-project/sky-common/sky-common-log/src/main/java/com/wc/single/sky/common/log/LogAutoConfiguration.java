package com.wc.single.sky.common.log;

import com.wc.single.sky.common.log.aspect.OperLogAspect;
import com.wc.single.sky.common.log.listen.LogListener;
import com.wc.single.sky.system.feign.RemoteLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.AllArgsConstructor;

@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration
{
    private final RemoteLogService logService;

    @Bean
    public LogListener sysOperLogListener()
    {
        return new LogListener(logService);
    }

    @Bean
    public OperLogAspect operLogAspect()
    {
        return new OperLogAspect();
    }
}