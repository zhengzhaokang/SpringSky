package com.wc.single.sky.system.domain.dto;

import lombok.Data;

@Data
public class SupplierSimpleDTO {

    private String id;
    private String supplierName;
    private String firstName;
    private String lastName;
    private String destination;
    private String email;
    private String phoneRegion;
    private String phone;
    private String financingModel;
    private String supplierModel;
    private String currency;
    private Double minimumNetFinancingAmount;
    private Double toUpPricing;
    private Integer invalidDaysBeforeMaturityDate;

    private String basisOfMaturityDateCalculation;
    private String paymentCycle;
    private String weekOfTheMonth;
    private Integer traceBackHistoryInvoiceDays;

    private Boolean onshore;
    private String lenovoEntityName;
    private String preferredFinancingModel;
    private String primarySupplierCode;
}