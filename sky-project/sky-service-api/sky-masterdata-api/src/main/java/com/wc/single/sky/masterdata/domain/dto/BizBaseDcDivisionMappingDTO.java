package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BaseDcDivisionMapping对象 biz_base_dc_division_mapping
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public class BizBaseDcDivisionMappingDTO extends BaseDTO
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


    /** Distribution Channel */
    @Excel(name = "Distribution Channel")
    private String dcCode;

    /** Distribution Channel Name */
    @Excel(name = "Distribution Channel Name")
    private String dcName;


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

    /** Status */
  //  @Excel(name = "Status")
    private String status;
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

    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    public String getSalesOfficeCode() {
        return salesOfficeCode;
    }

    public void setSalesOfficeCode(String salesOfficeCode) {
        this.salesOfficeCode = salesOfficeCode;
    }

    public void setDcCode(String dcCode) 
    {
        this.dcCode = dcCode;
    }

    public String getDcCode() 
    {
        return dcCode;
    }
    public void setDcName(String dcName) 
    {
        if(StringUtils.isNotBlank(dcName)){
            this.dcName = dcName.trim();
        }
    }

    public String getDcName() 
    {
        return dcName;
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
            .append("salesOrg", getSalesOrgCode())
            .append("salesOffice", getSalesOfficeCode())
            .append("dcCode", getDcCode())
            .append("dcName", getDcName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
