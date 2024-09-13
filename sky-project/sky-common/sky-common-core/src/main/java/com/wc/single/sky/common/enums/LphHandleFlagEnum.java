package com.wc.single.sky.common.enums;

/**
 * * 操作类型
 */
public enum LphHandleFlagEnum {


    OFFSET_SUBMIT("offset Submit", "提交offset"),
    OFFSET_REMOVE("offset remove", "撤销offset"),
    CLOSE("Closed", "Closed"),
    COMPLETE_SETTLED("Complete Settled", "Complete Settled"),
    CANCEL("Cancelled", "Cancelled"),
    PAY_OUT_FAILED("Pay Out Failed", "Pay Out Failed"),
    ;


    LphHandleFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private final String code;
    private final String name;

    public String getCode() {
        return code;
    }

    public static LphHandleFlagEnum getEnumByCode(String code) {
        for (LphHandleFlagEnum lphHandleFlagEnum : LphHandleFlagEnum.values()) {
            if (lphHandleFlagEnum.getCode().equals(code)) {
                return lphHandleFlagEnum;
            }
        }
        return null;
    }

}
