package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * companyCode和localcurrency映射对象 biz_base_company_local_currecy_mapping
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Data
public class BizBaseCompanyLocalCurrecyMappingDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String companyCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String localCurrencyCode;

    /** 删除标识 */
    private Integer delFlag;

    private String companyName;

}
