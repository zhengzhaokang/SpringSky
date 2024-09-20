package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 流程实例关联表单对象 sys_instance_form
 * 
 * @author lovefamily
 * @date 2022年1月6日 14:58:17
 */

public class SysDeployForm extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 表单主键 */
    @Excel(name = "表单主键")
    private Long formId;

    /** 流程定义主键 */
    @Excel(name = "流程定义主键")
    private String deployId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFormId(Long formId) 
    {
        this.formId = formId;
    }

    public Long getFormId() 
    {
        return formId;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("formId", getFormId())
            .append("deployId", getDeployId())
            .toString();
    }
}
