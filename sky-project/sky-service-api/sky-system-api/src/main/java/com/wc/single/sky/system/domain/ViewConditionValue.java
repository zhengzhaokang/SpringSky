package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * viewConditionValue对象 view_condition_value
 * 
 * @author lovefamily
 * @date 2022-06-13
 */
public class ViewConditionValue extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer fieldId;

    /** 通过条件去过滤出要展示的字段 */
    @Excel(name = "通过条件去过滤出要展示的字段")
    private String condition;

    /** 维度值 */
    @Excel(name = "维度值")
    private String conditionValue;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setFieldId(Integer fieldId) 
    {
        this.fieldId = fieldId;
    }

    public Integer getFieldId() 
    {
        return fieldId;
    }
    public void setCondition(String condition) 
    {
        this.condition = condition;
    }

    public String getCondition() 
    {
        return condition;
    }
    public void setConditionValue(String conditionValue) 
    {
        this.conditionValue = conditionValue;
    }

    public String getConditionValue() 
    {
        return conditionValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fieldId", getFieldId())
            .append("condition", getCondition())
            .append("conditionValue", getConditionValue())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
