package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.wc.single.sky.system.feign.RemoteBusinessMassUploadLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteSysBusinessMassUploadLogFallbackFactory implements RemoteBusinessMassUploadLogService
{

    @Override
    public ResultDTO add(SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO) {
        return null;
    }

    @Override
    public ResultDTO edit(SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO) {
        return null;
    }
}
