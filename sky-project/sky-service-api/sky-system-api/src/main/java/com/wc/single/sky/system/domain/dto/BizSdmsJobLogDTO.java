package com.wc.single.sky.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * jobMonitor对象 biz_sdms_job_log
 * 
 * @author dhc
 * @date 2022-10-11
 */
@Data
public class BizSdmsJobLogDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** geo */
    @Excel(name = "geo")
    private String geo;

    /** 执行时间 */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "执行时间", width = 30, dateFormat = "MM/dd/yyyy")
    private Date  executionTime;

    /** 开始时间 */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "MM/dd/yyyy")
    private Date  jobStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "MM/dd/yyyy")
    private Date  jobEndTime;



    /** 任务类型 */
    @Excel(name = "任务类型")
    private String jobType;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String status;

    /** 成功条数 */
    @Excel(name = "成功条数")
    private Integer successCount;

    /** 失败条数 */
    @Excel(name = "失败条数")
    private Integer failCount;

    /** 年度 */
    @Excel(name = "年度")
    private String poolFy;

    /** 季度 */
    @Excel(name = "季度")
    private String poolQ;

    /** 模块 */
    @Excel(name = "模块")
    private String modules;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String logId;

}
