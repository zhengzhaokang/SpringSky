package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.page.TableDataInfo;
import com.wc.single.sky.system.domain.MsgRemindDTO;
import com.wc.single.sky.system.feign.RemoteMsgRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteMsgRemindFallbackFactory implements RemoteMsgRemindService{

    @Override
    public TableDataInfo list(MsgRemindDTO msgRemindDTO) {
        return null;
    }
}
