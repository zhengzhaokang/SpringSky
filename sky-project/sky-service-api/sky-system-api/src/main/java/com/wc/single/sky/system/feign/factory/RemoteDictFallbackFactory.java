package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.system.domain.SysDictData;
import com.wc.single.sky.system.feign.RemoteDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteDictFallbackFactory implements RemoteDictService
{
    @Override
    public List<SysDictData> getDictSelect(String dictType) {
        return null;
    }
}
