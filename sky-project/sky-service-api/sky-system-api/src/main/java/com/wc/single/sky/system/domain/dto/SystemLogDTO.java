package com.wc.single.sky.system.domain.dto;

import com.wc.single.sky.system.domain.BizAsyncTaskLogDTO;
import com.wc.single.sky.system.domain.SysKafkaLog;
import com.wc.single.sky.system.domain.SysLogininfor;
import com.wc.single.sky.system.domain.SysOperLog;
import lombok.Data;

@Data
public class SystemLogDTO {

    SysBusinessOperatorLogDTO sysBusinessOperatorLog;

    SysBusinessMassUploadLogDTO sysBusinessMassUploadLog;

    SysOperLog sysOperLog;

    SysKafkaLog sysKafkaLog;
    BizAsyncTaskLogDTO bizAsyncTaskLog;
    SysLogininfor sysLogininfor;

    String logType;


}
