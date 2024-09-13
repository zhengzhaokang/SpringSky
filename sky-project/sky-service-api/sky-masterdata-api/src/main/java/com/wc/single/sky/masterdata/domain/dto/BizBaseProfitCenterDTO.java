package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BaseProfitCenter对象 biz_base_profit_center
 *
 * @author lovefamily
 * @date 2022-04-22
 */
@Data
public class BizBaseProfitCenterDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Region/Market */
    @Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Sales Org */
    @Excel(name = "Sales Org")
    private String salesOrgCode;

    /** Sales Office */
    @Excel(name = "Sales Office")
    private String salesOfficeCode;

    /** Status */
    //@Excel(name = "Status")
    private String status;

    /** Profit Center */
    @Excel(name = "Profit Center")
    private String profitCenterCode;

    /** Division */
    @Excel(name = "Division")
    private String geoCode;

    /** BWBU */
    @Excel(name = "BWBU")
    private String bwbu;

    /** TS Think Flag */
    @Excel(name = "TS Think Flag")
    private String tsThinkFlag;

    /** Dummy GTN MTM */
    @Excel(name = "Dummy GTN MTM")
    private String dummyGtnMtm;

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

    //临时公共字段
    private String tempField;


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
    public void setRegionMarketCode(String regionMarketCode) 
    {
        this.regionMarketCode = regionMarketCode;
    }

    public String getRegionMarketCode() 
    {
        return regionMarketCode;
    }
    public void setSalesOrgCode(String salesOrgCode) 
    {
        this.salesOrgCode = salesOrgCode;
    }

    public String getSalesOrgCode() 
    {
        return salesOrgCode;
    }
    public void setSalesOfficeCode(String salesOfficeCode) 
    {
        this.salesOfficeCode = salesOfficeCode;
    }

    public String getSalesOfficeCode() 
    {
        return salesOfficeCode;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setProfitCenterCode(String profitCenterCode) 
    {
        this.profitCenterCode = profitCenterCode;
    }

    public String getProfitCenterCode() 
    {
        return profitCenterCode;
    }
    public void setGeoCode(String geoCode)
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode()
    {
        return geoCode;
    }
    public void setTsThinkFlag(String tsThinkFlag) 
    {
        this.tsThinkFlag = tsThinkFlag;
    }

    public String getTsThinkFlag() 
    {
        return tsThinkFlag;
    }
    public void setDummyGtnMtm(String dummyGtnMtm) 
    {
        this.dummyGtnMtm = dummyGtnMtm;
    }

    public String getDummyGtnMtm() 
    {
        return dummyGtnMtm;
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

    public String getTempField() {
        return tempField;
    }

    public void setTempField(String tempField) {
        this.tempField = tempField;
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
            .append("regionMarketCode", getRegionMarketCode())
            .append("salesOrgCode", getSalesOrgCode())
            .append("salesOfficeCode", getSalesOfficeCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("profitCenterCode", getProfitCenterCode())
            .append("geo", getGeoCode())
            .append("tsThinkFlag", getTsThinkFlag())
            .append("dummyGtnMtm", getDummyGtnMtm())
            .append("businessGroup", getBusinessGroup())
            .toString();
    }
}
