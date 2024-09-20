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
public class DisclosuresBasicVO {

    /**
     * 返回给前端的ID
     */
    private String basicId;

    private Long id;

    @Excel(name = "Disclosure ID", width = 20)
    private String basicNumber;

    @Excel(name = "Business Group")
    private String businessGroup;

    @Excel(name = "Geo")
    private String geoCode;

    @Excel(name = "Year")
    private String fiscalYear;

    @Excel(name = "Quarter")
    private String quarter;

    @Excel(name = "Qdp Focal")
    private String qdpFocal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dueDate;

    @Excel(name = "Due Date")
    private String dueDateF;

    @Excel(name = "Status")
    private String status;

    private String delFlag;

    @Excel(name = "Created By")
    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String approveUuid;

    private String approver;

    private List<String> businessGroupList;

    private List<String> geoCodeList;

    private List<String> toList;

    private String emailTo;

    private Boolean canChangeStatus;
}
