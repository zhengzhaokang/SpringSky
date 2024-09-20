package com.wc.single.sky.common.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class KafkaThreadPoolConfig {

    private int cpuNum = Runtime.getRuntime().availableProcessors();

    @Bean("kafkaLogExecutor")
    public Executor kafkaLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum + 1);
        executor.setMaxPoolSize(cpuNum * 2);
        executor.setQueueCapacity(20000);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("kafkaLogExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
