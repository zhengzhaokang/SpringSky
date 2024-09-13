package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * BaseBpcSeries对象 biz_base_bpc_series
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public class BizBaseBpcSeriesDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "GEO")
    private String geoCode;

    /** $column.columnComment */
    @Excel(name = "BPC BU")
    private String bpcBuCode;

    /** $column.columnComment */
    @Excel(name = "BW BU")
    private String bwBuCode;

    /** $column.columnComment */
    @Excel(name = "BPC Series")
    private String bpcSeriesCode;

    /** $column.columnComment */
    @Excel(name = "BPC SeriesName")
    private String bpcSeriesName;

    private String status;

    /** * 创建者 */
    @Excel(name = "Applicant", width = 25)
    private String createBy;

    /** * 创建时间 */
    private Date createTime;

    /** * 创建时间 */
    @Excel(name = "Application Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    /** * 创建时间 */
    @Excel(name = "Application Time", width = 25, dateFormat = "HH:mm:ss")
    private Date createSecond;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGeoCode(String geoCode) 
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode() 
    {
        return geoCode;
    }
    public void setBpcBuCode(String bpcBuCode) 
    {
        if(StringUtils.isNotBlank(bpcBuCode)){
            this.bpcBuCode = bpcBuCode.trim();
        }
    }

    public String getBpcBuCode() 
    {
        return bpcBuCode;
    }
    public void setBwBuCode(String bwBuCode) 
    {
        if(StringUtils.isNotBlank(bwBuCode)){
            this.bwBuCode = bwBuCode.trim();
        }
    }

    public String getBwBuCode() 
    {
        return bwBuCode;
    }
    public void setBpcSeriesCode(String bpcSeriesCode)
    {
        if(StringUtils.isNotBlank(bpcSeriesCode)){
            this.bpcSeriesCode = bpcSeriesCode.trim();
        }
    }

    public String getBpcSeriesCode() 
    {
        return bpcSeriesCode;
    }
    public void setBpcSeriesName(String bpcSeriesName) 
    {
        if(StringUtils.isNotBlank(bpcSeriesName)){
            this.bpcSeriesName = bpcSeriesName.trim();
        }
    }

    public String getBpcSeriesName() 
    {
        return bpcSeriesName;
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
    public void setCreateBy(String createBy) {
        if(StringUtils.isNotBlank(createBy)){
            this.createBy = createBy.trim();
        }
    }
    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateSecond() {
        return createSecond;
    }

    public void setCreateSecond(Date createSecond) {
        this.createSecond = createSecond;
    }

    @Override
    public void setRemark(String remark) {

        if(StringUtils.isNotBlank(remark)){
            super.setRemark(remark.trim());
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("geoCode", getGeoCode())
            .append("bpcBuCode", getBpcBuCode())
            .append("bwBuCode", getBwBuCode())
            .append("bpcSeriesCode", getBpcSeriesCode())
            .append("bpcSeriesName", getBpcSeriesName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
