package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseTerritoryRelation对象 biz_base_territory_relation
 * 
 * @author lovefamily
 * @date 2022-04-26
 */
public class BizBaseTerritoryRelationDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** GEO */
    @Excel(name = "GEO")
    private String geoCode;

    /** Region/Market */
    @Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Region/Market Name */
    @Excel(name = "Region/Market Name")
    private String regionMarketName;

    /** Territory */
    @Excel(name = "Territory")
    private String territory;

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
    public void setBusinessGroup(String businessGroup) 
    {
        this.businessGroup = businessGroup;
    }

    public String getBusinessGroup() 
    {
        return businessGroup;
    }
    public void setGeoCode(String geoCode) 
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode() 
    {
        return geoCode;
    }
    public void setRegionMarketCode(String regionMarketCode) 
    {
        this.regionMarketCode = regionMarketCode;
    }

    public String getRegionMarketCode() 
    {
        return regionMarketCode;
    }
    public void setRegionMarketName(String regionMarketName) 
    {
        this.regionMarketName = regionMarketName;
    }

    public String getRegionMarketName() 
    {
        return regionMarketName;
    }
    public void setTerritory(String territory) 
    {
        this.territory = territory;
    }

    public String getTerritory() 
    {
        return territory;
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
            .append("businessGroup", getBusinessGroup())
            .append("geoCode", getGeoCode())
            .append("regionMarketCode", getRegionMarketCode())
            .append("regionMarketName", getRegionMarketName())
            .append("territory", getTerritory())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
