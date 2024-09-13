package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * bank info by lgvm对象 biz_base_bank_api_lgvm
 * 
 * @author lovefamily
 * @date 2023-03-20
 */
@Data
public class BizBaseVendorBankDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String vendorCode;

    /** Bank Type */
    @Excel(name = "Bank Type")
    private String bankType;

    /** Bank Account */
    @Excel(name = "Bank Account")
    private String bankAccount;

    /** Bank Number */
    @Excel(name = "Bank Number")
    private String bankNumber;

    /** Bank Name */
    @Excel(name = "Bank Name")
    private String bankName;

    /** Bank Country */
    @Excel(name = "Bank Country")
    private String bankCountry;

    /** Swift Code */
    @Excel(name = "Swift Code")
    private String swiftCode;

    /** IBAN */
    @Excel(name = "IBAN")
    private String iban;

    /** Account Holder */
    @Excel(name = "Account Holder")
    private String accountHolder;

    /** Branch Code */
    @Excel(name = "Branch Code")
    private String branchCode;

    /** 是否逻辑删除 */
    @Excel(name = "是否逻辑删除")
    private Boolean deleteFlag;

}
