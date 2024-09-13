package com.wc.single.sky.disclosures.common.enums;


/**
 * @Desc S3桶相关的枚举
 * @Author yangshuai11
 * @Date 2022.11.08
 */
public enum S3BucketEnum {

    DISCLOSUREITEM("disclosureitem","disclosure Item"),
    ;

    private final String name;

    private final String description;

    private S3BucketEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return name;
    }
}
