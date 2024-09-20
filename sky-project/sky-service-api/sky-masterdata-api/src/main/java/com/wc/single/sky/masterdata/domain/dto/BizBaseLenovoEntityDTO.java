package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Lenovo Entity Table From ECC对象 biz_base_lenovo_entity
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBaseLenovoEntityDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Company Code */
    @Excel(name = "Company Code")
    private String companyCode;

    /** Country */
    @Excel(name = "Country")
    private String country;

    /** Lenovo Entity Name */
    @Excel(name = "Lenovo Entity Name")
    private String lenovoEntityName;

    /** Lenovo Entity Address */
    @Excel(name = "Lenovo Entity Address")
    private String lenovoEntityAddress;

    /** Lenovo Vat Id */
    @Excel(name = "Lenovo Vat Id")
    private String lenovoVatId;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** Data Version */
    @Excel(name = "Data Version")
    private Integer dataVersion;

    /** Delete Flag */
    @Excel(name = "Delete Flag")
    private Boolean deleteFlag;

}
