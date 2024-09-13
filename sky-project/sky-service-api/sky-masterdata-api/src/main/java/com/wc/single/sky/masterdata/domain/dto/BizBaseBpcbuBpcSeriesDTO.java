package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * BizBaseBpcbuBpcSeries In SDMS对象 biz_base_bpcbu_bpc_series
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
@Data
public class BizBaseBpcbuBpcSeriesDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;


    /** $column.columnComment */
    @Excel(name = "BPC BU")
    private String bpcBuCode;


    /** $column.columnComment */
    @Excel(name = "BPC Series")
    private String bpcSeriesCode;

    /** $column.columnComment */
    @Excel(name = "Status")
    private String status;

    /** $column.columnComment */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** $column.columnComment */
    @Excel(name = "BPC BU Description")
    private String bpcBuDescription;

    /** $column.columnComment */
    @Excel(name = "BPC Series Description")
    private String bpcSeriesDescription;

    /** $column.columnComment */
    @Excel(name = "End Month")
    private String endMonth;

    private String ids;

    private List<Long> idsList;
    @Excel(name = "Remark", width = 25)
    private String remark;

    /** * 创建者 */
    @Excel(name = "Applicant", width = 25)
    private String createBy;
    /** * 创建时间 */
    private Date createTime;

    /** * 创建时间 */
    @Excel(name = "Application Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    /** * 创建时间 */
    @Excel(name = "Application Time", width = 25, dateFormat = "HH:mm:ss")
    private Date createSecond;

    @Override
    public void setCreateBy(String createBy) {
        if(StringUtils.isNotBlank(createBy)){
            this.createBy = createBy.trim();
        }
    }
    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }
}
