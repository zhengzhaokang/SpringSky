package com.wc.single.sky.common.core.domain;

/**
 * @author guowb1
 * @date 2022/5/29 19:29
 */

public class BasePageDTO extends BaseDTO {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    /**
     * 时间范围：开始时间
     */
    private String beginTime;
    /**
     * 时间范围：结束时间
     */
    private String endTime;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    @Override
    public String getBeginTime() {
        return beginTime;
    }

    @Override
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
