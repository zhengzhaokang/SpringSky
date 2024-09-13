package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * ProfitCenter Table from LGAP对象 biz_base_profit_center_lgap
 * 
 * @author lovefamily
 * @date 2023-07-01
 */
@Data
public class BizBaseProfitCenterLgapDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Profit Center */
    @Excel(name = "Profit Center")
    private String profitCenterCode;

    /** Profit Center Name */
    @Excel(name = "Profit Center Name")
    private String profitCenterName;

    /** Status */
    @Excel(name = "Status")
    private String status;

}
