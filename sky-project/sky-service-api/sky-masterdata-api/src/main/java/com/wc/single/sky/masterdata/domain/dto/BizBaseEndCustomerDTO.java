package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BaseEndCustomer对象 biz_base_end_customer
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public class BizBaseEndCustomerDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** End Customer Number */
    @Excel(name = "EndCustomerId")
    private String endCustomerId;

    /** End Customer Name */
    @Excel(name = "EndCustomerName")
    private String endCustomerName;

    /** Region/Market */
    @Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Country */
    @Excel(name = "Country")
    private String country;

    /** Status */
   // @Excel(name = "Status")
    private String status;

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
    public void setEndCustomerId(String endCustomerId) 
    {
        if(StringUtils.isNotBlank(endCustomerId)){
            this.endCustomerId = endCustomerId.trim();
        }
    }

    public String getEndCustomerId() 
    {
        return endCustomerId;
    }
    public void setEndCustomerName(String endCustomerName) 
    {
        if(StringUtils.isNotBlank(endCustomerName)){
            this.endCustomerName = endCustomerName.trim();
        }
    }

    public String getEndCustomerName() 
    {
        return endCustomerName;
    }
    public void setRegionMarketCode(String regionMarketCode) 
    {
        if(StringUtils.isNotBlank(regionMarketCode)){
            this.regionMarketCode = regionMarketCode.trim();
        }
    }

    public String getRegionMarketCode() 
    {
        return regionMarketCode;
    }
    public void setCountry(String country) 
    {
        if(StringUtils.isNotBlank(country)){
            this.country = country.trim();
        }
    }

    public String getCountry() 
    {
        return country;
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
            .append("endCustomerId", getEndCustomerId())
            .append("endCustomerName", getEndCustomerName())
            .append("regionMarketCode", getRegionMarketCode())
            .append("country", getCountry())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
