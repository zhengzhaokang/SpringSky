package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * bizBaseParty3rdVendor对象 biz_base_party_3rd_vendor
 * 
 * @author lovefamily
 * @date 2022-10-12
 */
@Data
public class BizBaseParty3rdVendorDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    //@Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** Geo Code */
    @Excel(name = "Geo Code")
    private String geoCode;

    /** Country */
    //@Excel(name = "Country")
    private String countryCode;

    /** Country */
    @Excel(name = "Country")
    private String countryName;

    /** 3rd party Vendor code */
    @Excel(name = "3rd party Vendor code")
    private String vendorCode;

    /** Vendor Name */
    @Excel(name = "Vendor Name")
    private String vendorName;

    /** Vendor Country */
    @Excel(name = "Vendor Country")
    private String vendorCountry;

    /** Bank Type */
    @Excel(name = "Bank Type")
    private String bankType;

    /** Bank Account */
    @Excel(name = "Bank Account")
    private String bankAccount;

    /** Bank Name */
    @Excel(name = "Bank Name")
    private String bankName;

    /** Swift Code */
    @Excel(name = "Swift Code")
    private String swiftCode;

}
