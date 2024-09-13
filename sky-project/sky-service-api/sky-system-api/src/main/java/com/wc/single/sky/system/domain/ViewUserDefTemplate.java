package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;

/**
 * 用户默认模板对象 view_user_def_template
 *
 * @author sdms
 * @date 2022-02-23
 */
public class ViewUserDefTemplate extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * IT Code
     */
    @Excel(name = "IT Code")
    private String itCode;

    /**
     * 模板ID
     */
    @Excel(name = "模板ID")
    private Integer templateId;

    public void setItCode(String itCode) {
        this.itCode = itCode;
    }

    public String getItCode() {
        return itCode;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

}
