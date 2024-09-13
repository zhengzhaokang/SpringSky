package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BizBaseSalesOffice对象 biz_base_sales_office
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public class BizBaseSalesOfficeDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Sales Org */
    @Excel(name = "Sales Org")
    private String salesOrgCode;

    /** Sales Office */
    @Excel(name = "Sales Office")
    private String salesOfficeCode;

    /** Sales Office Name */
    @Excel(name = "Sales Office Name")
    private String salesOfficeName;

    /** Status */
   // @Excel(name = "Status")
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


    private String ids;
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
    public void setSalesOfficeName(String salesOfficeName) 
    {
        if(StringUtils.isNotBlank(salesOfficeName)){
            this.salesOfficeName = salesOfficeName.trim();
        }
    }

    public String getSalesOfficeName() 
    {
        return salesOfficeName;
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
            .append("salesOrgCode", getSalesOrgCode())
            .append("salesOfficeCode", getSalesOfficeCode())
            .append("salesOfficeName", getSalesOfficeName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
