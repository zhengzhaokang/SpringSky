package com.wc.single.sky.masterdata.domain.vo;

import com.wc.single.sky.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseSegment对象 biz_base_segment
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public class BizBaseSegmentVO
{
    private static final long serialVersionUID = 1L;

    /** Segment */
    @Excel(name = "Segment")
    private String segmentCode;

    /** Segment Name */
    @Excel(name = "Segment Name")
    private String segmentName;

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("segmentCode", getSegmentCode())
            .append("segmentName", getSegmentName())
            .toString();
    }
}
