package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.system.domain.SysDept;
import com.wc.single.sky.system.feign.RemoteDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteDeptFallbackFactory implements RemoteDeptService
{
    @Override
    public SysDept selectSysDeptByDeptId(long deptId) {
        return null;
    }
}
