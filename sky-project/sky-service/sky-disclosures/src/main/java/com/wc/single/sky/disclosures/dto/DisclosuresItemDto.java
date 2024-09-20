package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "disclosures_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosuresItemDto {

    @Id
    private Long id;

    private String itemNumber;

    private String disclosuresId;

    private String keyInfo;

    private String region;

    private String description;

    private String rootCause;

    private String impact;

    private String inQtrBooks;

    private String itemClassification;

    private String action;

    private String issueStatus;

    private String owner;

    private Date targetDate;

    private String acctEntry;

    private String inPriorQuarter;

    private String status;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
