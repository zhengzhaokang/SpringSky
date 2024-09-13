package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据权限，用户属于那个区域对象 view_data_permission
 * 
 * @author lovefamily
 * @date 2022-04-14
 */
public class ViewDataPermission extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** IT Code */
    @Excel(name = "IT Code")
    private String itCode;

    /** 菜单ID */
    @Excel(name = "菜单ID")
    private String pageKey;

    /** list、edit */
    @Excel(name = "list、edit")
    private String viewType;

    /** GEO */
    @Excel(name = "GEO")
    private String geoCode;

    /** Region Code */
    @Excel(name = "Region Code")
    private String regionCode;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** payment mode */
    @Excel(name = "payment mode")
    private String paymentMode;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setItCode(String itCode) 
    {
        this.itCode = itCode;
    }

    public String getItCode() 
    {
        return itCode;
    }
    public void setPageKey(String pageKey)
    {
        this.pageKey = pageKey;
    }

    public String getPageKey()
    {
        return pageKey;
    }
    public void setViewType(String viewType) 
    {
        this.viewType = viewType;
    }

    public String getViewType() 
    {
        return viewType;
    }
    public void setGeoCode(String geoCode)
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode()
    {
        return geoCode;
    }
    public void setRegionCode(String regionCode) 
    {
        this.regionCode = regionCode;
    }

    public String getRegionCode() 
    {
        return regionCode;
    }
    public void setBusinessGroup(String businessGroup) 
    {
        this.businessGroup = businessGroup;
    }

    public String getBusinessGroup() 
    {
        return businessGroup;
    }
    public void setPaymentMode(String paymentMode) 
    {
        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() 
    {
        return paymentMode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itCode", getItCode())
            .append("pagekey", getPageKey())
            .append("viewType", getViewType())
            .append("geo", getGeoCode())
            .append("regionCode", getRegionCode())
            .append("businessGroup", getBusinessGroup())
            .append("paymentMode", getPaymentMode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
