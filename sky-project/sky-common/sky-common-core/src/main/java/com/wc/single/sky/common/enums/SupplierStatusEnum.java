package com.wc.single.sky.common.enums;

public enum SupplierStatusEnum {

    AWAITING("awaiting", 0),
    PENDING("pending", 1),
    ACTIVE("active", 2),
    SUSPEND("suspend",3),
    FAILED("failed",4),
    ONHOLD("onhold",5)
    ;

    public String code;
    public Integer num;

    SupplierStatusEnum(String code, Integer num){
        this.code= code;
        this.num = num;
    }

}
