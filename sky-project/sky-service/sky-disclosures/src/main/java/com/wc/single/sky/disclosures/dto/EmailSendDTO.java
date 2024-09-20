package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  * @Description
  * @author liuhuayong
  * @date 2020/1/2 11:36
  */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendDTO {
    private List<String> uniqueIds;
    private String uniqueId;
    @NotNull
    private String emailType;
    private String geoCode;
    private String createBy;
    private String market;
    private String businessGroup;
    private String attachment;
    private List<String> sendTo;
    private List<String> sendCc;
    private String mailFrom;
    private String traceId;
    private String businessType;
    private String module;
    private String bean;
    private Map<String,String> tableParamsForBean;
    private String mailTitleBean;

    private ArrayList<String> attachmentPath;

}
