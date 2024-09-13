package com.wc.single.sky.disclosures.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureApproveLogVO {

    private Long id;

    private String approveUuid;

    private String disclosuresId;

    private String disclosureNumber;

    private String approver;

    private Date approveTime;

    private String assignee;

    private String completed;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
