package com.wc.single.sky.common.kafka.listener;

import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.constant.RedisConstants;
import com.wc.single.sky.common.redis.util.RedisUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Deprecated
@Component
public class ErrorListener {
    private static final Logger log= LoggerFactory.getLogger(ErrorListener.class);

    @Autowired
    RedisUtils redisUtils;
    /*
    * 记录到redis
    * kafka+topic+partition+offset  = 1
    * kafka:qdp-svc.update-pool.dev:0:385
    * */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return new ConsumerAwareListenerErrorHandler() {
            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException e, Consumer<?, ?> consumer) {

                Map<String,String> kafkaInfo= parseMessage(message);
                String topic = kafkaInfo.get("topic");
                String partition = kafkaInfo.get("partition");
                String offset = kafkaInfo.get("offset");
                String kafkaKey = kafkaInfo.get("kafkaKey");
                String value = kafkaInfo.get("value");

                log.error("consumerAwareErrorHandler receive : "+message.getPayload().toString() + "error info" + e.getMessage(), e);
                String redisKey = RedisConstants.generateRedisKey(RedisConstants.KAFKA_ERROR,
                        String.valueOf(Constants.SEPARATE_COLON),
                        topic,
                        kafkaKey,
                        partition,
                        offset
                );
                log.info("redisKey"+redisKey);
                //todo
  /*              System.out.println("消费失败消息:"+message.toString());
                //获取消息处理异常主题
                MessageHeaders headers = message.getHeaders();
                String topic=headers.get("kafka_receivedTopic")+TOPIC_DLT;
                */
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }
                int size = redisUtils.increment(redisKey, 1).intValue();
                if(size>3){
                    //重试4次 还报错记录到log表里
                    try {
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    consumer.commitSync();
                }else{
                    TopicPartition topicPartition = new TopicPartition(topic,Integer.parseInt(partition));
//                    consumer.seek(topicPartition,(Long.parseLong(offset)-1L));
                    consumer.seek(topicPartition,(Long.parseLong(offset)));
                    consumer.commitSync();
                }

                return message;
            }
        };
    }


    private Map<String,String> parseMessage(Message<?> message){
        Map<String,String> kafkaInfo= new HashMap<>();

        String info =message.getPayload().toString();
        String topic = info.split("topic = ")[1].split(", ")[0];info=info.split("topic = ")[1];
        String partition = info.split("partition = ")[1].split(", ")[0];info=info.split("partition = "+partition)[1];
        String offset = info.split("offset = ")[1].split(", ")[0];info=info.split("offset = ")[1];
        String kafkaKey = info.split("key = ")[1].split(", ")[0];info=info.split("key = ")[1];
        String value = info.split("value = ")[1].substring(0, info.split("value = ")[1].length() - 1);
        kafkaInfo.put("topic",topic);
        kafkaInfo.put("partition",partition);
        kafkaInfo.put("offset",offset);
        kafkaInfo.put("kafkaKey",kafkaKey);
        kafkaInfo.put("value",value);
        return kafkaInfo;
    }

    public static void main(String[] args) {

/*
                consumerAwareErrorHandler receive : ConsumerRecord(topic = qdp-svc.update-pool.dev,
                partition = 0, leaderEpoch = 91,
                offset = 387, CreateTime = 1672296548521, serialized key size = 9, serialized value size = 85,
                headers = RecordHeaders(headers = [], isReadOnly = false), key = 630020484,
                value = {"forecastChangeP":3.2,"forecastChangeU":40.7,"isDeltaValue":true,"poolId":630020484})
                */

        String info ="ConsumerRecord(topic = qdp-svc.update-pool.dev, partition = 0, leaderEpoch = 91, offset = 387, CreateTime = 1672296548521, serialized key size = 9, serialized value size = 85, headers = RecordHeaders(headers = [], isReadOnly = false), key = 630020484, value = {\"forecastChangeP\":3.2,\"forecastChangeU\":40.7,\"isDeltaValue\":true,\"poolId\":630020484})";
        String topic = info.split("topic = ")[1].split(", ")[0];info=info.split("topic = ")[1];
        String partition = info.split("partition = ")[1].split(", ")[0];info=info.split("partition = "+partition)[1];
        String offset = info.split("offset = ")[1].split(", ")[0];info=info.split("offset = ")[1];
        String key = info.split("key = ")[1].split(", ")[0];info=info.split("key = ")[1];
        String value = info.split("value = ")[1].substring(0, info.split("value = ")[1].length() - 1);
    }
}