package com.wc.single.sky.system.domain.dto;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

@Data
public class RemoteSdmsJobLogDTO implements Serializable {

    /** 主键 */
    private Long id;

    /** geo */
    @Excel(name = "geo")
    private String geo;

    /** 执行时间 */
    @Excel(name = "执行时间", width = 30, dateFormat = "MM/dd/yyyy")
    private String   executionTime;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "MM/dd/yyyy")
    private String  jobStartTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "MM/dd/yyyy")
    private String   jobEndTime;



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

    public Date getExecutionTime() {
        if (StrUtil.isNotBlank(executionTime)) {
            try {
                return DateUtils.dateParse(executionTime, DateUtils.QDP_DEFAULT_DATE_TIME_PATTERN);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Date getJobStartTime() {
        if (StrUtil.isNotBlank(jobStartTime)) {
            try {
                return DateUtils.dateParse(jobStartTime, DateUtils.QDP_DEFAULT_DATE_TIME_PATTERN);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Date getJobEndTime() {
        if (StrUtil.isNotBlank(jobEndTime)) {
            try {
                return DateUtils.dateParse(jobEndTime, DateUtils.QDP_DEFAULT_DATE_TIME_PATTERN);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
