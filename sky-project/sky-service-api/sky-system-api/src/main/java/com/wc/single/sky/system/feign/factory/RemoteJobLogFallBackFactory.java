package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.dto.RemoteSdmsJobLogDTO;
import com.wc.single.sky.system.feign.RemoteJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteJobLogFallBackFactory implements RemoteJobLogService {
    @Override
    public ResultDTO add(RemoteSdmsJobLogDTO bizSdmsJobLog) {
        return null;
    }

    @Override
    public ResultDTO edit(RemoteSdmsJobLogDTO bizSdmsJobLog) {
        return null;
    }

    @Override
    public ResultDTO<RemoteSdmsJobLogDTO> getSdmsJobLogDTO(RemoteSdmsJobLogDTO bizSdmsJobLog) {
        return null;
    }
}
