package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 操作日志记录表 oper_log
 * 
 * @author lovefamily
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysKafkaLog extends BaseDTO
{
    //
    private static final long serialVersionUID = -5556121284445360558L;

    /** 日志主键 */
    @Excel(name = "操作序号")
    private Long              operId;
    private Long              offset;

    private Integer           partition;
    private String           key;

    private List<Long> operIds;

    /** 操作模块 */
    @Excel(name = "topic")
    private String            topic;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer           businessType;

    /** 请求方式 */
    @Excel(name = "发送或者接收")
    private Boolean            isSend;

    /** 请求url */
    @Excel(name = "发送集群ip")
    private String            sendIp;

    /** 操作地址 */
    @Excel(name = "接收集群ip")
    private String            receiveIp;

    @Excel(name = "消息内容")
    private String            kafkaMsg;

    /** 操作状态（0正常 1异常） */
    private Boolean isError;

    /** 错误消息 */
    @Excel(name = "错误消息")
    private String            errorMsg;

    //失败报错会记录topic等信息到redis,用于记录失败重试次数,为了追踪和清理对应key,需要记录此rediskey到日志里
    private String            redisLogKey;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date                operTime;
}
