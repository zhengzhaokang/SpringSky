package com.wc.single.sky.common.kafka.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Describe:
 * @Author: shirui3
 * @Date: 2020/3/16 14:49
 */
@Slf4j
public class KafkaCallback implements ListenableFutureCallback {
    private String kafkaTopic;
    @Autowired
    public KafkaCallback(String kafkaTopic){
        this.kafkaTopic=kafkaTopic;
    }

    @Override
    public void onFailure(Throwable throwable) {
        //如果Kafka返回一个错误，onCompletion方法抛出一个non null异常。
        throwable.printStackTrace();//对异常进行一些处理，这里只是简单打印出来
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        log.error("kafka-Producer推送失败ClaimData:{}",JSON.toJSONString("temp"));
    }

    @Override
    public void onSuccess(Object o) {
        //log.info("kafka-Produce推送成功 offset值:{},时间标识:{}, 数据: {}",recordMetadata.offset(), dateFlag, JSON.toJSONString(claimResponse, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue));

    }
}
