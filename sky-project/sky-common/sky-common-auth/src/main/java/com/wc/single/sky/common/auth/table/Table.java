package com.wc.single.sky.common.auth.table;

import lombok.Data;

import java.util.Set;

@Data
public class Table {

    private String name;

    private String alias;

    private String joinKey;
    /**
     * 维度
     */
    private String dimension;

    private Set<JoinTable> joinTables;

}
