package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * lgvm vendor对象 biz_base_vendor
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBaseVendorDTO extends BaseDTO
{

    private String status;
    private String bankType;
    private String bankAccount;
    private String bankNumber;
    private String bankName;
    private String bankCountry;

    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Vendor Code */
    @Excel(name = "Vendor Code")
    private String vendorCode;

    /** Vendor Name */
    @Excel(name = "Vendor Name")
    private String vendorName;

    /** Vendor Country */
    @Excel(name = "Vendor Country")
    private String vendorCountry;

    /** State */
    @Excel(name = "State")
    private String state;

    /** City */
    @Excel(name = "City")
    private String city;

    /** VAT number */
    @Excel(name = "VAT number")
    private String vatNumber;

    /** Customer ID */
    @Excel(name = "Customer ID")
    private String customerId;

    /** India GSTIN */
    @Excel(name = "India GSTIN")
    private String indiaGstin;

    /** additionalVendorCategory */
    @Excel(name = "additionalVendorCategory")
    private String additionalVendorCategory;

    /** primaryVendorCategory */
    @Excel(name = "primaryVendorCategory")
    private String primaryVendorCategory;

    /** transactionCategories */
    @Excel(name = "transactionCategories")
    private String transactionVendorCategories;

    /** taxNumber1 */
    @Excel(name = "taxNumber1")
    private String taxNumber1;

    /** taxNumber2 */
    @Excel(name = "taxNumber2")
    private String taxNumber2;

    /** taxNumber3 */
    @Excel(name = "taxNumber3")
    private String taxNumber3;

    /** taxNumber4 */
    @Excel(name = "taxNumber4")
    private String taxNumber4;

    /** taxNumber5 */
    @Excel(name = "taxNumber5")
    private String taxNumber5;

}
