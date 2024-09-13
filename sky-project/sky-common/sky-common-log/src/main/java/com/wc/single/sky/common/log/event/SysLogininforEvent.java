package com.wc.single.sky.common.log.event;

import com.wc.single.sky.system.domain.SysLogininfor;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 */
public class SysLogininforEvent extends ApplicationEvent
{
    //
    private static final long serialVersionUID = -9084676463718966036L;

    public SysLogininforEvent(SysLogininfor source)
    {
        super(source);
    }
}