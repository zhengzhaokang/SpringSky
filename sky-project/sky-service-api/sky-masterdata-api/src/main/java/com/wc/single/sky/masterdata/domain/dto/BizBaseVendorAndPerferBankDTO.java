package com.wc.single.sky.masterdata.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询VendorAndPerferBank对象
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBaseVendorAndPerferBankDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String customerId;

    private String vendorCode;

    private String vendorName;

    private String vendorCountry;

    private String state;

    private String bankAccount;

    private String bankName;

    private String bankType;

    private String swiftCode;

    private String accountHolder;

    private String errorInfo;
}
