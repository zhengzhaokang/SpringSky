package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BizSdmsPersonalQuickLinkConfig对象 biz_sdms_personal_quick_link_config
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public class BizSdmsPersonalQuickLinkConfig extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Quick Link Config Id */
    @Excel(name = "Quick Link Config Id")
    private Long quickLinkConfigId;

    /** User IT Code */
    @Excel(name = "User IT Code")
    private String userCode;

    /** Quick Link Title */
    @Excel(name = "Quick Link Title")
    private String quickLinkTitle;

    /** Quick Link Name */
    @Excel(name = "Quick Link Name")
    private String quickLinkName;

    /** Quick Link Dsc */
    @Excel(name = "Quick Link Dsc")
    private String quickLinkDsc;

    /** Icon */
    @Excel(name = "Icon")
    private String icon;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** Path */
    @Excel(name = "Path")
    private String path;

    public void setQuickLinkConfigId(Long quickLinkConfigId) 
    {
        this.quickLinkConfigId = quickLinkConfigId;
    }

    public Long getQuickLinkConfigId() 
    {
        return quickLinkConfigId;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setQuickLinkTitle(String quickLinkTitle) 
    {
        this.quickLinkTitle = quickLinkTitle;
    }

    public String getQuickLinkTitle() 
    {
        return quickLinkTitle;
    }
    public void setQuickLinkName(String quickLinkName) 
    {
        this.quickLinkName = quickLinkName;
    }

    public String getQuickLinkName() 
    {
        return quickLinkName;
    }
    public void setQuickLinkDsc(String quickLinkDsc) 
    {
        this.quickLinkDsc = quickLinkDsc;
    }

    public String getQuickLinkDsc() 
    {
        return quickLinkDsc;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("quickLinkConfigId", getQuickLinkConfigId())
            .append("userCode", getUserCode())
            .append("quickLinkTitle", getQuickLinkTitle())
            .append("quickLinkName", getQuickLinkName())
            .append("quickLinkDsc", getQuickLinkDsc())
            .append("icon", getIcon())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("path", getPath())
            .toString();
    }
}
