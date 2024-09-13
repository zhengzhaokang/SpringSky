package com.wc.single.sky.system.feign;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.dto.SysBusinessMassUploadLogDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志Feign服务层
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
//@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteBusinessOperatorLogFallbackFactory.class)
public interface RemoteBusinessMassUploadLogService
{
    @PostMapping("/sysBusinessMassUploadLog/save")
    public ResultDTO add(@RequestBody SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO);

    @PutMapping("/sysBusinessMassUploadLog")
    public ResultDTO edit(@RequestBody SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO);

}
