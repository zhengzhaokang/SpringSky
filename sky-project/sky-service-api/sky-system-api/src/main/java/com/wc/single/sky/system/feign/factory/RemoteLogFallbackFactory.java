package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.system.domain.SysKafkaLog;
import com.wc.single.sky.system.domain.SysLogininfor;
import com.wc.single.sky.system.domain.SysOperLog;
import com.wc.single.sky.system.feign.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLogFallbackFactory implements RemoteLogService
{
    @Override
    public void insertOperlog(SysOperLog operLog) {

    }

    @Override
    public void insertLoginlog(SysLogininfor logininfor) {

    }

    @Override
    public void insertKafkalog(SysKafkaLog operLog) {

    }
}
