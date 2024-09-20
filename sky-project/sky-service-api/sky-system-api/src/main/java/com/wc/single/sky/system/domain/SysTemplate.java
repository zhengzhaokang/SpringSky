package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模板管理对象 sys_template
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
public class SysTemplate extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String module;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String templateCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String templateName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String templateUrl;

    /** $column.columnComment */
    private Integer delFlag;

    private Long ossId;

    private SysOss sysOss;

    private String originalName;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public SysOss getSysOss() {
        return sysOss;
    }

    public void setSysOss(SysOss sysOss) {
        this.sysOss = sysOss;
    }

    public Long getOssId() {
        return ossId;
    }

    public void setOssId(Long ossId) {
        this.ossId = ossId;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setModule(String module) 
    {
        this.module = module;
    }

    public String getModule() 
    {
        return module;
    }
    public void setTemplateCode(String templateCode) 
    {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() 
    {
        return templateCode;
    }
    public void setTemplateName(String templateName) 
    {
        this.templateName = templateName;
    }

    public String getTemplateName() 
    {
        return templateName;
    }
    public void setTemplateUrl(String templateUrl) 
    {
        this.templateUrl = templateUrl;
    }

    public String getTemplateUrl() 
    {
        return templateUrl;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("module", getModule())
            .append("templateCode", getTemplateCode())
            .append("templateName", getTemplateName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("templateUrl", getTemplateUrl())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
