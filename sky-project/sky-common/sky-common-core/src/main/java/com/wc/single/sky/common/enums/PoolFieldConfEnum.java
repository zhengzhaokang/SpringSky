package com.wc.single.sky.common.enums;

public enum PoolFieldConfEnum {
    POOL_SUB_NAME("subName", "", ""),
    FISCAL_YEAR("poolFy", "", ""),
    FISCAL_QUARTER("poolQ", "", ""),
    REGION_MARKET("regionMarketCode","",""),
    POOL_TYPE("poolType", "", ""),
    GTN_TYPE("gtnTypeCode", "", ""),
    SALES_ORG("salesOrgCode", "", ""),
    SALES_OFFICE("salesOfficeCode", "", ""),
    SEGMENT("segmentCode", "", ""),
    PROFIT_CENTER("profitCenterCode", "", ""),

    ;

    private String fieldName;
    private String fieldDic;
    private String fieldMasterData;

    PoolFieldConfEnum(String fieldName, String fieldMapDic, String fieldMapMasterData) {
        this.fieldName = fieldName;
        this.fieldDic = fieldMapDic;
        this.fieldMasterData = fieldMapMasterData;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDic() {
        return fieldDic;
    }

    public void setFieldDic(String fieldDic) {
        this.fieldDic = fieldDic;
    }

    public String getFieldMasterData() {
        return fieldMasterData;
    }

    public void setFieldMasterData(String fieldMasterData) {
        this.fieldMasterData = fieldMasterData;
    }
}
