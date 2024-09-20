package com.wc.single.sky.common.constant;

/**
 * @author guowb1
 * @description redis 常量
 * 通过常量定义redis对应key的格式，避免各自定义可能出现的redis的key重复
 * <p>
 * key格式要求：模块:业务场景:其他
 * @date 2022/6/15 11:26
 */
public class RedisConstants {

    private RedisConstants() {
    }

    /*******************************COMMON*******************************/

    /**
     * 过期时间
     */
    public static final Long REDIS_EXPIRE_TIME_ONE = 1L;
    public static final Long REDIS_EXPIRE_TIME_SEVEN = 7L;
    public static final Long REDIS_EXPIRE_TIME_TWENTY_FOUR = 24L;
    public static final Long REDIS_EXPIRE_TIME_THIRTY = 30L;
    public static final Long REDIS_EXPIRE_TIME_FOREVER = 60L * 60L * 24L * 365L;

    public static final long REDIS_EXPIRE_TIME_ONE_HOUR = 60L * 60L;
    public static final long REDIS_EXPIRE_TIME_ONE_DAY = 60L * 60L * 24L;
    public static final long REDIS_EXPIRE_TIME_ONE_WEEK = 7 * 60L * 60L * 24L;

    /*******************************BUDGET*******************************/

    /**
     * budget校验pool状态前缀
     * "BUDGET:CHECK_POOL_STATUS:" + POOL_ID
     */
    public static final String BUDGET_CHECK_POOL_STATUS_KEY_PREFIX = "BUDGET:CHECK_POOL_STATUS";


    /*******************************FACTOR*********************************/
    /**
     * factor缓存version数据前缀 "FACTOR:ACTIVE_VERSION:" + BUSINESS_GROUP + GEO
     */
    public static final String FACTOR_ACTIVE_VERSION = "FACTOR:ACTIVE_VERSION";
    /**
     * factor缓存priority level数据前缀 "FACTOR:PRIORITY_ACTIVE_ALL_LEVEL:" + BUSINESS_GROUP + GEO + VERSION_CODE
     */
    public static final String FACTOR_PRIORITY_ACTIVE_ALL_LEVEL = "FACTOR:PRIORITY_ACTIVE_ALL_LEVEL";

    /**
     * factor缓存priority 具体level的配置数据前缀 "FACTOR:PRIORITY_ACTIVE_LEVEL_INFO:" + BUSINESS_GROUP + GEO + VERSION_CODE + LEVEL
     */
    public static final String FACTOR_PRIORITY_ACTIVE_LEVEL_INFO = "FACTOR:PRIORITY_ACTIVE_LEVEL_INFO";

    /**
     * factor缓存record数据前缀 "FACTOR:RECORD_ACTIVE_ALL_RECORD:" + BUSINESS_GROUP + GEO + VERSION_CODE
     */
    public static final String FACTOR_RECORD_ACTIVE_ALL_RECORD = "FACTOR:RECORD_ACTIVE_ALL_RECORD";

    /**
     * factor缓存record 具体record的配置数据前缀 "FACTOR:RECORD_ACTIVE_RECORD_INFO:" + BUSINESS_GROUP + GEO + RECORD_ID
     */
    public static final String FACTOR_RECORD_ACTIVE_RECORD_INFO = "FACTOR:RECORD_ACTIVE_RECORD_INFO";

    /**
     * factor缓存record 具体field数据前缀 "FACTOR:RECORD_ACTIVE_FIELD_INFO:" + BUSINESS_GROUP + GEO + VERSION_CODE + FIELD + FIELD_VALUE
     */
    public static final String FACTOR_RECORD_ACTIVE_FIELD_INFO = "FACTOR:RECORD_ACTIVE_FIELD_INFO";

    /**
     * factor缓存record dimension数据前缀 "FACTOR:RECORD_ACTIVE_ALL_DIMENSION:" + BUSINESS_GROUP + GEO + VERSION_CODE
     */
    public static final String FACTOR_RECORD_ACTIVE_ALL_DIMENSION = "FACTOR:RECORD_ACTIVE_ALL_DIMENSION";

