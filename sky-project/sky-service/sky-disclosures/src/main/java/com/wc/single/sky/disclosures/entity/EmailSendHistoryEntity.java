package com.wc.single.sky.disclosures.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @Description
  * @author shirui3
  * @date 2022/11/9 11:36
  */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendHistoryEntity {
    private String uniqueId;
    private String traceId;
    private String emailType;
    private Date sendTime;
    private String normalParams;
    private String tableParams;
    private String remark;
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
    private String mailFrom;
    private String mailCc;

    //未发送为0,成功: 1,失败:2
    private Integer success;
    private String failReason;

    private String businessType;
    private String module;
    private String emailDimension;
    private String geoCode;



}
