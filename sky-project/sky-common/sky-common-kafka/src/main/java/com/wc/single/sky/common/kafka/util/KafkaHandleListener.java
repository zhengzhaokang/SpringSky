package com.wc.single.sky.common.kafka.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

/**
 * kafka监听 * @author King-Mouse *
 */
@Component
public class KafkaHandleListener {


    @Autowired
    private KafkaListenerEndpointRegistry registry;


    public void start(String listenerId) {
        // 判断监听容器是否启动，未启动则将其启动
        if (!registry.getListenerContainer(listenerId).isRunning()) {
            registry.getListenerContainer(listenerId).start();
        }
        // 将其恢复
        registry.getListenerContainer(listenerId).resume();
    }

    public void stop(String listenerId) {
        // 暂停监听
        registry.getListenerContainer(listenerId).pause();
    }
}
