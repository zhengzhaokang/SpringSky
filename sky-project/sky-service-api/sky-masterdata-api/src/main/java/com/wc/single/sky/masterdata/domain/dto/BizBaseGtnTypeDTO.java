package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BaseGtnType对象 biz_base_gtn_type
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public class BizBaseGtnTypeDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** GEO */
    @Excel(name = "GEO")
    private String geoCode;

    /** GTN Type */
    @Excel(name = "GTN Type")
    private String gtnTypeCode;

    /** GTN Category */
    @Excel(name = "GTN Category")
    private String gtnCategoryCode;

    /** Order Reason */
    @Excel(name = "Order Reason")
    private String cndnOrderReason;

    /** Status */
   // @Excel(name = "Status")
    private String status;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

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
    public void setGeoCode(String geoCode) 
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode() 
    {
        return geoCode;
    }
    public void setGtnTypeCode(String gtnTypeCode) 
    {
        this.gtnTypeCode = gtnTypeCode;
    }

    public String getGtnTypeCode() 
    {
        return gtnTypeCode;
    }
    public void setGtnCategoryCode(String gtnCategoryCode) 
    {
        if(StringUtils.isNotBlank(gtnCategoryCode)){
            this.gtnCategoryCode = gtnCategoryCode.trim();
        }
    }

    public String getGtnCategoryCode() 
    {
        return gtnCategoryCode;
    }
    public void setCndnOrderReason(String cndnOrderReason) 
    {
        if(StringUtils.isNotBlank(cndnOrderReason)){
            this.cndnOrderReason = cndnOrderReason.trim();
        }
    }

    public String getCndnOrderReason() 
    {
        return cndnOrderReason;
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
            .append("gtnTypeCode", getGtnTypeCode())
            .append("gtnCategoryCode", getGtnCategoryCode())
            .append("cndnOrderReason", getCndnOrderReason())
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
