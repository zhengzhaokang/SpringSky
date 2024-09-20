package com.wc.single.sky.common.kafka.consumer;

import com.wc.single.sky.common.kafka.common.ConstantKafka;
import com.wc.single.sky.common.kafka.util.HeaderInterceptor;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerYmlConfig {
    private KafkaConsumer kafkaConsumer;
    private List<KafkaConsumer> server;

    private KafkaTemplate template;


    public ConcurrentKafkaListenerContainerFactory<String, String> getConsumerFactory(KafkaConsumerYmlConfig kafkaConsumerYmlConfig, String kafkaFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerYmlConfig.consumerFactory(kafkaConsumerYmlConfig, kafkaFactory));
        setFactoryConcurrency(factory, kafkaConsumerYmlConfig, kafkaFactory);
        //根据是否手动提交设置此参数
        setAckMode(factory, kafkaConsumerYmlConfig, kafkaFactory);
        factory.getContainerProperties().setPollTimeout(1500);
        //将监听的消息记录 根据配置确定是否记录log
        setMessageLogListener(factory);
        return factory;
    }

    private void setMessageLogListener(ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        //设置为false则不记录
        if (null != factory.getConsumerFactory().getConfigurationProperties().get(ConstantKafka.CONSUMER_ENABLE_LOG) && !(Boolean) factory.getConsumerFactory().getConfigurationProperties().get(ConstantKafka.CONSUMER_ENABLE_LOG)) {
            return;
        }
    }

    private void setAckMode(ConcurrentKafkaListenerContainerFactory<String, String> factory, KafkaConsumerYmlConfig kafkaConsumerYmlConfig, String kafkaFactory) {
        //配置并发数 (和Partition一致)
        //能到这步 说明配置没问题有值且唯一 这里只做concurrency校验
        if (null == kafkaConsumerYmlConfig.getKafkaConsumer().getEnableAutoCommit()) {
            throw new RuntimeException("配置有误,未配置EnableAutoCommit");
        }
        boolean autoCommit = kafkaConsumerYmlConfig.getKafkaConsumer().getEnableAutoCommit();
        for (KafkaConsumer pojo : kafkaConsumerYmlConfig.getServer()) {
            if (pojo.getKafkaFactory().equals(kafkaFactory)) {
                if (null != pojo.getEnableAutoCommit()) {
                    autoCommit = pojo.getEnableAutoCommit();
                }
            }
        }
        //设置手动提交ackMode; 如需手动提交 则在配置里enableAutoCommit: false
        if (!autoCommit) {
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        }
    }

    private void setFactoryConcurrency(ConcurrentKafkaListenerContainerFactory<String, String> factory, KafkaConsumerYmlConfig kafkaConsumerYmlConfig, String kafkaFactory) {
        //配置并发数 (和Partition一致)
        //能到这步 说明配置没问题有值且唯一 这里只做concurrency校验
        if (null == kafkaConsumerYmlConfig.getKafkaConsumer().getConcurrency()) {
            throw new RuntimeException("配置有误,未配置concurrency");
        }
        Integer concurrency = kafkaConsumerYmlConfig.getKafkaConsumer().getConcurrency();
        for (KafkaConsumer pojo : kafkaConsumerYmlConfig.getServer()) {
            if (pojo.getKafkaFactory().equals(kafkaFactory)) {
                if (null != pojo.getConcurrency()) {
                    concurrency = pojo.getConcurrency();
                }
            }
        }
        factory.setConcurrency(concurrency);
    }

    public ConsumerFactory<String, String> consumerFactory(KafkaConsumerYmlConfig kafkaConsumerYmlConfig, String kafkaFactory) {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(kafkaConsumerYmlConfig, kafkaFactory));
    }

    public Map<String, Object> consumerConfigs(KafkaConsumerYmlConfig kafkaConsumerYmlConfig, String kafkaFactory) {
        Map<String, Object> props = new HashMap<>();

        //将自定义的拦截器放入
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, HeaderInterceptor.class.getName());

        //用common的设置一遍
        KafkaConsumer kafkaConsumer = kafkaConsumerYmlConfig.getKafkaConsumer();
        props.put(ConstantKafka.CONSUMER_ENABLE_LOG, kafkaConsumer.getEnableLog());
        props.put("key.deserializer", kafkaConsumer.getKeyDeserializer());
        props.put("value.deserializer", kafkaConsumer.getValueDeserializer());
        props.put("security.protocol", kafkaConsumer.getSecurityProtocol());
        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaConsumer.getSslTruststorePassword());
        props.put("enable.auto.commit", kafkaConsumer.getEnableAutoCommit());
        props.put("auto.offset.reset", kafkaConsumer.getAutoOffsetReset());
        props.put("auto.commit.interval.ms", kafkaConsumer.getAutoCommitIntervalMs());
        props.put("session.timeout.ms", kafkaConsumer.getSessionTimeoutMs());
        props.put("max.poll.interval.ms", kafkaConsumer.getMaxPollIntervalMs());
