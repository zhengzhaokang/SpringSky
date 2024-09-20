package com.wc.single.sky.masterdata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Exchange Rate对象 biz_base_exchange_rate
 *
 * @author lovefamily
 * @date 2022-04-09
 */
@Data
public class RemoteBizBaseExchangeRate extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * $column.columnComment
     */
    private String currencyCode;

    /**
     * $column.columnComment
     */
    private BigDecimal rateValue;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date rateDate;

    /**
     * 状态：0：删除/作废， 1：正常
     */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private String status;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String remark;
    @Excel(name = "exchangeRateType")
    private String exchangeRateType;

    /**
     * Exchange Rate (T-L)
     */
    @Excel(name = "exchangeRateLocal")
    private BigDecimal exchangeRateL;
    /**
     * Exchange Rate (T-U)：
     */
    @Excel(name = "exchangeRateTransaction")
    private BigDecimal exchangeRateU;

    /**
     * target Exchange Rate (T-L)
     */
    @Excel(name = "targetExchangeRate")
    private BigDecimal targetExchangeRate;

}
