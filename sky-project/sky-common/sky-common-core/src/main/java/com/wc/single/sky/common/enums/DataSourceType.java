package com.wc.single.sky.common.enums;

/**
 * 数据源
 * 
 * @author lovefamily
 */
public enum DataSourceType
{
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库
     */
    SLAVE,

    /**
     * 单库单表
     */
    SINGLE,
    /**
     * 分库分表
     */
    SHARDING,
    ;
}
