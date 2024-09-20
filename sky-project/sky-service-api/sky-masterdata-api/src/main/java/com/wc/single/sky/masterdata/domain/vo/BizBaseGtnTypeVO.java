package com.wc.single.sky.masterdata.domain.vo;

import com.wc.single.sky.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseGtnType对象 biz_base_gtn_type 用于下拉框
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public class BizBaseGtnTypeVO
{
    private static final long serialVersionUID = 1L;


    /** GTN Type */
    @Excel(name = "GTN Type")
    private String gtnTypeCode;

    /** GTN Category */
    @Excel(name = "GTN Category")
    private String gtnCategoryCode;

    /** Order Reason */
    @Excel(name = "Order Reason")
    private String cndnOrderReason;

    public String getGtnTypeCode() {
        return gtnTypeCode;
    }

    public void setGtnTypeCode(String gtnTypeCode) {
        this.gtnTypeCode = gtnTypeCode;
    }

    public String getGtnCategoryCode() {
        return gtnCategoryCode;
    }

    public void setGtnCategoryCode(String gtnCategoryCode) {
        this.gtnCategoryCode = gtnCategoryCode;
    }

    public String getCndnOrderReason() {
        return cndnOrderReason;
    }

    public void setCndnOrderReason(String cndnOrderReason) {
        this.cndnOrderReason = cndnOrderReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("gtnTypeCode", getGtnTypeCode())
            .append("gtnCategoryCode", getGtnCategoryCode())
            .append("cndnOrderReason", getCndnOrderReason())
            .toString();
    }
}
