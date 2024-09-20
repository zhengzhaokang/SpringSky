package com.wc.single.sky.disclosures.common.enums;

import lombok.Getter;

@Getter
public enum EmailEnum {

    NOT_SEND(0, 0),
    SUCCESS_SEND(1, 1),
    FAIL_SEND(2, 2),


    ;

    private final Integer code;
    private final Integer name;

    EmailEnum(Integer code, Integer name) {
        this.code = code;
        this.name = name;
    }
}
