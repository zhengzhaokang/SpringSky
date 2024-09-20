package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * baseComBiz对象 biz_base_com_biz
 *
 * @author sdms
 * @date 2022-01-17
 */
public class BizBaseComBizDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    private List<Integer>ids;

    /** geo */
    //@Excel(name = "geo")
    private String geoCode;

    /** 表名 */
    // @Excel(name = "Table")
    private String bizTable;

    /** 业务组 */
    @Excel(name = "BU Code")
    private String bizCode;

    /** 业务组全称 */
    @Excel(name = "BU Name")
    private String bizName;

    /** 状态：0：删除/作废， 1：正常 */
    // @Excel(name = "Status")
    private String status;

    /** 描述 */
    @Excel(name = "Remark")
    private String remark;
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
    private Integer  pageSize;
    private Integer pageNum;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
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
    public void setBizTable(String bizTable)
    {
        this.bizTable = bizTable;
    }

    public String getBizTable()
    {
        return bizTable;
    }
    public void setBizCode(String bizCode)
    {
        this.bizCode = bizCode.trim();
    }

    public String getBizCode()
    {
        return bizCode;
    }
    public void setBizName(String bizName)
    {
        this.bizName = bizName.trim();
    }

    public String getBizName()
    {
        return bizName;
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
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        if(StringUtils.isNotBlank(remark)){
            this.remark = remark.trim();
        }
    }

    @Override
    public void setCreateBy(String createBy) {
        if(StringUtils.isNotBlank(createBy)) {
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
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("geoCode", getGeoCode())
                .append("bizTable", getBizTable())
                .append("bizCode", getBizCode())
                .append("bizName", getBizName())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
