package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * BizBaseMbgCustomerDrmTomsTofi对象 biz_base_mbg_customer_drm_toms_tofi
 * 
 * @author lovefamily
 * @date 2023-03-01
 */
@Data
public class BizBaseMbgCustomerDrmTomsTofiDTO extends BaseDTO
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

    /** Sales Org Code */
    @Excel(name = "Sales Org Code")
    private String salesOrgCode;

    /** Sales Org Name */
    @Excel(name = "Sales Org Name")
    private String salesOrgName;

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

    /** Customer Number */
    @Excel(name = "Customer Number")
    private String customerNumber;

    /** Customer Name */
    @Excel(name = "Customer Name")
    private String customerName;

    /** Key Account */
    @Excel(name = "Key Account")
    private String keyAccount;

    /** Key Account Desc */
    @Excel(name = "Key Account Desc")
    private String keyAccountDesc;

    /** Customer Group */
    @Excel(name = "Customer Group")
    private String customerGroup;

    /** Customer Group Desc */
    @Excel(name = "Customer Group Desc")
    private String customerGroupDesc;

    /** Active Status */
    @Excel(name = "Active Status")
    private String activeStatus;

    //临时公共字段
    private String tempField;

}
