package com.wc.single.sky.common.kafka.producer;

import lombok.Data;

@Data
public class KafkaProducer {
    //todo @shirui3每个参数含义  之后补充
    //通用部分 yml一般不用重新配置
    private String acks;
    private Integer retries;
    private Integer batchSize;
    private Integer maxRequestSize;
    private Long lingerMs;
    private Long keyDeserializer;
    private Long bufferMemory;
    private String keySerializer;
    private String valueSerializer;
    private String securityProtocol;
    private String sslTruststorePassword;
    private String saslMechanism;

    //需要配置
    private String saslJaasConfig;
    private String server;
    private String kafkaFactory;

    //没配置或者 配置为true 则需要记录日志
    private Boolean enableLog;

}