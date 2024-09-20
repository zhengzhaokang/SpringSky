package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * BaseApc对象 biz_base_apc
 * 
 * @author lovefamily
 * @date 2022-07-15
 */
public class BizBaseApcDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** APC Code */
    @Excel(name = "APC Code")
    private String apcCode;

    /** Internal Name */
    @Excel(name = "Internal Name")
    private String internalName;

    /** External Name */
    @Excel(name = "External Name")
    private String externalName;

    /** MTM */
    @Excel(name = "MTM")
    private String mtm;

    /** Country Code */
    @Excel(name = "Country Code")
    private String countryCode;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String mtmDescription;

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
    public void setApcCode(String apcCode) 
    {
        if(StringUtils.isNotBlank(apcCode)){
            this.apcCode = apcCode.trim();
        }
    }

    public String getApcCode() 
    {
        return apcCode;
    }
    public void setInternalName(String internalName) 
    {
        if(StringUtils.isNotBlank(internalName)){
            this.internalName = internalName.trim();
        }
    }

    public String getInternalName() 
    {
        return internalName;
    }
    public void setExternalName(String externalName) 
    {
        if(StringUtils.isNotBlank(externalName)){
            this.externalName = externalName.trim();
        }
    }

    public String getExternalName() 
    {
        return externalName;
    }
    public void setMtm(String mtm) 
    {
        if(StringUtils.isNotBlank(mtm)){
            this.mtm = mtm.trim();
        }
    }

    public String getMtm() 
    {
        return mtm;
    }
    public void setCountryCode(String countryCode) 
    {
        if(StringUtils.isNotBlank(countryCode)){
            this.countryCode = countryCode.trim();
        }
    }

    public String getCountryCode() 
    {
        return countryCode;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setMtmDescription(String mtmDescription) 
    {
        if(StringUtils.isNotBlank(mtmDescription)){
            this.mtmDescription = mtmDescription.trim();
        }
    }

    public String getMtmDescription() 
    {
        return mtmDescription;
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
            .append("apcCode", getApcCode())
            .append("internalName", getInternalName())
            .append("externalName", getExternalName())
            .append("mtm", getMtm())
            .append("countryCode", getCountryCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("mtmDescription", getMtmDescription())
            .toString();
    }
}
