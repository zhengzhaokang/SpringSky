package com.wc.single.sky.common.kafka.util;

import com.alibaba.fastjson.JSON;
import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.exception.QdpException;
import lombok.Data;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Describe:
 */
@Component("kafka")
@Data
public class KafkaSend {

    private static final Logger log = LoggerFactory.getLogger(KafkaSend.class);

    private ConcurrentMap<String,KafkaTemplate<String, String>> kafkaTemplateListMap = new ConcurrentHashMap<>();


    /**
     * @param topic
     * @param key
     * @param value
     * @return
     * @Description kafka 推送数据 异步方式;
     * @author shirui3
     * @date 2023/7/24 19:33
     */
    public ListenableFuture<SendResult<String, String>> send(String topic, String key, String value,String factory,Boolean enableLog) {
        ListenableFuture<SendResult<String, String>> future = null;
        try {
            ProducerRecord<String, String> producerRecord = new ProducerRecord(topic, key, value);
            setTraceIdInHeader(producerRecord);
            future = kafkaTemplateListMap.get(factory).send(producerRecord);
            //enableLog为true 才打印日志 为null 或者false 不打印日志

        } catch (KafkaException  e) {
            log.error("推送kafka消息异常，topic：{}，key：{}，value：{}，异常信息：{}", topic, key, value, e);
        }

        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplateListMap.get(factory);
        if (kafkaTemplate == null) {
            throw new QdpException("推送kafka消息，获取kafkaTemplate为空");
        }
        return future;
    }

    private static void setTraceIdInHeader(ProducerRecord<String, String> producerRecord) {
        Headers headers = producerRecord.headers();
        if (StringUtils.isEmpty(MDC.get(Constants.TRACE_ID))) {
            MDC.put(Constants.TRACE_ID, com.wc.single.sky.common.utils.StringUtils.getNextRandomId());
        }
        headers.add(Constants.TRACE_ID, MDC.get(Constants.TRACE_ID).getBytes());
        log.info("kafka send traceId :{}", MDC.get(Constants.TRACE_ID));
    }


    /**
     * @param topic
     * @param key
     * @param value
     * @param callback
     * @return
     * @Description kafka 推送数据 异步方式;
     * @author shirui3
     * @date 2023/7/24 19:33
     */
    public void send(String topic, String key, String value, String factory, ListenableFutureCallback<SendResult<String, String>> callback,Boolean enableLog) {
        log.info("send Start.");
        ListenableFuture<SendResult<String, String>> future = this.send(topic, key, value, factory,enableLog);
        future.addCallback(callback);
        log.info("send success.");
    }

    /**
     * 同步推送kafka消息
     * @param topic
     * @param key
     * @param value
     * @param factory
     */
    public void syncSend(String topic, String key, String value, String factory,Boolean enableLog) {
        try {
            ListenableFuture<SendResult<String, String>> future = this.send(topic, key, value, factory,enableLog);
            future.get();
        } catch (Exception e) {
            log.error("同步推送kafka消息异常，topic：{}，key：{}，value：{}，异常信息：{}", topic, key, value, e);
            throw new QdpException("同步推送kafka消息异常，topic："+topic+"，key："+key+"，value："+value, e);
        }
        log.info("同步推送kafka消息成功，topic：{}，key：{}，value：{}", topic, key, value);
    }

    /**
     * 同步推送kafka消息
     * @param topic
     * @param key
     * @param value
     * @param factory
     * @param enableLog
     */
    public void syncSend(String topic, String key, Object value, String factory, Boolean enableLog) {
        this.syncSend(topic, key, JSON.toJSONString(value), factory, enableLog);
    }

    /**
     * 异步推送kafka消息
     * @param topic
     * @param key
     * @param value
     * @param factory
     * @param callback
     */
    public void asynSend(String topic, String key, String value, String factory, ListenableFutureCallback<SendResult<String, String>> callback,Boolean enableLog) {
        try {
            this.send(topic, key, value, factory, callback,enableLog);
        } catch (Exception e) {
            log.error("异步推送kafka消息异常，topic：{}，key：{}，value：{}，异常信息：{}", topic, key, value, e);
            throw new QdpException("异步推送kafka消息异常，topic："+topic+"，key："+key+"，value："+value, e);
        }
        log.info("异步推送kafka消息完成，topic：{}，key：{}，value：{}", topic, key, value);
    }

}
