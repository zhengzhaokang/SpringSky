package com.wc.single.sky.disclosures.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wc.single.sky.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosuresItemVO {

    /**
     * 返回给前端的ID
     */
    private String itemId;

    private Long id;

    @Excel(name = "Item ID", width = 20)
    private String itemNumber;

    @Excel(name = "Business Group")
    private String businessGroup;

    @Excel(name = "Geo")
    private String geoCode;

    private String disclosuresId;

    @Excel(name = "Disclosure Item Key title", width = 20)
    private String keyInfo;

    private String region;

    private String description;

    private String rootCause;

    private String impact;

    private String inQtrBooks;

    private String itemClassification;

    private String action;

    @Excel(name = "Owner")
    private String owner;

    @Excel(name = "Issue Status")
    private String issueStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date targetDate;

    private String acctEntry;

    private String inPriorQuarter;

    @Excel(name = "Created By")
    private String createBy;

    @Excel(name = "Process Status")
    private String status;

    private String delFlag;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

//    private List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList;

    private List<DisclosureItemCommentVO> disclosureItemCommentVOList;

    private List<DisclosureS3AttachmentVO> disclosureS3AttachmentVOList;

    private DisclosuresBasicVO disclosuresBasicVO;

    private String operator;
}
