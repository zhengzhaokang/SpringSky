package com.wc.single.sky.common;


public class NewRedisMasterDataKey {

    private NewRedisMasterDataKey() {
    }

    private static final String COMMON = "MASTERDATA:";
    public  static final String TOS = COMMON + "TOS";
    /**
     * 原biz_base_bp_type表的bwbu
     * 变更为biz_base_profit_center表的bwbu
     */
    public  static final String BW_BU = COMMON + "BW_BU";
    public  static final String END_CUSTOMER_ID = COMMON + "END_CUSTOMER_ID";
    public static final String PROFIT_CENTER = COMMON + "BIZ_BASE_PROFIT_CENTER";
    /**
     * 原biz_base_bp_type表的bp_type
     * 变更为sys_dict_data表的dict_value(条件为:dict_type = "partner_typ")
     */
    public static final String BP_TYPE = COMMON + "BP_TYPE";
    public static final String SALES_ORG = COMMON + "SALES_ORG_CODE";
    public static final String SALES_OFFICE = COMMON + "SALES_OFFICE_CODE";
    public static final String APC_CODE = COMMON + "APC_CODE";
    public static final String SEGMENT = COMMON + "BIZ_BASE_SEGMENT";
    public static final String SEGMENT_LEVEL1 = COMMON + "BIZ_BASE_SEGMENT:LEVEL1";
    public static final String SEGMENT_LEVEL2 = COMMON + "BIZ_BASE_SEGMENT:LEVEL2";
    public static final String SEGMENT_LEVEL3 = COMMON + "BIZ_BASE_SEGMENT:LEVEL3";
    public static final String PH_LEVEL = COMMON + "PH_LEVEL";
    public static final String CUSTOMER = COMMON + "CUSTOMER";
    public static final String GTN_TYPE = COMMON + "BIZ_GTN_TYPE";
    public static final String GTN_CATEGORY_L0 = COMMON + "BIZ_BASE_GTN_CATEGORY:L0";
    public static final String GTN_CATEGORY_L1 = COMMON + "BIZ_BASE_GTN_CATEGORY:L1";

    public static final String REGION_MARKET_CODE = COMMON + "REGION_MARKET_CODE";
    public static final String MTM = COMMON + "MTM";
    public static final String CURRENCY = COMMON + "BIZ_BASE_CURRENCY";
    public static final String BIZ_BASE_DC_DIVISION_MAPPING = COMMON + "BIZ_BASE_DC_DIVISION_MAPPING";
    /***************************************vendor_bank*************************/
    public static final String VENDOR_BANK = COMMON + "BIZ_BASE_VENDOR_BANK";
    public static final String VENDOR = COMMON + "BIZ_BASE_VENDOR";
    public static final String BANK = COMMON + "BIZ_BASE_BANK";
    /**
     * 原biz_base_bp_type_customer表的customer_number
     * 变更为biz_base_mbg_customer_drm_toms_tofi表的key_account
     */
    public static final String KEY_ACCOUNT = COMMON + "BP_TYPE:CUSTOMER";


    public static final String TERRITORY = COMMON + "TERRITORY";


}
