package com.wc.single.sky.common.kafka.producer;

import com.wc.single.sky.common.kafka.common.ConstantKafka;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProducerYmlConfig {
    private KafkaProducer kafkaProducer;
    private List<KafkaProducer> server;

    public Map<String, Object> producerConfigs(KafkaProducerYmlConfig kafkaYmlConfig, String kafkaFactory) {
        Map<String, Object> props = new HashMap<>();
        //用common的设置一遍
        KafkaProducer kafkaConfig = kafkaYmlConfig.getKafkaProducer();

        props.put(ConstantKafka.PRODUCE_ENABLE_LOG, kafkaConfig.getEnableLog());
        props.put("acks", kafkaConfig.getAcks());
        props.put("retries", kafkaConfig.getRetries());
        props.put("batch.size", kafkaConfig.getBatchSize());
//        props.put("max.request.size", kafkaConfig.getMaxRequestSize());
        props.put("linger.ms", kafkaConfig.getLingerMs());
        props.put("buffer.memory", kafkaConfig.getBufferMemory());
        props.put("key.serializer", kafkaConfig.getKeySerializer());
        props.put("value.serializer", kafkaConfig.getValueSerializer());
        props.put("security.protocol", kafkaConfig.getSecurityProtocol());
        props.put("ssl.truststore.password", kafkaConfig.getSslTruststorePassword());
        props.put("sasl.mechanism", kafkaConfig.getSaslMechanism());
        props.put("sasl.jaas.config", kafkaConfig.getSaslJaasConfig());
        props.put("ssl.truststore.location", getConfigFilePath());

        //再用个性化的的设置一遍  个性化的配置必须是唯一的
        if(null== kafkaYmlConfig.getServer()){
            throw new RuntimeException("配置有误,未配置kafka自定义配置项");
        }
        int configCount = 0;
        for (KafkaProducer pojo: kafkaYmlConfig.getServer()) {
            if(pojo.getKafkaFactory().equals(kafkaFactory)){
                configCount++;
                if(null!=pojo.getEnableLog()){ props.put(ConstantKafka.PRODUCE_ENABLE_LOG, pojo.getEnableLog());}
                if(null!=pojo.getAcks()){ props.put("acks", pojo.getAcks());}
                if(null!=pojo.getRetries()){ props.put("retries", pojo.getRetries());}
                if(null!=pojo.getBatchSize()){ props.put("batch.size", pojo.getBatchSize());}
//                if(null!=pojo.getMaxRequestSize()){ props.put("max.request.size", pojo.getMaxRequestSize());}
                if(null!=pojo.getLingerMs()){ props.put("linger.ms", pojo.getLingerMs());}
                if(null!=pojo.getBufferMemory()){ props.put("buffer.memory", pojo.getBufferMemory());}
                if(null!=pojo.getKeySerializer()){ props.put("key.serializer", pojo.getKeySerializer());}
                if(null!=pojo.getValueSerializer()){ props.put("value.serializer", pojo.getValueSerializer());}
                if(null!=pojo.getSecurityProtocol()){ props.put("security.protocol", pojo.getSecurityProtocol());}
                if(null!=pojo.getSslTruststorePassword()){ props.put("ssl.truststore.password", pojo.getSslTruststorePassword());}
                if(null!=pojo.getSaslMechanism()){ props.put("sasl.mechanism", pojo.getSaslMechanism());}
                if(null!=pojo.getSslTruststorePassword()){ props.put("ssl.truststore.password", pojo.getSslTruststorePassword());}
                if(null!=pojo.getSaslJaasConfig()){ props.put("sasl.jaas.config", pojo.getSaslJaasConfig());}
                if(null!=pojo.getServer()){ props.put("bootstrap.servers", pojo.getServer());}
            }
        }
        if(configCount!=1){
            throw new RuntimeException("配置有误,存在多个同名自定义配置项");
        }
        return props;
    }

    private String getConfigFilePath() {

        //打包成jar后,无法找到文件的解决方法(复制jar包里的文件,到文件目录里)
        ApplicationHome applicationHome = new ApplicationHome(KafkaProducerYmlConfig.class);
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


    public ProducerFactory<String, String> producerFactory(KafkaProducerYmlConfig kafkaYmlConfig, String kafkaFactory) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(kafkaYmlConfig,kafkaFactory));
    }

}


