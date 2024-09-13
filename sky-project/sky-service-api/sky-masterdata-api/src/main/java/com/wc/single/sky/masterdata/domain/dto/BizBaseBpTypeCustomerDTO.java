package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseBpTypeCustomer对象 biz_base_bp_type_customer
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public class BizBaseBpTypeCustomerDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** BP Type */
    @Excel(name = "BP Type")
    private String bpType;

    /** Customer Number */
    @Excel(name = "Customer Number")
    private String customerNumber;

    /** Customer Name */
    @Excel(name = "Customer Name")
    private String customerName;

    /** Sales Org */
    @Excel(name = "Sales Org")
    private String salesOrgCode;

    /** Sales Office */
    @Excel(name = "Sales Office")
    private String salesOfficeCode;

    /** Customer Group */
    @Excel(name = "Customer Group")
    private String customerGroup;

    /** Status */
    @Excel(name = "Status")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBpType(String bpType) 
    {
        this.bpType = bpType;
    }

    public String getBpType() 
    {
        return bpType;
    }
    public void setCustomerNumber(String customerNumber) 
    {
        this.customerNumber = customerNumber;
    }

    public String getCustomerNumber() 
    {
        return customerNumber;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
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
    public void setCustomerGroup(String customerGroup) 
    {
        this.customerGroup = customerGroup;
    }

    public String getCustomerGroup() 
    {
        return customerGroup;
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
            .append("id", getId())
            .append("bpType", getBpType())
            .append("customerNumber", getCustomerNumber())
            .append("customerName", getCustomerName())
            .append("salesOrgCode", getSalesOrgCode())
            .append("salesOfficeCode", getSalesOfficeCode())
            .append("customerGroup", getCustomerGroup())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
