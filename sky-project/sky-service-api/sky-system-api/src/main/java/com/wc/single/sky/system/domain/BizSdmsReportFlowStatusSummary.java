package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * BizSdmsReportFlowStatusSummary对象 biz_sdms_report_flow_status_summary
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public class BizSdmsReportFlowStatusSummary extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Status Summary Id */
    @Excel(name = "Status Summary Id")
    private Long statusSummaryId;

    /** Category Code */
    @Excel(name = "Category Code")
    private String categoryCode;

    /** User IT Code */
    @Excel(name = "User IT Code")
    private String userCode;

    /** Status Type */
    @Excel(name = "Status Type")
    private String statusType;

    /** Value */
    @Excel(name = "Value")
    private String value;

    /** Status */
    @Excel(name = "Status")
    private String status;

    public void setStatusSummaryId(Long statusSummaryId) 
    {
        this.statusSummaryId = statusSummaryId;
    }

    public Long getStatusSummaryId() 
    {
        return statusSummaryId;
    }
    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setStatusType(String statusType) 
    {
        this.statusType = statusType;
    }

    public String getStatusType() 
    {
        return statusType;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statusSummaryId", getStatusSummaryId())
            .append("categoryCode", getCategoryCode())
            .append("userCode", getUserCode())
            .append("statusType", getStatusType())
            .append("value", getValue())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
