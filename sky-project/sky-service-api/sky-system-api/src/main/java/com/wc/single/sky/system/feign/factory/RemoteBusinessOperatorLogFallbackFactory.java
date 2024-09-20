package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.core.page.TableDataInfo;
import com.wc.single.sky.system.domain.dto.SysBusinessOperatorLogDTO;
import com.wc.single.sky.system.feign.RemoteBusinessOperatorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteBusinessOperatorLogFallbackFactory implements RemoteBusinessOperatorLogService
{
    @Override
    public ResultDTO add(SysBusinessOperatorLogDTO sysBusinessOperatorLogDTO) {
        return null;
    }

    @Override
    public TableDataInfo list(SysBusinessOperatorLogDTO sysBusinessOperatorLog) {
        return null;
    }
}
