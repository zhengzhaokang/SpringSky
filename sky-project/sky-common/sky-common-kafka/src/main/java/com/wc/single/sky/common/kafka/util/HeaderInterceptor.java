package com.wc.single.sky.common.kafka.util;

import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.utils.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.slf4j.MDC;

import java.util.Map;

public class HeaderInterceptor implements ConsumerInterceptor<String, String> {
    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        // 在消息被消费之前进行处理
        for (ConsumerRecord<String, String> consumerRecord : records) {
            processHeaders(consumerRecord);
        }
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        //不需要实现
    }

    @Override
    public void close() {
        //不需要实现
    }

    @Override
    public void configure(Map<String, ?> configs) {
        //不需要实现
    }

    private void processHeaders(ConsumerRecord<String, String> consumerRecord) {
        boolean hasTraceId = false;
        for (Header header : consumerRecord.headers()) {
            if (header.key().equals(Constants.TRACE_ID)) {
                String traceId = new String(header.value());
                // 使用 traceId 进行后续操作
                MDC.put(Constants.TRACE_ID, traceId);
                hasTraceId = true;
                break;
            }
        }
        //遍历header没有traceid 则表示发送端是 外部系统 没有设置trace id ,所以这里需要初始化一个
        if (Boolean.FALSE.equals(hasTraceId)) {
            MDC.put(Constants.TRACE_ID, StringUtils.getNextRandomId());
        }
    }
}