//        props.put("heartbeat.interval.ms", kafkaConsumer.getHeartbeatIntervalMs());
        props.put("sasl.mechanism", kafkaConsumer.getSaslMechanism());
        props.put("sasl.jaas.config", kafkaConsumer.getSaslJaasConfig());
        props.put("bootstrap.servers", kafkaConsumer.getServer());
        props.put("group.id", kafkaConsumer.getGroupId());
        props.put("ssl.truststore.location", getConfigFilePath());

        //再用个性化的的设置一遍  个性化的配置必须是唯一的
        if (null == kafkaConsumerYmlConfig.getServer()) {
            throw new RuntimeException("配置有误,未配置kafka自定义配置项");
        }
        int configCount = 0;
        for (KafkaConsumer pojo : kafkaConsumerYmlConfig.getServer()) {
            if (pojo.getKafkaFactory().equals(kafkaFactory)) {
                configCount++;
                if (null != pojo.getEnableLog()) {
                    props.put(ConstantKafka.CONSUMER_ENABLE_LOG, pojo.getEnableLog());
                }
                if (null != pojo.getKeyDeserializer()) {
                    props.put("key.deserializer", pojo.getKeyDeserializer());
                }
                if (null != pojo.getValueDeserializer()) {
                    props.put("value.deserializer", pojo.getValueDeserializer());
                }
                if (null != pojo.getSecurityProtocol()) {
                    props.put("security.protocol", pojo.getSecurityProtocol());
                }
                if (null != pojo.getSslTruststorePassword()) {
                    props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, pojo.getSslTruststorePassword());
                }
                if (null != pojo.getEnableAutoCommit()) {
                    props.put("enable.auto.commit", pojo.getEnableAutoCommit());
                }
                if (null != pojo.getAutoOffsetReset()) {
                    props.put("auto.offset.reset", pojo.getAutoOffsetReset());
                }
                if (null != pojo.getAutoCommitIntervalMs()) {
                    props.put("auto.commit.interval.ms", pojo.getAutoCommitIntervalMs());
                }
//                if (null != pojo.getHeartbeatIntervalMs()) {
//                    props.put("heartbeat.interval.ms", pojo.getHeartbeatIntervalMs());
//                }
                if (null != pojo.getSessionTimeoutMs()) {
                    props.put("session.timeout.ms", pojo.getSessionTimeoutMs());
                }
                if (null != pojo.getMaxPollIntervalMs()) {
                    props.put("max.poll.interval.ms", pojo.getMaxPollIntervalMs());
                }
                if (null != pojo.getSaslMechanism()) {
                    props.put("sasl.mechanism", pojo.getSaslMechanism());
                }
                if (null != pojo.getSaslJaasConfig()) {
                    props.put("sasl.jaas.config", pojo.getSaslJaasConfig());
                }
                if (null != pojo.getServer()) {
                    props.put("bootstrap.servers", pojo.getServer());
                }
                if (null != pojo.getGroupId()) {
                    props.put("group.id", pojo.getGroupId());
                }
            }
        }
        if (configCount != 1) {
            throw new RuntimeException("配置有误,存在多个同名自定义配置项");
        }
        return props;
    }

    private String getConfigFilePath() {

        //打包成jar后,无法找到文件的解决方法(复制jar包里的文件,到文件目录里)
        ApplicationHome applicationHome = new ApplicationHome(KafkaConsumerYmlConfig.class);
        //项目打包成jar包所在的根路径
        String rootPath = applicationHome.getDir().getAbsolutePath();
        String configFilePath = rootPath + "\\client_truststore.jks";
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            try {
                //获取类路径下的指定文件流 (项目目录下的: /resource/libs/client_truststore.jks)
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("client_truststore.jks");
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(in, "config.xml文件找不到"), configFile);
            } catch (IOException e) {
                throw new IllegalArgumentException("保存文件证书失败->" + e.getMessage());
            }
        }
        return configFilePath;
    }

}


