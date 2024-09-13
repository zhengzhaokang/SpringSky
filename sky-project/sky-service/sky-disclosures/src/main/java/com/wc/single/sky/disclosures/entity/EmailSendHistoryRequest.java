package com.wc.single.sky.disclosures.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
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
public class EmailSendHistoryRequest {
    private List<String> uniqueIds;
    private String uniqueId;
    @NotNull
    private String emailType;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTimeBegin;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTimeEnd;
    private String normalParams;
    private String remark;
    private String geoCode;
    private Boolean deleteFlag;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String market;
    private String businessGroup;
    private String mailTitle;
    private String mailBody;
    private String attachment;
    private String mailTo;
    private List<String> sendTo;
    private List<String> sendCc;
    private String mailFrom;
    private String mailCc;
    private String failReason;
    private Integer success;
    private String tableParams;
    private String titleParams;
    private String traceId;

    private String businessType;
    private String module;
    private String emailDimension;

    private String bean;
    private Map<String,String> tableParamsForBean;
    private String mailTitleBean;

}