    /**
     * factor缓存record status数据前缀 "FACTOR:RECORD_STATUS:" + RECORD_ID
     */
    public static final String FACTOR_RECORD_STATUS = "FACTOR:RECORD_STATUS";
    /**
     * factor缓存rule数据前缀 "FACTOR:RULE_VALID_RULE_INFO:" + RULE业务主键（buinessGroup、geoCode、其他业务主键）
     */
    public static final String FACTOR_RULE_VALID_RULE_INFO = "FACTOR:RULE_VALID_RULE_INFO";
    /**
     * factor缓存record 具体field对应具体值并集数据前缀 "FACTOR:RECORD_ACTIVE_FIELD_UNION_INFO:" + BUSINESS_GROUP + GEO + VERSION_CODE + FIELD + FIELD_VALUE
     */
    public static final String FACTOR_RECORD_ACTIVE_FIELD_UNION_INFO = "FACTOR:RECORD_ACTIVE_FIELD_UNION_INFO";

    /**
     * 异步任务的key
     */
    public static final String ASYNC_PREFIX = "async:task";
    public static final String ASYNC_ROW = "row";


    /*******************************ACCRUAL*********************************/
    /**
     * accrual 缓存task info unique数据前缀 "ACCRUAL:TASK_INFO_UNIQUE:" + BUSINESS_GROUP + GEO + MESSAGE ID
     */
    public static final String ACCRUAL_TASK_INFO_UNIQUE = "ACCRUAL:TASK_INFO_UNIQUE";
    /**
     * accrual 缓存task info数据前缀 "ACCRUAL:TASK_INFO_LOCK:" + BUSINESS_GROUP + GEO + TASK ID
     */
    public static final String ACCRUAL_TASK_INFO_LOCK = "ACCRUAL:TASK_INFO_LOCK";
    /**
     * accrual 缓存task status info数据前缀 "ACCRUAL:TASK_STATUS_INFO:" + BUSINESS_GROUP + GEO + TASK ID
     */
    public static final String ACCRUAL_TASK_STATUS_INFO = "ACCRUAL:TASK_STATUS_INFO";
    /**
     * accrual 缓存task execute info数据前缀 "ACCRUAL:TASK_HANDLE_LOCK:" + BUSINESS_GROUP + GEO + TASK ID
     */
    public static final String ACCRUAL_TASK_HANDLE_LOCK = "ACCRUAL:TASK_HANDLE_LOCK";
    /**
     * accrual 缓存task wait count数据前缀 "ACCRUAL:TASK_HANDLE_COUNT:" + BUSINESS_GROUP + GEO + TASK ID
     */
    public static final String ACCRUAL_TASK_WAIT_COUNT = "ACCRUAL:TASK_WAIT_COUNT";
    /**
     * accrual 缓存task handle count数据前缀 "ACCRUAL:TASK_HANDLE_COUNT:" + BUSINESS_GROUP + GEO + TASK ID
     */
    public static final String ACCRUAL_TASK_HANDLE_COUNT = "ACCRUAL:TASK_HANDLE_COUNT";
    /**
     * accrual 分布式锁 task init 数据前缀 "ACCRUAL:TASK_INIT_LOCK:" + BUSINESS_GROUP + GEO
     */
    public static final String ACCRUAL_TASK_INIT_LOCK = "ACCRUAL:TASK_INIT_LOCK";

    /**
     * accrual缓存billing 对应md5摘要前缀 "ACCRUAL:CLEAN_BILLING_INFO:" + BILLING数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_CLEAN_BILLING_INFO = "ACCRUAL:CLEAN_BILLING_INFO";
    /**
     * accrual缓存bw billing 数据前缀 "ACCRUAL:CLEAN_SYNAPSE_BILLING_INFO:" + 数据唯一标识
     */
    public static final String ACCRUAL_CLEAN_BW_BILLING_INFO = "ACCRUAL:CLEAN_BW_BILLING_INFO";
    /**
     * accrual缓存synapse billing 数据前缀 "ACCRUAL:CLEAN_SYNAPSE_BILLING_INFO:" + BATCH ID + BATCH ITEM
     */
    public static final String ACCRUAL_CLEAN_SYNAPSE_BILLING_INFO = "ACCRUAL:CLEAN_SYNAPSE_BILLING_INFO";
    /**
     * accrual缓存late billing 状态前缀 "ACCRUAL:LATE_BILLING_STATUS:" + BILLING数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_LATE_BILLING_STATUS = "ACCRUAL:LATE_BILLING_STATUS";

    /**
     * accrual缓存billing result detail 数据前缀 "ACCRUAL:CLEAN_BILLING_RESULT_DETAIL_INFO:" + 数据唯一标识（UUID）
     */
    public static final String ACCRUAL_CLEAN_BILLING_RESULT_DETAIL_INFO = "ACCRUAL:CLEAN_BILLING_RESULT_DETAIL_INFO";
    /**
     * accrual缓存outbound auto billing 数据前缀 "ACCRUAL:OUTBOUND_AUTO_BILLING_DETAIL_INFO:" + OUTBOUND数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId、gtnType）
     */
    public static final String ACCRUAL_OUTBOUND_AUTO_BILLING_DETAIL_INFO = "ACCRUAL:OUTBOUND_AUTO_BILLING_DETAIL_INFO";

