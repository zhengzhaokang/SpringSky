package com.wc.single.sky.common.kafka.consumer;

import lombok.Data;

@Data
public class KafkaConsumer {

    //todo @shirui3每个参数含义  之后补充
    //通用部分 yml一般不用重新配置
    private String truststoreLocation;
    private String keyDeserializer;
    private String valueDeserializer;
    private String securityProtocol;
    private String sslTruststorePassword;

    private Boolean enableAutoCommit;
    private String autoOffsetReset;
    private Integer autoCommitIntervalMs;
    private Integer sessionTimeoutMs;
    private Integer maxPollIntervalMs;
    private Integer heartbeatIntervalMs;
    private String saslMechanism;
    private String saslTruststorePassword;

    private Integer concurrency;

    //需要配置
    private String saslJaasConfig;
    private String server;
    private String groupId;

    private String kafkaFactory;

    //没配置或者 配置为true 则需要记录日志
    private Boolean enableLog;

}