package com.wc.single.sky.disclosures.common.enums;


/**
 * @Desc 标识S3文件类型的枚举
 * @Author yangshuai11
 * @Date 2022.11.08
 */
public enum S3FileTypeEnum {

    HEADER("10", "Header级文件" ,"header"),
    ITEM("11", "Item级文件","item"),
    SUBITEM("12", "SubItem级文件","subItem");

    private final String level;

    private final String description;

    private final String code;

    private S3FileTypeEnum(String level, String description, String code) {
        this.level = level;
        this.description = description;
        this.code = code;
    }

    public static S3FileTypeEnum getEnumByCode(String code) {
        for (S3FileTypeEnum s3FileTypeEnum : S3FileTypeEnum.values()) {
            if (s3FileTypeEnum.getCode().equals(code)) {
                return s3FileTypeEnum;
            }
        }
        return null;
    }


    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public String getCode() {
        return code;
    }
}