    /**
     * accrual缓存late billing 数据前缀 "ACCRUAL:LATE_BILLING_DETAIL_INFO:" + billing数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId、gtnType）+ FACTOR_RECORD_ID
     */
    public static final String ACCRUAL_LATE_BILLING_DETAIL_INFO = "ACCRUAL:LATE_BILLING_DETAIL_INFO";
    /**
     * accrual缓存factor error billing 数据前缀 "ACCRUAL:FACTOR_ERROR_BILLING_DETAIL_INFO:" + billing数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_FACTOR_ERROR_BILLING_DETAIL_INFO = "ACCRUAL:FACTOR_ERROR_BILLING_DETAIL_INFO";
    /**
     * accrual缓存pool error billing 数据前缀 "ACCRUAL:POOL_ERROR_BILLING_DETAIL_INFO:" + billing数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_POOL_ERROR_BILLING_DETAIL_INFO = "ACCRUAL:POOL_ERROR_BILLING_DETAIL_INFO";

    /**
     * accrual缓存 匹配并计算billing 数据前缀 "ACCRUAL:CALCULATE_BILLING_INFO" + billing数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_CALCULATE_BILLING_INFO = "ACCRUAL:CALCULATE_BILLING_INFO";
    /**
     * accrual缓存 late标记billing 数据前缀 "ACCRUAL:LATE_MARK_BILLING_INFO" + billing数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_LATE_MARK_BILLING_INFO = "ACCRUAL:LATE_MARK_BILLING_INFO";
    /**
     * accrual 缓存retrigger track info 数据前缀 "ACCRUAL:RETRIGGER_TRACK_INFO_UNIQUE:" + BUSINESS_GROUP + GEO + RETRIGGER TRACK ID
     */
    public static final String ACCRUAL_RETRIGGER_TRACK_INFO = "ACCRUAL:RETRIGGER_TRACK_INFO";
    /**
     * accrual 缓存retrigger track info unique数据前缀 "ACCRUAL:RETRIGGER_TRACK_INFO_UNIQUE:" + MESSAGE ID
     */
    public static final String ACCRUAL_RETRIGGER_TRACK_INFO_UNIQUE = "ACCRUAL:RETRIGGER_TRACK_INFO_UNIQUE";
    /**
     * accrual 缓存calculate info unique数据前缀 "ACCRUAL:CALCULATE_INFO_UNIQUE:" + MESSAGE ID
     */
    public static final String ACCRUAL_CALCULATE_INFO_UNIQUE = "ACCRUAL:CALCULATE_INFO_UNIQUE";
    /**
     * accrual缓存retrigger pool error数据前缀 "ACCRUAL:RETRIGGER_POOL_ERROR_INFO:"+ TASK ID + billing数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_RETRIGGER_POOL_ERROR_INFO = "ACCRUAL:RETRIGGER_POOL_ERROR_INFO";

    /**
     * accrual缓存factor匹配结果数据前缀 "ACCRUAL:MATCH_BILLING_FACTOR_INFO:" + BUSINESS_GROUP + GEO + VERSION_CODE + BILLING数据业务主键（billingNumber、billingItemNumber、billingCategory、companyCode、fiscYear、mtmSourceId）
     */
    public static final String ACCRUAL_MATCH_BILLING_FACTOR_INFO = "ACCRUAL:MATCH_BILLING_FACTOR_INFO";

