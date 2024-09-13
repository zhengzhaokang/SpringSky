package com.wc.single.sky.masterdata.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ExchangeRateDTO {

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date rateDate;

    private String fromCurrency;

    private String toCurrency;
}
