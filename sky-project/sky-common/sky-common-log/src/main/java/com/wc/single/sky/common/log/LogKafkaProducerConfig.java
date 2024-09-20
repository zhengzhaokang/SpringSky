package com.wc.single.sky.common.log;


import com.wc.single.sky.common.kafka.factory.DefaultKafkaFactory;
import com.wc.single.sky.common.kafka.producer.KafkaProducerYmlConfig;
import com.wc.single.sky.common.kafka.util.KafkaSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnProperty(name = "enableSystemLog", havingValue = "true", matchIfMissing = true)
public class LogKafkaProducerConfig {

    @Autowired
    private KafkaSend kafkaSend;

    @Autowired
    private KafkaProducerYmlConfig kafkaProducerYmlConfig;

    /*
     * 新建生产消息工厂, 加addFactory即可
     * */
    @PostConstruct
    public void setKafkaTemplate() {
        addFactory(DefaultKafkaFactory.SYS_LOG_KAFKA_FACTORY);
    }

    private void addFactory(String producerFactory) {
        kafkaSend.getKafkaTemplateListMap().put(producerFactory,
                new KafkaTemplate<>(kafkaProducerYmlConfig.producerFactory(kafkaProducerYmlConfig, producerFactory)));
    }
}
