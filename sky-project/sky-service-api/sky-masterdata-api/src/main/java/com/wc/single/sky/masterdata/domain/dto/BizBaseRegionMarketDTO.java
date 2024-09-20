package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BizBaseRegionMarket对象 biz_base_region_market
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public class BizBaseRegionMarketDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** GEO */
    @Excel(name = "GEO")
    private String geoCode;

    /** Region/Market */
    @Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Region/Market Name */
    @Excel(name = "Region/Market Name")
    private String regionMarketName;

    /** Segment Level */
    @Excel(name = "Segment Level")
    private String segmentLevel;

    /** Sub GEO */
    @Excel(name = "Sub GEO")
    private String subGeo;

    /** Region Market Currency */
    @Excel(name = "Region Market Currency")
    private String regionMarketCurrency;

    /** Status */
   // @Excel(name = "Status")
    private String status;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;
    private String ids;

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    private List<Long> idsList;

    public List<Long> getIdsList() {
        return idsList;
    }

    public void setIdsList(List<Long> ids) {
        this.idsList = ids;
    }

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
    public void setRegionMarketCode(String regionMarketCode) 
    {
        this.regionMarketCode = regionMarketCode;
    }

    public String getRegionMarketCode() 
    {
        return regionMarketCode;
    }
    public void setRegionMarketName(String regionMarketName) 
    {
        this.regionMarketName = regionMarketName;
    }

    public String getRegionMarketName() 
    {
        return regionMarketName;
    }
    public void setSegmentLevel(String segmentLevel) 
    {
        this.segmentLevel = segmentLevel;
    }

    public String getSegmentLevel() 
    {
        return segmentLevel;
    }
    public void setSubGeo(String subGeo) 
    {
        this.subGeo = subGeo;
    }

    public String getSubGeo() 
    {
        return subGeo;
    }
    public void setRegionMarketCurrency(String regionMarketCurrency) 
    {
        this.regionMarketCurrency = regionMarketCurrency;
    }

    public String getRegionMarketCurrency() 
    {
        return regionMarketCurrency;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setBusinessGroup(String businessGroup) 
    {
        this.businessGroup = businessGroup;
    }

    public String getBusinessGroup() 
    {
        return businessGroup;
    }
    @Override
    public void setCreateBy(String createBy) {
        this.createBy=createBy;
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
        super.setRemark(remark);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("geoCode", getGeoCode())
            .append("regionMarketCode", getRegionMarketCode())
            .append("regionMarketName", getRegionMarketName())
            .append("segmentLevel", getSegmentLevel())
            .append("subGeo", getSubGeo())
            .append("regionMarketCurrency", getRegionMarketCurrency())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("businessGroup", getBusinessGroup())
            .toString();
    }
}
