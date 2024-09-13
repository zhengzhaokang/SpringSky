package com.wc.single.sky.common.enums;

/**
 * @author guowb1
 * @date 2022/9/8 17:37
 */
public enum BooleanStatusEnum {
    TRUE_Y("Y", true),
    TRUE_YES("YES", true),
    FALSE_N("N", false),
    FALSE_NO("NO", false),
    ;

    private String code;
    private Boolean value;

    BooleanStatusEnum(String code, Boolean value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public boolean isValue() {
        return value;
    }
}
