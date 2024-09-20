package com.wc.single.sky.disclosures.common;

/**
 * @author qiaodf2
 */
public class KafkaFactory {

    private KafkaFactory() {
          throw new IllegalStateException("Utility class");
      }

    /*************************************消费者*********************************************/

    //此处需要和yml配置 server下的list里的kafkafactory值一致
    public static final String SEND_EMAIL_KAFKA_FACTORY = "sendEmailKafkaFactory";

    public static final String SEND_CONSUMER_KAFKA_FACTORY = "consumerKafkaFactory";


    /*************************************生产工厂*********************************************/

    public static final String SEND_EMAIL_KAFKA_PRODUCER_FACTORY = "sendEmailKafkaProduceFactory";

    public static final String SEND_KAFKA_PRODUCER_FACTORY = "sendKafkaProduceFactory";

}
