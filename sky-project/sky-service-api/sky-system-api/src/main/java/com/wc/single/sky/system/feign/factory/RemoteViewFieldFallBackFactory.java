package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.page.RemoteResponse;
import com.wc.single.sky.system.feign.RemoteViewFieldService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoteViewFieldFallBackFactory implements RemoteViewFieldService {
    @Override
    public Object getViewFieldByBgAndGeo(String geo, String businessGroup, String pageKey, String viewType) {
        return null;
    }

    @Override
    public RemoteResponse pageKeyList(Map<String, Object> param) {
        return null;
    }
}
