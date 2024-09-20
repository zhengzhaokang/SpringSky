package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * BizBaseOrgOfficeDTO 对象 biz_base_geography
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
@Data
public class BizBaseOrgOfficeDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** Geo */
    @Excel(name = "Geo")
    private String geoCode;

    /** Region/Market Code */
    @Excel(name = "Region/Market Code")
    private String regionMarketCode;

    /** Region/Market Name */
    @Excel(name = "Region/Market Name")
    private String regionMarketName;

    /** Territory Code */
    @Excel(name = "Territory Code")
    private String territoryCode;

    /** Territory Name */
    @Excel(name = "Territory Name")
    private String territoryName;

    /** Sales Org Code */
    @Excel(name = "Sales Org Code")
    private String salesOrgCode;

    /** Sales Org Name */
    @Excel(name = "Sales Org Name")
    private String salesOrgName;

    @Excel(name = "Is Dummy Org")
    private String isDummy;

    @Excel(name = "Local Currency")
    private String localCurrencyCode;

    @Excel(name = "Company Code")
    private String companyCode;

    @Excel(name = "Accrual Company Code")
    private String accrualCompanyCode;

    /** Sales Office Code */
    @Excel(name = "Sales Office Code")
    private String salesOfficeCode;

    /** Sales Office Name */
    @Excel(name = "Sales Office Name")
    private String salesOfficeName;

    /** Country Code */
    @Excel(name = "Country Code")
    private String countryCode;

    /** Country Name */
    @Excel(name = "Country Name")
    private String countryName;

    /** BPC Country Code */
    @Excel(name = "BPC Country Code")
    private String bpcCountryCode;

    /** BPC Country Name */
    @Excel(name = "BPC Country Name")
    private String bpcCountryName;

    /** Distribution Channel Code */
    private String distributionChannelCode;

    /** Distribution Channel Name */
    @Excel(name = "Distribution Channel")
    private String distributionChannelName;

    /** Status */
    private String status;

    /** 数据版本 */
    private Integer dataVersion;

    /** 是否逻辑删除（0正常 1已删除） */
    private Boolean deleteFlag;

    /** Remake */
    private String remark;

    private Date createTime;

    /** * 创建者 */
    private String createBy;

    /** * 创建时间 */
    private Date createDate;

    /** * 创建时间 */
    private Date createSecond;

    private String ids;

    private List<Long> idsList;

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }

    @Override
    public void setCreateBy(String createBy) {
        if(StringUtils.isNotBlank(createBy)){
            this.createBy = createBy.trim();
        }
    }

    public void setRegionMarketName(String regionMarketName) {
        if (StringUtils.isNotBlank(regionMarketName)) {
            this.regionMarketName = regionMarketName.trim();
        }

    }

    public void setTerritoryName(String territoryName) {
        if (StringUtils.isNotBlank(territoryName)) {
            this.territoryName = territoryName.trim();
        }
    }

    public void setCountryName(String countryName) {
        if (StringUtils.isNotBlank(countryName)) {
            this.countryName = countryName.trim();
        }

    }

    public void setBpcCountryName(String bpcCountryName) {
            if (StringUtils.isNotBlank(bpcCountryName)) {
                this.bpcCountryName = bpcCountryName.trim();
            }
    }

    public void setSalesOrgName(String salesOrgName) {
            if (StringUtils.isNotBlank(salesOrgName)) {
                this.salesOrgName = salesOrgName.trim();
            }
    }

    public void setSalesOfficeName(String salesOfficeName) {
        if (StringUtils.isNotBlank(salesOfficeName)) {
            this.salesOfficeName = salesOfficeName.trim();
        }
    }

    public void setDistributionChannelName(String distributionChannelName) {
        if (StringUtils.isNotBlank(distributionChannelName)) {
            this.distributionChannelName = distributionChannelName.trim();
        }
    }
}
