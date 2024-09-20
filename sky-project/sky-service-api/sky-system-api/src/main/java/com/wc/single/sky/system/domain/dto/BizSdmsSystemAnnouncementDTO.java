package com.wc.single.sky.system.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BasePageDTO;
import lombok.Data;

/**
 * BizSdmsSystemAnnouncement对象 biz_sdms_system_announcement
 * 
 * @author lovefamily
 * @date 2023-02-28
 */
@Data
public class BizSdmsSystemAnnouncementDTO extends BasePageDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** Category */
    @Excel(name = "Category")
    private String category;

    /** Priority */
    @Excel(name = "Priority")
    private String priority;

    /** Title */
    @Excel(name = "Title")
    private String title;

    /** Detail */
    @Excel(name = "Detail")
    private String detail;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** Geo */
    @Excel(name = "Geo")
    private String geoCode;

    /** Status */
    @Excel(name = "Status")
    private String status ;

    /** Delete Flag */
    private Integer delFlag;

    private String createDateStart;
    private String createDateEnd;

}
