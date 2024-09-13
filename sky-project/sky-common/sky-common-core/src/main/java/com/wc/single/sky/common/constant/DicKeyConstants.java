package com.wc.single.sky.common.constant;


import java.util.HashSet;
import java.util.Set;

public class DicKeyConstants {
    public static Set<String> set = new HashSet<String>();
    public static final String MULTIPLE_COUNTRY_FLAG = "multiple_country_flag";
    public static final String NEW_PAYMENT_MODE = "NEW_PAYMENT_MODE";

    public static final String B_G = "business_group";
    public static final String GEO = "geo_code";
    public static final String P_TYPE = "pool_type";
    public static final String DIS_CHANNEL = "distribution_channel";
    public static final String FOB_FLAG = "fob_flag";
    public static final String RUNRATE_FLAG = "runrate_flag";
    public static final String T_C_F = "top_choice_flag";
    public static final String G_C_S_L2 = "gtm_customer_segment_l2";
    public static final String SBO = "is_sbo";
    public static final String FISCAL_YEAR = "fiscal_year";
    public static final String FISCAL_QUARTER = "fiscal_quarter";
    public static final String DIVISION = "division";
    public static final String I_C_C_L2 = "isg_customer_segment_l2";
    public static final String MANUAL_PAYMENT_MODE = "manual_payment_mode";
    public static final String MANUAL_PAYMENT_TYPE = "manual_payment_type";
    public static final String LAST_PAYMENT = "last_payment";
    public static final String DEDUCTABLE_FOR_QUALCOMM_ROYALTY = "deductable_for_qualcomm_royalty";
    public static final String ALLOCATION_TYPE = "allocation_type";
    public static final String ADJUSTMENT_ITEM_TYPE = "adjustment_Item_type";

    static {
        set.add(I_C_C_L2);
        set.add(DIVISION);
        set.add(FISCAL_QUARTER);
        set.add(FISCAL_YEAR);
        set.add(SBO);
        set.add(G_C_S_L2);
        set.add(T_C_F);
        set.add(RUNRATE_FLAG);
        set.add(FOB_FLAG);
        set.add(DIS_CHANNEL);
        set.add(P_TYPE);
        set.add(GEO);
        set.add(B_G);
        set.add(MANUAL_PAYMENT_MODE);
        set.add(MANUAL_PAYMENT_TYPE);
        set.add(LAST_PAYMENT);
        set.add(DEDUCTABLE_FOR_QUALCOMM_ROYALTY);
        set.add(ALLOCATION_TYPE);
        set.add(ADJUSTMENT_ITEM_TYPE);
    }

}
