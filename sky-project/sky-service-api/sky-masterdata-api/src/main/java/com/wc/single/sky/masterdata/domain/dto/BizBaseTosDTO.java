package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseTos对象 biz_base_tos
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public class BizBaseTosDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** TOS */
    @Excel(name = "TOS")
    private String memberId;

    /** Evdescription */
    @Excel(name = "Evdescription")
    private String evDescription;

    /** Parenth1 */
    @Excel(name = "Parenth1")
    private String parentH1;

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
    public void setMemberId(String memberId) 
    {
        this.memberId = memberId;
    }

    public String getMemberId() 
    {
        return memberId;
    }
    public void setEvDescription(String evDescription) 
    {
        this.evDescription = evDescription;
    }

    public String getEvDescription() 
    {
        return evDescription;
    }
    public void setParentH1(String parentH1) 
    {
        this.parentH1 = parentH1;
    }

    public String getParentH1() 
    {
        return parentH1;
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
            .append("memberId", getMemberId())
            .append("evDescription", getEvDescription())
            .append("parentH1", getParentH1())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
