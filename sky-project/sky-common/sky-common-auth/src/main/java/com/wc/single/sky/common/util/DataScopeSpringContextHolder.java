package com.wc.single.sky.common.util;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.handler.scope.DataScopeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

@Component
public class DataScopeSpringContextHolder extends ApplicationObjectSupport {
    /**
     * 处理器名称
     */
    public static final String HANDLER_NAME = "handlerName";
    /**
     * 获取dataScope处理
     * @param beanName
     * @return
     */
    public DataScopeHandler getDataScopeHandler(String beanName) {
        DataScopeHandler dataScopeHandler = null;
        ApplicationContext context = super.getApplicationContext();
        if (null == context) {
            logger.error("数据权限获取不到上下文");
            return null;
        }
        if (StrUtil.isNotBlank(beanName)) {
            dataScopeHandler = context.getBean(beanName, DataScopeHandler.class);
        }
        if (null == dataScopeHandler) {
            return context.getBean("defaultDataScopeHandler--", DataScopeHandler.class);
        }
        return dataScopeHandler;
    }
}