    /**
     * masterData
     */
    public static final String REDIS_MASTER_DATA_NAME_PREFIX = "master:";
    /**
     * dictionary
     */

    public static final String REDIS_DICTIONARY_DATA_NAME_PREFIX = "dictionary:";

    public static final String REDIS_DBTABLECOLUMN_DATA_NAME_PREFIX = "dbtablecolumn:";

    /**
     * 统计 billing 详细处理情况
     */
    public static final String ACCRUAL_DASHBOARD_COUNTER = "ACCRUAL:DASHBOARD:COUNTER";
    /**
     * 保存所有的批次
     */
    public static final String ACCRUAL_DASHBOARD_BATCH_KEY = "ACCRUAL:DASHBOARD:BATCH";


    /*******************************POOL*********************************/
    /**
     * pool缓存pool 具体field数据前缀 "POOL:POOL_ACTIVE_FIELD_INFO:" + BUSINESS_GROUP + GEO + GTN_TYPE + POOL_TYPE + FIELD + FIELD_VALUE
     */
    public static final String POOL_POOL_ACTIVE_FIELD_INFO = "POOL:POOL_ACTIVE_FIELD_INFO";
    /**
     * pool缓存pool 具体pool的数据前缀 "POOL:POOL_ACTIVE_INFO:" + BUSINESS_GROUP + GEO + POOL_ID
     */
    public static final String POOL_POOL_ACTIVE_INFO = "POOL:POOL_ACTIVE_INFO";
    /**
     * pool缓存pool dimension数据前缀 "POOL:POOL_ACTIVE_ALL_DIMENSION:" + BUSINESS_GROUP + GEO + VERSION_CODE
     */
    public static final String POOL_POOL_ACTIVE_ALL_DIMENSION = "POOL:POOL_ACTIVE_ALL_DIMENSION";


    public static final String POOL_BUDGET_P_VALUE = "POOL:VALUE:BUDGET_P";
    public static final String POOL_BUDGET_U_VALUE = "POOL:VALUE:BUDGET_U";

    public static final String POOL_CRITICAL_P_VALUE = "POOL:VALUE:CRITICAL_P";
    public static final String POOL_CRITICAL_U_VALUE = "POOL:VALUE:CRITICAL_U";

    public static final String POOL_ACCRUAL_P_VALUE = "POOL:VALUE:ACCRUAL_P";
    public static final String POOL_ACCRUAL_U_VALUE = "POOL:VALUE:ACCRUAL_U";

    public static final String POOL_AVAILABLE_BUDGET_P_VALUE = "POOL:VALUE:AVAILABLE_BUDGET_P";
    public static final String POOL_AVAILABLE_BUDGET_U_VALUE = "POOL:VALUE:AVAILABLE_BUDGET_U";

    public static final String POOL_END_BALANCE_P_VALUE = "POOL:VALUE:END_BALANCE_P";
    public static final String POOL_END_BALANCE_U_VALUE = "POOL:VALUE:END_BALANCE_U";

    public static final String POOL_RECLASS_P_VALUE = "POOL:VALUE:RECLASS_P";
    public static final String POOL_RECLASS_U_VALUE = "POOL:VALUE:RECLASS_U";

    public static final String POOL_CARRY_P_VALUE = "POOL:VALUE:CARRY_P";
    public static final String POOL_CARRY_U_VALUE = "POOL:VALUE:CARRY_U";

    public static final String POOL_CARRY_BACK_P_VALUE = "POOL:VALUE:CARRY_BACK_P";
    public static final String POOL_CARRY_BACK_U_VALUE = "POOL:VALUE:CARRY_BACK_U";

    public static final String POOL_ADDITIONAL_P_VALUE = "POOL:VALUE:ADDITIONAL_P";
    public static final String POOL_ADDITIONAL_U_VALUE = "POOL:VALUE:ADDITIONAL_U";

    public static final String POOL_UPDATE_UUID = "POOL:VALUE:UPDATE_UUID";
    public static final String KAFKA_CONSUMER_UUID = "POOL:KAFKA:UPDATE_UUID";

    public static final String POOL_UPDATE_BATCH = "BATCH";

