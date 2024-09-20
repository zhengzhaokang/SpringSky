package com.wc.single.sky.system.feign;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.dto.RemoteSdmsJobLogDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteJobLogFallBackFactory.class)
public interface RemoteJobLogService {

    @PostMapping("/jobMonitor/add")
    public ResultDTO add(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog);

    @PutMapping("/jobMonitor/edit")
    public ResultDTO edit(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog);

    @PostMapping("/jobMonitor/getJobLogDTO")
    public ResultDTO<RemoteSdmsJobLogDTO> getSdmsJobLogDTO(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog);

}
