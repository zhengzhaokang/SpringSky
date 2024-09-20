package com.wc.single.sky.system.domain;


import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BizSdmsReportKeyMetrics对象 biz_sdms_report_key_metrics
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public class BizSdmsReportKeyMetrics extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Key Metrics Id */
    @Excel(name = "Key Metrics Id")
    private Long keyMetricsId;

    /** Category Code */
    @Excel(name = "Category Code")
    private String categoryCode;

    /** Business Unit */
    @Excel(name = "Business Unit")
    private String businessUnit;

    /** Geo Code */
    @Excel(name = "Geo Code")
    private String geoCode;

    /** Metirc */
    @Excel(name = "Metirc")
    private String metirc;

    /** Frequency */
    @Excel(name = "Frequency")
    private String frequency;

    /** Unit */
    @Excel(name = "Unit")
    private String unit;

    /** Value */
    @Excel(name = "Value")
    private String value;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** Percentage Value */
    @Excel(name = "Percentage Value")
    private String percentageValue;

    public void setKeyMetricsId(Long keyMetricsId) 
    {
        this.keyMetricsId = keyMetricsId;
    }

    public Long getKeyMetricsId() 
    {
        return keyMetricsId;
    }
    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }
    public void setBusinessUnit(String businessUnit) 
    {
        this.businessUnit = businessUnit;
    }

    public String getBusinessUnit() 
    {
        return businessUnit;
    }
    public void setGeoCode(String geoCode) 
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode() 
    {
        return geoCode;
    }
    public void setMetirc(String metirc) 
    {
        this.metirc = metirc;
    }

    public String getMetirc() 
    {
        return metirc;
    }
    public void setFrequency(String frequency) 
    {
        this.frequency = frequency;
    }

    public String getFrequency() 
    {
        return frequency;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
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
    public void setPercentageValue(String percentageValue) 
    {
        this.percentageValue = percentageValue;
    }

    public String getPercentageValue() 
    {
        return percentageValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("keyMetricsId", getKeyMetricsId())
            .append("categoryCode", getCategoryCode())
            .append("businessUnit", getBusinessUnit())
            .append("geoCode", getGeoCode())
            .append("metirc", getMetirc())
            .append("frequency", getFrequency())
            .append("unit", getUnit())
            .append("value", getValue())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("percentageValue", getPercentageValue())
            .toString();
    }
}
