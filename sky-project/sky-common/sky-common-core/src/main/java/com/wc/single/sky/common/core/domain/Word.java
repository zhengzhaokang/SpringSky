package com.wc.single.sky.common.core.domain;

import com.wc.single.sky.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * word对象 sys_wording
 * 
 * @author shirui3
 * @date 2022-05-07
 */
public class Word
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Type */
    @Excel(name = "Type")
    private String type;

    /** Category */
    @Excel(name = "Category")
    private String category;

    /** Wording Key */
    @Excel(name = "Wording Key")
    private Long wordingKey;

    /** Wording Code */
    @Excel(name = "Wording Code")
    private String wordingCode;

    /** Text In Cn */
    @Excel(name = "Text In Cn")
    private String textInCn;

    /** Text In En */
    @Excel(name = "Text In En")
    private String textInEn;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** Create Time */
    @Excel(name = "Create Time")
    private Date createTime;
    /** Create by */
    @Excel(name = "Create By")
    private String createBy;

    /** Create Time */
    @Excel(name = "Create Time")
    private Date updateTime;
    /** Update by */
    @Excel(name = "Update By")
    private String updateBy;

    /** remark */
    @Excel(name = "Remark")
    private String remark;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setWordingKey(Long wordingKey)
    {
        this.wordingKey = wordingKey;
    }

    public Long getWordingKey()
    {
        return wordingKey;
    }
    public void setWordingCode(String wordingCode) 
    {
        this.wordingCode = wordingCode;
    }

    public String getWordingCode() 
    {
        return wordingCode;
    }
    public void setTextInCn(String textInCn) 
    {
        this.textInCn = textInCn;
    }

    public String getTextInCn() 
    {
        return textInCn;
    }
    public void setTextInEn(String textInEn) 
    {
        this.textInEn = textInEn;
    }

    public String getTextInEn() 
    {
        return textInEn;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("category", getCategory())
            .append("wordingKey", getWordingKey())
            .append("wordingCode", getWordingCode())
            .append("textInCn", getTextInCn())
            .append("textInEn", getTextInEn())
            .append("status", getStatus())
            .toString();
    }
}
