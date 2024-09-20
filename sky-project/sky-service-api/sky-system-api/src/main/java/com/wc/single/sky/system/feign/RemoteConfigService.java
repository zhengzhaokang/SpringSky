package com.wc.single.sky.system.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户 Feign服务层
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
//@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteConfigFallbackFactory.class)
public interface RemoteConfigService
{
    @GetMapping("/config/getConfigKey")
    public String getConfigKey(@RequestParam("configKey") String configKey);

}
