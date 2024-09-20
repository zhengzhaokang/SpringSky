package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BaseSegment对象 biz_base_segment
 *
 * @author lovefamily
 * @date 2022-04-24
 */
public class BizBaseSegmentDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * Segment
     */
    @Excel(name = "Segment")
    private String segmentCode;

    /**
     * Segment Name
     */
    @Excel(name = "Segment Name")
    private String segmentName;

    /**
     * Segment Level
     */
    @Excel(name = "Segment Level")
    private String segmentLevel;

    /**
     * Parent Segment
     */
    @Excel(name = "Parent Segment")
    private String parentSegment;

    /**
     * GPN
     */
    @Excel(name = "GPN")
    private String gpnCode;

    /**
     * Status
     */
    // @Excel(name = "Status")
    private String status;

    /**
     * Remark
     */
    @Excel(name = "Remark")
    private String remark;

    /**
     * bg
     */
    private String businessGroup;
    /**
     * 创建者
     */
    @Excel(name = "Applicant", width = 25)
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    @Excel(name = "Application Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    /**
     * 创建时间
     */
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


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSegmentCode(String segmentCode) {
        if (StringUtils.isNotBlank(segmentCode)) {
            this.segmentCode = segmentCode.trim();
        }
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentName(String segmentName) {
        if (StringUtils.isNotBlank(segmentName)) {
            this.segmentName = segmentName.trim();
        }
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentLevel(String segmentLevel) {
        if (StringUtils.isNotBlank(segmentLevel)) {
            this.segmentLevel = segmentLevel.trim();
        }
    }

    public String getSegmentLevel() {
        return segmentLevel;
    }

    public void setParentSegment(String parentSegment) {
        if (StringUtils.isNotBlank(parentSegment)) {
            this.parentSegment = parentSegment.trim();
        }
    }

    public String getParentSegment() {
        return parentSegment;
    }

    public void setGpnCode(String gpnCode) {
        if (StringUtils.isNotBlank(gpnCode)) {
            this.gpnCode = gpnCode.trim();
        }
    }

    public String getGpnCode() {
        return gpnCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    @Override
    public void setCreateBy(String createBy) {
        if (StringUtils.isNotBlank(createBy)) {
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
        if (StringUtils.isNotBlank(remark)) {
            super.setRemark(remark.trim());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("segmentCode", getSegmentCode())
                .append("segmentName", getSegmentName())
                .append("segmentLevel", getSegmentLevel())
                .append("parentSegment", getParentSegment())
                .append("gpnCode", getGpnCode())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
