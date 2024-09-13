package com.wc.single.sky.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wc.single.sky.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * SysBusinessOperatorLog对象 sys_business_operator_log
 *
 * @author lovefamily
 * @date 2022-08-18
 */

public class SysBusinessOperatorLogDTO extends DimensionDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** Operation */
    @Excel(name = "Operation")
    private String operation;

    /** Operator */
    @Excel(name = "Operator")
    private String operator;

    /** Operation Date */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Excel(name = "Operation Date", width = 30, dateFormat = "MM/dd/yyyy")
    private Date operationDate;
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private Date operatorDateStart;
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private Date  operatorDateEnd;
    /** Target */
    @Excel(name = "Target")
    private String target;

    /** Detail */
    @Excel(name = "Detail")
    private String detail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String moduleType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public String getOperation()
    {
        return operation;
    }
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }
    public void setOperationDate(Date operationDate	)
    {
        this.operationDate	 = operationDate;
    }

    public Date getOperationDate()
    {
        return operationDate	;
    }
    public void setTarget(String target)
    {
        this.target = target;
    }

    public String getTarget()
    {
        return target;
    }
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getDetail()
    {
        return detail;
    }
    public void setModuleType(String moduleType)
    {
        this.moduleType = moduleType;
    }

    public String getModuleType()
    {
        return moduleType;
    }

    public Date getOperatorDateStart() {
        return operatorDateStart;
    }

    public void setOperatorDateStart(Date operatorDateStart) {
        this.operatorDateStart = operatorDateStart;
    }

    public Date getOperatorDateEnd() {
        return operatorDateEnd;
    }

    public void setOperatorDateEnd(Date operatorDateEnd) {
        this.operatorDateEnd = operatorDateEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("operation", getOperation())
                .append("operator", getOperator())
                .append("operationDate", getOperationDate	())
                .append("target", getTarget())
                .append("detail", getDetail())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("moduleType", getModuleType())
                .toString();
    }
}
