package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.List;


/**
 * BizSdmsReportFlowStatusSummary对象 biz_sdms_report_flow_status_summary
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
@Data
public class MsgReminder extends BaseDTO
{
    private static final long serialVersionUID = 1L;


    /** ItCode */
    private List<String> itCodes;

    private String requestItCode;

    private String messageTitle;

    private String messageDetail;

    private String status;

}
