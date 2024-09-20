package com.wc.single.sky.common.enums;

/**
 * @author guowb1
 * @date 2022/9/22 16:17
 */
public enum GeoTypeEnum {
    AP,
    EMEA,
    LA,
    NA,
    ;

    public static boolean contains(String name) {
        for (GeoTypeEnum geoTypeEnum : GeoTypeEnum.values()) {
            if (geoTypeEnum.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
