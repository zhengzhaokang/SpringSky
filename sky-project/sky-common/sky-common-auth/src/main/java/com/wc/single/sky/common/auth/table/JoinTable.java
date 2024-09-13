package com.wc.single.sky.common.auth.table;

import lombok.Data;

@Data
public class JoinTable {
    /**
     * join 的表
     */
    private String joinTableName;

    /**
     * join 的外键
     */
    private String joinKey;
    /**
     * join 表的别名
     */
    private String joinAlias;
    /**
     * 对应的维度
     */
    private String dimension;

}
