package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * gtnCategory对象 biz_base_gtn_category
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
@Data
public class BizBaseGtnCategoryDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;
    private List<Long> idsList;

    private String ids;

    /** GTN Category L0 */
    @Excel(name = "GTN Category L0", width = 25 )
    private String gtnCategoryL0;

    /** GTN Category L1 */
    @Excel(name = "GTN Category L1", width = 25 )
    private String gtnCategoryL1;

    /** Order Reason For CN */
    @Excel(name = "Order Reason For CN", width = 25 )
    private String orderReasonForCn;

    /** Order Reason Description In SAP */
    @Excel(name = "Order Reason Description In SAP", width = 30 )
    private String orderReasonDescriptionInSap;

    /** Status */
    private Boolean isActive;
    private Boolean deleteFlag;

}
