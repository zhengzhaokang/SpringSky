package com.wc.single.sky.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wc.single.sky.common.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * SysBusinessMassUploadLog对象 sys_business_mass_upload_log
 *
 * @author lovefamily
 * @date 2022-08-19
 */
@Data
public class SysBusinessMassUploadLogDTO extends DimensionDTO {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * File Name
     */
    @Excel(name = "File Name")
    private String fileName;

    /**
     * Fail,Success
     */
    @Excel(name = "Fail,Success")
    private String status;

    /**
     * Record Count
     */
    @Excel(name = "Record Count")
    private Integer recordCount;

    /**
     * Error Count
     */
    @Excel(name = "Error Count")
    private Integer errorCount;

    /**
     * Operator
     */
    @Excel(name = "Operator")
    private String operator;

    /**
     * Operation Date
     */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "Operation Date", width = 30, dateFormat = "MM/dd/yyyy")
    private Date operatorDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date operatorDateStart;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date operatorDateEnd;

    /**
     * Start Time
     */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "Start Time", width = 30, dateFormat = "MM/dd/yyyy")
    private Date startDateTime;

    /**
     * End Time
     */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "End Time", width = 30, dateFormat = "MM/dd/yyyy")
    private Date endDateTime;

    /**
     * Url
     */
    @Excel(name = "Url")
    private String url;

    /**
     * Module Type
     */
    @Excel(name = "Module Type")
    private String moduleType;

    private String geoCode;

    private String accrualVersion;

    private String businessGroup;

}
