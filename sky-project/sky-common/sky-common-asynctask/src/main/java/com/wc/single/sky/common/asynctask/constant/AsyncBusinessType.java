package com.wc.single.sky.common.asynctask.constant;

/**
 * 使用：业务类型，不会出现定义重复
 *
 * @author qiaodf2
 */
public class AsyncBusinessType {
    //****************************export********************************
    /**
     * payment 批量下载,
     */
    public static final String MANUAL_PAYMENT = "Manual payment";
    /**
     * Accrual billing 下载
     */
    public static final String BILLING_ACCRUAL = "Billing Accrual";

    public static final String ACCRUAL_NORMAL_BILLING_DETAIL = "Normal_Billing_History";
    public static final String ACCRUAL_LATE_BILLING_DETAIL = "Late_Billing_Result";
    public static final String ACCRUAL_FACTOR_ERROR_BILLING_DETAIL = "Factor_Error_Billing_Result";
    public static final String ACCRUAL_POOL_ERROR_BILLING_DETAIL = "Pool_Error_Billing_Result";
    public static final String ACCRUAL_GTN_FACTOR_DETAIL = "Gtn_Factor_Result";
    public static final String ACCRUAL_FACTOR_CHANGE_HISTORY_DETAIL = "Factor_Change_History_Result";
    public static final String VIEW_ITEM_LIST = "View Item List";
    //****************************import*******************************
    /**
     * payment 批量上传,
     */
    public static final String MANUAL_MASS = "Manual Mass";
    /**
     * payment feedback 批量上传,
     */
    public static final String MANUAL_FEEDBACK_MASS = "Feedback Mass";
}
