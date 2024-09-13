package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 页面字段展示信息对象 view_field
 *
 * @author sdms
 * @date 2022-02-16
 */
@Data
public class ViewField extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private List<Map<String, Object>> viewConditionValue;

    /**
     * $column.columnComment
     */
    private Integer id;

    /**
     * 页面KEY
     */
    @Excel(name = "页面KEY")
    private String pageKey;

    /**
     * 页面类型：列表页1，编辑页2
     */
    @Excel(name = "页面类型：列表页1，编辑页2")
    private String viewType;

    /**
     * 每一个form、list对应的key
     */
    @Excel(name = "每一个form、list对应的key")
    private String divKey;

    /**
     * 0活跃，1禁用
     */
    @Excel(name = "0活跃，1禁用")
    private Integer status;

    /**
     * 全量字段，包括字段属性
     */
    @Excel(name = "全量字段，包括字段属性")
    private String fieldJson;

    /**
     * DIV类型1：form、2：list
     */
    @Excel(name = "DIV类型1：form、2：list")
    private Integer type;

    private String viewFieldCode;
    /**
     * 表名称
     **/
    private String tableName;
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

    private String itCodeDef;

    private String menuName;

    private Integer sort;

    private String typeName;
    private String statusName;
    private Integer parentId;
    private Integer parentIdOld;
    private Integer pageParentId;
    private Integer rowId;
    private String templateFieldJson;
    private String templateName;
    private String pageKeyName;

    private String differentTemplate;

    private Long templateId;

    private List<ViewField> children;

    /**
     * 弹出绑定的字段
     */
    private String popoverBoundField;
    /**
     * 弹出绑定的字段
     */
    private String viewFieldDimensions;

}
