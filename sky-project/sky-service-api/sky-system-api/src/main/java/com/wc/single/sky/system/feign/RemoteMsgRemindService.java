package com.wc.single.sky.system.feign;

import com.wc.single.sky.common.core.page.TableDataInfo;
import com.wc.single.sky.system.domain.MsgRemindDTO;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Msg Feign服务层
 *
 * @Author lovefamily
 * @date 2022-08-10
 */
//@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteOssFallbackFactory.class)
public interface RemoteMsgRemindService {
    @GetMapping("/msgRemind/list")
    public TableDataInfo list(MsgRemindDTO msgRemindDTO);

}