    public static final String POOL_CODE = "POOL:CODE";

    public static final String POOL_CODE_PREFIX = "POOL:POOL_CODE:";
    public static final String POOL_INSERT_RECORD_ERROR = "RECORD_INSERT:ERROR:QUEUE";
    /*******************************消息通知*********************************/
    public static final String MSG_PREFIX = "MSG:";

    public static final String MSG_UUID_PREFIX = "MSG:UUID";
    /*通知类型*/
    public static final String COMBINE_COMPLETE = "COMBINE_COMPLETE";
    public static final String DOWNLOAD_REPORT_COMPLETE = "DOWNLOAD_REPORT_COMPLETE";
    /**************************** flowable配置相关 ***************************/
    /**
     * * 动态配置 +bg+geo+module+category
     */
    public static final String DIMENSION_CONFIG = "DIMENSION_CONFIG:";

    /**********************************************************************/
    public static final String USER = "USER:";

    public static final String USER_DETAIL = "user_details::";
    public static final String DOA = "DOA:";

    public static final String DOA_CONFIG = "DOA:CONFIG:";

    public static final String DOA_DIMENSION_CONFIG_PRIORITY = "DOA:CONFIG:DIMENSION:PRIORITY:";

    public static final String BPM_NODES = "BPM:NODES:";
    public static final String KAFKA_ERROR = "KAFKA:ERROR";

    /**
     * maturityAmount
     */
    public static final String MATURITYAMOUNT = "MATURITY:AMOUNT";

    /**
     * SupplierInvoiceCount
     */
    public static final String SUPPLIERINVOICE = "SUPPLIERINVOICE";
    public static final String SUPPLIERINVOICECOUNTSUPPLIERS = "SUPPLIERINVOICE:COUNT:SUPPLIERS";
    public static final String SUPPLIERINVOICECOUNTAWAITING = "SUPPLIERINVOICE:COUNT:AWAITING";
    public static final String SUPPLIERINVOICECOUNTPENDING = "SUPPLIERINVOICE:COUNT:PENDING";
    public static final String SUPPLIERINVOICECOUNTACTIVE = "SUPPLIERINVOICE:COUNT:ACTIVE";
    public static final String SUPPLIERINVOICECOUNTSUSPEND = "SUPPLIERINVOICE:COUNT:SUSPEND";
    public static final String SUPPLIERAMOUNT = "SUPPLIERAMOUNT";
    public static final String SUPPLIERAMOUNTFINANCEDSUPPLIER = "SUPPLIERAMOUNT:FINANCEDSUPPLIER";
    public static final String SUPPLIERAMOUNTPAYABLESUPPLIER = "SUPPLIERAMOUNT:PAYABLESUPPLIER";

    /**
     * ACCOUNTSPAYABLEJOB
     */
    public static final String ACCOUNTSPAYABLEJOB = "JOB:ACCOUNTSPAYABLEJOB";

    /**
     * 生成redis的key
     *
     * @param prefix
     * @param separator
     * @param otherKeyStr
     * @return
     */
    public static String generateRedisKey(String prefix, String separator, String... otherKeyStr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        for (String keyStr : otherKeyStr) {
            stringBuilder.append(separator == null ? Constants.SEPARATE_COLON : separator);
            stringBuilder.append(keyStr);
        }
        return stringBuilder.toString();
    }

    /*******************************financing*******************************/
    public static final String INFO_BANK_PREFIX = "otfp::info::bank::";
    public static final String INFO_ENTITY_PREFIX = "otfp::info::entity::";
    public static final String INFO_SUPPLIER_PREFIX = "otfp::info::supplier::";
    public static final String INFO_ALL_BANK = "otfp::info::all::bank";
    public static final String INFO_ALL_ENTITY = "otfp::info::all::entity";
    public static final String INFO_ALL_SUPPLIER = "otfp::info::all::supplier";

    public static String getBankInfoKey(String bankId) {
        return INFO_BANK_PREFIX + bankId;
    }

    public static String getEntityInfoKey(Long entityId) {
        return INFO_ENTITY_PREFIX + entityId;
    }

    public static String getSupplierInfoKey(Long supplierId) {
        return INFO_SUPPLIER_PREFIX + supplierId;
    }
}
