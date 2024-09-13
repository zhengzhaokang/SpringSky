package com.wc.single.sky.system.domain;

import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 页面展示模板对象 view_template
 * 
 * @author sdms
 * @date 2022-02-15
 */
@Data
public class ViewTemplate extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    private String pageKey;
    private String viewType;
    private String divKey;
    private Integer type;
    private Integer parentId;

    private Integer isDefault;

    /** 配置好的字段 */
    @Excel(name = "配置好的字段")
    private String fieldJson;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    private Integer fieldId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String name;

    /** IT Code */
    @Excel(name = "IT Code")
    private String itCode;

    /** 0活跃，1禁用 */
    @Excel(name = "0活跃，1禁用")
    private Integer status;

    List<JSONObject> fields;

    private String itCodeDef;

    private String viewTypeOld;

    private String geoOld;

    private String regionOld;

    private String businessGroupOld;

    private String paymentModeOld;

//    private String viewFieldDimensions;

    private String popoverBoundField;

    /**
     * 地区，区域
     */
    private String region;
    /**
     * payment mode
     */
    private String paymentMode;

    private String businessGroup;

    private String geoCode;

}
