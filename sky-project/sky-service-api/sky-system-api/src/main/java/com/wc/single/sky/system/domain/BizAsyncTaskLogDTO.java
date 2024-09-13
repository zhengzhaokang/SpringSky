package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 记录文件上传下载信息对象 biz_async_task_log
 *
 * @author lovefamily
 * @date 2022-10-04
 */
@Data
public class BizAsyncTaskLogDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String fullUrl;

    private String statusName;
    private String beginCreateTime;

    private String endCreateTime;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String asyncCode;

    /**
     * 模块
     */
    @Excel(name = "模块")
    private String module;

    /**
     * 业务类型
     */
    @Excel(name = "业务类型")
    private String businessType;

    /**
     * -1异常，0进行中，1完成
     */
    @Excel(name = "-1异常，0进行中，1完成")
    private Integer status;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date startDate;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date endDate;

    /**
     * 进度条
     */
    @Excel(name = "进度条")
    private Double progress;

    /**
     * 存储地址
     */
    @Excel(name = "存储地址")
    private String url;

    /**
     * 使用时间
     */
    @Excel(name = "使用时间")
    private Double useTime;

    /**
     * 错误信息
     */
    @Excel(name = "错误信息")
    private String errorInfo;

    /**
     * 任务类型
     */
    @Excel(name = "任务类型")
    private String taskType;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

}
