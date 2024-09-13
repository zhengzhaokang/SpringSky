package com.wc.single.sky.disclosures.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
  * @Description
  * @author shirui3
  * @date 2022/11/9 11:36
  */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendKafka {

    private Map<String, String> emailDimension;
    private Map<String, String> emailInfo;
    private EmailParamEntity emailParamEntity;
    private Map<String, List<String>> sendInfo;

    private JSONObject normalParams;
    private Map<String,String> tableParams;

    private String failReason;

}
