package com.wc.single.sky.disclosures.common.enums;

import org.apache.commons.compress.utils.Lists;

import java.util.List;

public enum DisclosureBasicEnum {
    SUBMITTED("Submitted"),

    REVIEWED("Reviewed"),

    APPROVED("Approved"),

//    REJECTED("Rejected"),

    DATA_COLLECTED("Data Collected");

    public final String code;

    DisclosureBasicEnum(String code){
        this.code= code;
    }

    public static List<String> getAllStatus() {
        List<String> statusList = Lists.newArrayList();
        for (DisclosureBasicEnum value : DisclosureBasicEnum.values()) {
            statusList.add(value.code);
        }
        return statusList;
    }
}
