package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseProductHierarchyByLevel对象 biz_base_product_hierarchy_by_level
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public class BizBaseProductHierarchyByLevelDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer phLevel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productHierarchyCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productHierarchyName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPhLevel(Integer phLevel) 
    {
        this.phLevel = phLevel;
    }

    public Integer getPhLevel() 
    {
        return phLevel;
    }
    public void setProductHierarchyCode(String productHierarchyCode) 
    {
        this.productHierarchyCode = productHierarchyCode;
    }

    public String getProductHierarchyCode() 
    {
        return productHierarchyCode;
    }
    public void setProductHierarchyName(String productHierarchyName) 
    {
        this.productHierarchyName = productHierarchyName;
    }

    public String getProductHierarchyName() 
    {
        return productHierarchyName;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("phLevel", getPhLevel())
            .append("productHierarchyCode", getProductHierarchyCode())
            .append("productHierarchyName", getProductHierarchyName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
