package com.wc.single.sky.common.exception.message;

import com.alibaba.fastjson.JSONArray;
import com.wc.single.sky.common.utils.spring.SpringContextHolder;
import com.wc.single.sky.common.core.domain.Word;
import com.wc.single.sky.common.redis.util.RedisUtils;
import com.wc.single.sky.common.utils.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lovefamily
 * @ClassName: DefaultErrorMessage
 * @Description: 异常代码与提示
 * @date 2022年4月7日 16:55:41
 */
@Component
public class DefaultErrorMessage {

    public static final String REDIS_NAME_PREFIX = "word:";
    /***
     * 建议：100001-199999为系统级别异常定义（如系统），200001开始定义业务级别异常
     */


    /**
     * 数据库中已存在该记录
     */
    public static final Long DB_RECORD_REPEAT = 110001L;

    /**
     * 数据库中已存在该记录
     */
    public static final Long SERVER_ERROR = 110002L;

    /**
     * 请求方式不支持
     */
    public static final Long REQUEST_METHOD_IS_NOT_SUPPORTED = 110003L;

    /**
     * 导出excel sheet页不存在提示
     */
    public static final Long EXCEL_EXPORT_SHEET_CHECK = 110004L;

    /**
     * 用户不存在
     */
    public static final Long ERR_USER_NOT_EXIST = 100001L;
    /**
     * 不允许修改超级管理员用户
     */
    public static final Long ERR_NOT_ALLOW_UPDATE_SUPER_ADMIN = 100002L;
    /**
     * MAINTENENCE点击创建时如果数据存在，进行提示
     */
    public static final Long MAINTENENCE_ADD = 100003L;

    /**
     * MAINTENENCE点击修改操作时，数据不能重复
     */
    public static final Long MAINTENENCE_EDIT = 100004L;

    /**
     * master data 数据重复
     */
    public static final Long MASTER_DATA_REPEAT = 100005L;

    /**
     * master data 需要sale office 条件
     */
    public static final Long SALE_OFFICE_IS_NULL = 100006L;



    public static final Long PLS_SELECT_BIZ_TABLE = 100009L;

    /**
     * exchangeRate from不存在
     */
    public static final Long EXCHANGE_RATE_FROM_NOT_EXSIT = 100020L;

    /**
     * exchangeRate to不存在
     */
    public static final Long EXCHANGE_RATE_TO_NOT_EXSIT = 100021L;
    /**
     * 两者rate date不一致
     */
    public static final Long EXCHANGE_RATE_DATE_DIFFERENT = 100022L;
    /**
     * biz_base_org_office表org已存在
     */
    public static final Long SALES_ORG_EXSIT = 100023L;
    /**S
     * biz_base_org_office表office已存在
     */
    public static final Long SALES_OFFICE_EXSIT = 100024L;
    /**S
     * biz_base_org_office表office不能为空
     */
    public static final Long SALES_OFFICE_NOT_NULL = 100025L;

    /**
     * budget version 数据重复
     */
    public static final Long BUDGET_VERSION_REPEAT = 200001L;

    /**
     * budget create时 budget_name 校验
     */
    public static final Long BUDGET_CREATE_BUDGET_NAME_CHECK = 200002L;
    /**
     * budget create  pool 校验
     */
    public static final Long BUDGET_CREATE_POOL_DATA_CHECK = 200003L;
    /**
     * budget create add pool
     */
    public static final Long BUDGET_CREATE_ADD_POOL = 200004L;
    /**
     * budget 创建 adjustedValue check
     */
    public static final Long BUDGET_CREATE_ADJUSTED_VALUE_CHECK = 200005L;
    /**
     * budget 创建 pool 数据重复
     */
    public static final Long BUDGET_CREATE_POOL_DATA_REPEAT = 200006L;
    /**
     * budget create  pool 校验
     */
    public static final Long BUDGET_CREATE_POOL_DATA_CHECK_SUB = 200007L;
    /**
     * budget copy 校验
     */
    public static final Long BUDGET_COPY_NORMAL_CHECK = 200008L;
    /**
     * budget 导出数据为空
     */
    public static final Long BUDGET_EXPORT_NO_DATA = 200009L;
    /**
     * budget 创建 budget_value 校验
     */
    public static final Long BUDGET_CREATE_BUDGET_VALUE_CHECK = 200010L;
    /**
     * budget reclass创建pool数据 同一个flow重复
     */
    public static final Long BUDGET_RECLASS_CREATE_POOL_REPEAT_SAME_FLOW = 200100L;
    /**
     * budget reclass创建pool数据 不同flow重复
     */
    public static final Long BUDGET_RECLASS_CREATE_POOL_REPEAT_DIFFERENT_FLOW = 200101L;
    /**
     * budget reclass创建pool数据 segment数据没有交集
     */
    public static final Long BUDGET_RECLASS_CREATE_POOL_SEGMENT_NO_INTERSECTION = 200102L;
    /**
     * budget reclass创建pool数据 currency数据不同
     */
    public static final Long BUDGET_RECLASS_CREATE_POOL_CURRENCY_DIFF = 200103L;
    /**
     * budget normal创建pool数据重复
     */
    public static final Long BUDGET_NORMAL_CREATE_POOL_REPEAT = 200104L;
    /**
     * budget创建pool数据 pool code不存在
     */
    public static final Long BUDGET_CREATE_POOL_NOT_EXIST = 200105L;
    /**
     * budget 创建 reclass value 校验
     */
    public static final Long BUDGET_CREATE_RECLASS_VALUE_NULL_CHECK = 200106L;
    /**
     * budget 创建 reclass value 校验
     */
    public static final Long BUDGET_CREATE_RECLASS_VALUE_NOT_NULL_CHECK = 200107L;
    /**
     * budget 数据不存在
     */
    public static final Long BUDGET_DATA_NOT_EXIST = 200108L;
    /**
     * budget 数据版本校验
     */
    public static final Long BUDGET_DATA_VERSION_CHECK = 200109L;
    /**
     * budget创建pool数据校验 pool A仅能出现在output pool或仅能在input pool
     */
    public static final Long BUDGET_CREATE_POOL_ITEM_TYPE_CHECK = 200110L;
    /**
     * budget 附件上传 名字校验
     */
    public static final Long BUDGET_FILE_UPLOADING_FILE_SIZE_NAME = 200111L;
    /**
     *
     * normal upload error
     */
    public static final Long BUDGET_UPLOAD_ERROR_POOL_CODE_ERROR = 200120L;
    public static final Long BUDGET_UPLOAD_ERROR_POOL_NOTMATCH_ERROR = 200112L;
    public static final Long BUDGET_UPLOAD_ERROR_POOL_USING_ERROR = 200113L;
    public static final Long BUDGET_UPLOAD_ERROR_PERCENTAGE_ERROR = 200114L;
    public static final Long BUDGET_UPLOAD_ERROR_ADJUSTED_VALUE_ERROR = 200115L;
    /**
     * reclass  upload error
     */
    public static final Long BUDGET_RECLASS_UPLOAD_ERROR_POOL_SEGMENT_ERROR = 200116L;
    public static final Long BUDGET_RECLASS_UPLOAD_ERROR_ONLY_CHOOSE_POOLS_ERROR = 200117L;
    public static final Long BUDGET_RECLASS_UPLOAD_ERROR_RECLASS_VALUE_ERROR = 200118L;
    /**
     * 禁止exe
     */
    public static final Long  BUDGET_FILE_UPLOADING_FILE_TYPE= 200119L;
    /**
     * pool available budget 校验
     */
    public static final Long BUDGET_CREATE_POOL_AVAILABLE_CHECK= 200121L;

    /**
     * pool 数据重复
     */
    public static final Long POOL_DATA_REPEAT = 200011L;
    /**
     * pool currency 不存在
     */
    public static final Long POOL_CURRENCY_NOT_FOUND = 200012L;
    /**
     * pool 状态是Active
     */
    public static final Long POOL_IS_ACTIVE = 200013L;
    /**
     * pool 导出无数据
     */
    public static final Long POOL_EXPORT_NO_DATA = 200014L;
    /**
     * pool 状态是Active
     */
    public static final Long POOL_DATA_INCOMPLETE = 200015L;
    /**
     * pool code不存在
     */
    public static final Long POOL_CODE_NOT_FOUND = 200016L;

    public static final Long FROM_TO_POOL_NOT_SAME = 202000L;
    public static final Long FROM_TO_POOL_NOT_SAME_FY_FQ = 202001L;
    public static final Long FROM_SHOULD_AFTER_TO_FY_Q = 202003L;
    public static final Long POOL_ITEM_ZERO = 202002L;
    /**
     * pool 参数错误
     */
    public static final Long POOL_PARAM_NO_FISCAL_YEAR_OR_QUARTER  = 200017L;

    public static final Long POOL_BUDGET_VALUE_INSUFFICIENT  = 200018L;

    public static final Long UPDATE_POOL_IS_DELTA_EMPTY  = 200019L;

    public static final Long POOLID_IS_EMPTY  = 200020L;

    /**
     * pool导入的数据重复
     */
    public static final Long POOL_IMPORT_DATA_REPEAT = 200021L;

    public  static final  Long POOL_CHECK_CANNELLED=210022L;

    public  static final  Long CLOSE_ERROR_1=210031L;
    public  static final  Long CLOSE_ERROR_2=210032L;
    public  static final  Long CLOSE_ERROR_3=210033L;


    /**
     * MAINTENENCE点击创建时如果所有字段数据重复，进行提示
     */
    public static final Long MAINTENENCE_ADD_ALL_FIELD_REPEAT = 100007L;

    /**
     * MAINTENENCE点击创建时除fator_value值之外，其他字段数据重复，进行提示
     */
    public static final Long MAINTENENCE_ADD_NOT_ALL_FIELD_REPEAT = 100008L;

    /**
     * MAINTENENCE点击创建时除fator_value、currency值之外，其他字段数据重复，进行提示
     */
    public static final Long MAINTENENCE_ADD_NOT_ALL_FIELD_REPEAT_CURRENCY = 100010L;
    /**
     * MAINTENENCE 删除校验 1 全部 2部分 3 不能
     */

    public static final Long ACCRUAL_MAINTENENCE_DELETE_CHECK_ALL = 100011L;
    public static final Long ACCRUAL_MAINTENENCE_DELETE_CHECK_SOME = 100012L;
    public static final Long ACCRUAL_MAINTENENCE_DELETE_CHECK_NOT_ALL = 100013L;
    /**
     * PRIORITY 创建修改 唯一性校验
     */
    public static final Long ACCRUAL_PRIORITY_CREATE_OR_UPDATE_DATA_REPEAT = 200021L;
    /**
     * PRIORITY 创建 version 可用状态校验
     */
    public static final Long ACCRUAL_PRIORITY_CREATE_VERSION_REPEAT = 200022L;
    /**
     * PRIORITY 修改 version 可用状态校验
     */
    public static final Long ACCRUAL_PRIORITY_UPDATE_VERSION_REPEAT = 200023L;

    /**
     * accrual 创建 attribute 不能为空
     */
    public static final Long ACCRUAL_CREATE_ATTRIBUTE = 200024L;
    /**
     * accrual 创建version name 被使用
     */
    public static final Long ACCRUAL_CREATE_VERSION_NAME_IS_USD = 200025L;
    /**
     * accrual 创建 No Available Version
     */
    public static final Long ACCRUAL_CREATE_NO_AVAILABLE_VERSION = 200026L;

	/**
	 *  accrual 创建版本号名字重复
	 */
	public static final Long ACCRUAL_CREATE_VERSION_NAME_REPEAT =200027L;
	/**
	 * check EffectiveEnddate
	 */
	public static final Long ACCRUAL_CHECK_EFFECTIVE_END_DATE =200028L;
	/**
	 * accrual 导出 无数据
	 */
	public static final Long ACCRUAL_EXPORT_NO_DATA =200029L;
    /**
     * accrual 删除factor无数据校验
     */
    public static final Long ACCRUAL_DELETE_FACTOR_CHECK_NO_ID =200030L;

    /**
     * 创建version开始时间校验
     */
    public static final Long ACCRUAL_CREATE_VERSION_CHECK_START_DATE = 200031L;
    /**
     * upload 提示语
     */
    public static final Long ACCRUAL_UPLOAD_FIELD_CHECK = 201001L;
    public static final Long ACCRUAL_UPLOAD_EFFECTIVE_DATE_CHECK = 201002L;
    public static final Long ACCRUAL_UPLOAD_EFFECTIVE_DATE_QUARTER_CHECK = 201003L;
    public static final Long ACCRUAL_UPLOAD_CURRENCY_CHECK = 201004L;
    public static final Long ACCRUAL_UPLOAD_CURRENCY_FIELD_CHECK = 201005L;
    public static final Long ACCRUAL_UPLOAD_FACTOR_VALUE_CHECK = 201006L;
    public static final Long ACCRUAL_UPLOAD_BG_CHECK = 201007L;
    public static final Long ACCRUAL_UPLOAD_GEO_CHECK = 201008L;
    public static final Long ACCRUAL_UPLOAD_DICT_CHECK = 201009L;
    public static final Long ACCRUAL_UPLOAD_FILE_NAME = 201010L;
    public static final Long ACCRUAL_UPLOAD_FILE_TYPE = 201011L;
    public static final Long ACCRUAL_UPLOAD_FILE_SIZE = 201012L;
    public static final Long ACCRUAL_UPLOAD_CHECK_METHOD = 201013L;
    public static final Long ACCRUAL_UPLOAD_DATA_ZERO = 201014L;
    public static final Long ACCRUAL_UPLOAD_CHECK_TABLE_HEADER = 201015L;
    public static final Long ACCRUAL_UPLOAD_CHECK_EXCEL_IDENTICAL = 201016L;
    public static final Long ACCRUAL_UPLOAD_CHECK_EXCEL_UPDATE = 201017L;
    public static final Long ACCRUAL_UPLOAD_CHECK_EXCEL_DIFFERENT_CURRENCY = 201018L;

    public static final Long ACCRUAL_UPLOAD_CHECK_EXCEL_TOTAL_ERROR = 201019L;


    /**
     * accrual 计算 factor配置数据错误
     */
    public static final Long ACCRUAL_COMPUTE_FACTOR_CONFIG_ERROR = 200032L;
    /**
     * accrual 计算 factor匹配有效时间数据为空
     */
    public static final Long ACCRUAL_COMPUTE_FACTOR_MATCH_EFFECTIVE_EMPTY = 200033L;
    /**
     * accrual 计算 factor匹配维度数据为空
     */
    public static final Long ACCRUAL_COMPUTE_FACTOR_MATCH_DIMENSION_EMPTY = 200034L;
    /**
     * accrual 计算 factor计算结果数据为空
     */
    public static final Long ACCRUAL_COMPUTE_FACTOR_CALCULATE_RESULT_EMPTY = 200035L;
    /**
     * accrual 计算 factor已激活version数据为空
     */
    public static final Long ACCRUAL_COMPUTE_FACTOR_ACTIVE_VERSION_EMPTY = 200036L;
    /**
     * accrual 计算 任务数据校验
     */
    public static final Long ACCRUAL_COMPUTE_TASK_CHECK = 200037L;
    /**
     * accrual 计算 factor匹配value数据的总数为负数
     */
    public static final Long ACCRUAL_COMPUTE_TOTAL_FACTOR_VALUE_NEGATIVE = 200038L;
    /**
     * accrual 计算 pool匹配维度数据为空
     */
    public static final Long ACCRUAL_COMPUTE_POOL_MATCH_DIMENSION_EMPTY = 200039L;
    /**
     * accrual 计算 pool匹配维度数据不唯一
     */
    public static final Long ACCRUAL_COMPUTE_POOL_MATCH_RESULT_NOT_UNIQUE = 200040L;
    /**
     * accrual 计算 pool计算结果数据为空
     */
    public static final Long ACCRUAL_COMPUTE_POOL_CALCULATE_RESULT_EMPTY = 200041L;

    /**
     * accrual factor rule 校验
     */
    public static final Long ACCRUAL_FACTOR_RULE_DATA_CHECK = 200042L;
    /**
     * accrual late to normal 校验
     */
    public static final Long ACCRUAL_LATE_TO_NORMAL_CHECK = 200043L;
    /**
     * accrual late to normal version date 校验
     */
    public static final Long ACCRUAL_LATE_TO_NORMAL_DATE_CHECK = 200044L;
    /**
     * accrual late to normal reset 校验
     */
    public static final Long ACCRUAL_LATE_TO_NORMAL_RESET_CHECK = 200045L;
    /**
     * PRIORITY 创建修改 dimension校验
     */
    public static final Long ACCRUAL_PRIORITY_DIMENSION_CHECK = 200046L;
    /**
     * accrual 计算 任务数据执行
     */
    public static final Long ACCRUAL_COMPUTE_TASK_EXECUTION = 200047L;
    /**
     * accrual 计算 任务数据中断
     */
    public static final Long ACCRUAL_COMPUTE_TASK_INTERRUPT = 200048L;
    /**
     * accrual factor error to recalculate 校验
     */
    public static final Long ACCRUAL_FACTOR_ERROR_TO_RECALCULATE_CHECK = 200049L;
    /**
     * accrual pool error to recalculate 校验
     */
    public static final Long ACCRUAL_POOL_ERROR_TO_RECALCULATE_CHECK = 200050L;
    /**
     * accrual 计算 factor计算异常
     */
    public static final Long ACCRUAL_COMPUTE_FACTOR_CALCULATE_ERROR = 200051L;


	public static final Long WORD_CODE_REPEAT =300001L;
	public static final Long EXCEL_IMPORT_ERROR =300002L;
    /**
     * --------------------------------email-------------------------------------
     */
    public static final Long EMAIL_SEND_REPEAT = 300011L;
    public static final Long EMAIL_SEND_FAIL = 300012L;
    public static final Long EMAIL_NORMAL_PARAMS_ERROR = 300013L;
    public static final Long EMAIL_TABLE_PARAMS_ERROR = 300014L;
    public static final Long EMAIL_TEMPLATE_STATUS_ERROR = 300015L;
    public static final Long EMAIL_TEMPLATE_CHANGE_INACTIVE_ERROR = 300016L;
    public static final Long EMAIL_TEMPLATE_DATA_ERROR = 300017L;
    public static final Long EMAIL_TEMPLATE_PARAMS_NO_MATCH = 300018L;
    public static final Long EMAIL_TEMPLATE__NOT_EXSIT = 300019L;
    public static final Long EMAIL_TABLE_PARAMS_NOT_STRING = 300020L;

    public static final Long EMAIL_TITLE_PARAMS_NO_MATCH = 300021L;
    public static final Long EMAIL_RECIPIENT_CONFIG_EXSIT = 300022L;
    public static final Long EMAIL_RECIPIENT_EMPTY = 300023L;


    public static final Long MODULE_IS_EMPTY = 400001L;
    public static final Long ITCODES_IS_EMPTY = 400002L;
    public static final Long MSGINFO_IS_EMPTY = 400003L;
    public static final Long ITCODES_IS_NOT_ONE = 400004L;
    public static final Long HAD_READ_FLAG_ERROR = 400005L;
    public static final Long ID_IS_EMPTY = 400006L;
    public static final Long MSGTOPIC_IS_EMPTY = 400007L;
    public static final Long MSGTYPE_IS_EMPTY = 400008L;

    /**
     * --------------------------------payment-------------------------------------
     */
    /** 字段为空 hint **/
    public static final Long COMMON_PLEASE_ENTER = 500000L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_CASH_REQUIRED = 500001L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_POOL_SALES_ORG_REQUIRED = 500002L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_POOL_SALES_OFFICE_REQUIRED = 500003L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_PAYMENT_SUB_MODE_POSITIVE = 500004L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_PAYMENT_SUB_MODE_NEGATIVE = 500005L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_LAST_PAYMENT = 500006L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_ITEM_REQUIRED = 500007L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_TOTAL_AMOUNT_EQUAL = 500008L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_STATUS_APPROVING = 500009L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_POOL_RETURN_DATA_NULL = 500010L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_INVOICE_CLAIM_STATUS = 500011L;
    /**
     * submit hint
     */
    public static final Long PAYMENT_SUB_BLOCK = 500012L;
    /**
     * save hint
     */
    public static final Long PAYMENT_SAVE_POOL_AMOUNT_OVERSPENDING = 510001L;
    /**
     * save hint
     */
    public static final Long PAYMENT_SAVE_STATUS_APPROVING = 510002L;
    /**
     * withdraw hint
     */
    public static final Long PAYMENT_WITHDRAW_STATUS = 520001L;
    /**
     * payment 导出数据为空
     */
    public static final Long PAYMENT_EXPORT_NO_DATA = 530001L;
    /**
     * calculateAmount hint
     */
    public static final Long PAYMENT_CALCULATE_AMOUNT_PAYMENT_AMOUNT_T = 540001L;
    /**
     * payment file hint
     */
    public static final Long PAYMENT_FILE_UPLOADING_FILE_SIZE = 550001L;
    public static final Long PAYMENT_FILE_UPLOADING_FILE_SIZE_INVOICE = 550002L;
    public static final Long PAYMENT_FILE_UPLOADING_FILE_SIZE_NAME = 550003L;
    public static final Long PAYMENT_FILE_UPLOADING_FILE_SIZE_TYPE = 550004L;
    public static final Long PAYMENT_FILE_TEMPLATE = 550005L;
    public static final Long PAYMENT_FILE_TEMPLATE_DATA = 550006L;

    /**
     * payment block save
     */
    public static final Long PAYMENT_BLOCK_SAVE_REQUIRED = 560001L;
    public static final Long PAYMENT_BLOCK_SAVE_ALREADY_EXIST = 560002L;
    public static final Long PAYMENT_BLOCK_SAVE_MUTUAL = 560003L;
    /**
     * payment offset submit
     */
    public static final Long PAYMENT_OFFSET_SUBMIT_VALUE = 570001L;
    public static final Long PAYMENT_OFFSET_SUBMIT_VALUE_NO_0 = 570002L;
    public static final Long PAYMENT_OFFSET_SUBMIT_PROFIT_CENTER_MATCH = 570003L;
    /**
     * payment mode transform  mass
     */
    public static final Long PAYMENT_MODE_TRANSFORM_NULL = 511001L;
    public static final Long PAYMENT_MODE_TRANSFORM_SECOND_FIRST_AMOUNT_NULL = 511002L;
    public static final Long PAYMENT_MODE_TRANSFORM_FIRST_AMOUNT_GT_0 = 511003L;
    public static final Long PAYMENT_MODE_TRANSFORM_FIRST_NULL = 511004L;
    public static final Long PAYMENT_MODE_TRANSFORM_SECOND_AMOUNT_GT_0 = 511005L;
    public static final Long PAYMENT_MODE_TRANSFORM_AMOUNT_SECOND_GT_FIRST = 511006L;
    public static final Long PAYMENT_MODE_TRANSFORM_MODE_SECOND_NULL = 511007L;
    public static final Long PAYMENT_MODE_TRANSFORM_DUPLICATION = 511008L;
    public static final Long PAYMENT_MODE_EXIST = 511009L;
    /**
     * offline
     */
     public static final Long PAYMENT_OFFLINE_REPEAT = 580003L;

    /**
     * mass upload
     */
    /**
     * 不在财季时间范围
     */
    public static final Long OUT_OF_RANGE_FQ_ERROR = 700003L;
    public static final Long BLANK_FILE = 700008L;


    /**
     * 日期格式效验
     */
    public static final Long FORMAT_ERROR = 7000004L;
    public static final Long PAYMENT_CODE_NOT_EXIST = 7000005L;




    /**
     * --------------------------------claim-------------------------------------
     */

    /**
     * claim 导出无数据
     */
    public static final Long CLAIM_EXPORT_NO_DATA = 600001L;
    /**
     * 生成claim后回写入payment失败
     */
    public static final Long CLAIM_UPDATE_PAYMENT_FAIL = 600002L;
    /**
     * 搜索payment数据失败
     */
    public static final Long CLAIM_SEARCH_PAYMENT_FAIL = 600003L;
    /**
     * claim 下载attchment超过40
     */
    public static final Long CLAIM_DOWNlOAD_EXCEED = 600004L;


    public static final Long CLAIM_PLEASE_UPLOAD_INVOICE = 600005L;
    /**
     * Default Days to Deadline 页面重复校验
     */
    public static final Long CLAIM_DATA_REPEAT_CHECK = 600014L;


    public static final Long CLAIM_NUMBER_ERROR = 600006L;
    public static final Long CLAIM_INVOICE_EMPTY = 600007L;
    public static final Long PENDING_APPROVER_NULL = 600008L;
    public static final Long CLAIM_APPROVE_EXPORT_NO_DATA = 600009L;
    public static final Long REJECT_NEED_REMARK = 600010L;
    public static final Long SIC_ERROR_NOT_RECEIVED = 600011L;
    public static final Long INVOICE_UPLOAD_FAILED = 600012L;
    public static final Long FILE_NO_FOUND = 600013L;
    public static final Long SIC_INCREASE_API_ABNORMAL = 600014L;
    public static final Long UPLOAD_TAX_NOT_ITALY = 600015L;
    public static final Long FILE_NAME_ERROR = 600016L;
    public static final Long REVIEW_MAPPING_ERROR= 600017L;
    public static final Long REVIEW_MAPPING_EXIST= 600018L;
    public static final Long REVIEW_MAPPING_PARAMS_ERROR= 600019L;
    public static final Long REJECT_NEED_REASON = 600020L;

    public static final Long INCENTIVE_ID_EMPTY = 600021L;
    public static final Long CLAIM_WITHDRAW_STATUS = 620022L;
    public static final Long WORKFLOW_ERROR = 620023L;

    public static final Long PAYMENT_STATUS_ERROR = 620024L;

    public static final Long REVIEW_NEED_REASON = 600025L;
    public static final Long ONLY_UPDATE_SOME_STATUS = 600026L;
    /**
     * ------------------------------------flowable--------------------
     */
    public static final Long FLOWABLE_DOA_ADD_DATA_CHECK= 700001L;
    public static final Long FLOWABLE_DOA_ADD_Approval_GROUP_CHECK= 700002L;
    public static final Long FLOWABLE_DOA_MATCH_ERROR = 700003L;

    /*balance adjustment */
    public static final Long BAL_PLEASE_SELECT_POOL= 800001L;
    public static final Long BAL_DETAIL_NOT_COMPLETE= 800002L;
    public static final Long POOL_CODE_NOT_EXIST= 800003L;
    public static final Long POOL_CODE_NO_MATCH_POOL= 800004L;
    public static final Long POOL_CODE_IS_EMPTY= 800005L;

    public static final Long OUTPUT_INPUT_AT_LEAST_ONE = 800011L;
    public static final Long COMPLETE_CURRENT_FLOW = 800012L;
    public static final Long ALL_SHOULE_BE_THE_SAME_FISCALYQ = 800013L;
    public static final Long SHOULE_BE_THE_SAME_FISCALYQ = 800014L;
    public static final Long SHOULE_BE_THE_SAME_BEFORE_FISCALYQ = 800021L;
    public static final Long ONE_POOL_IN_ONE_FLOW_NOT_ALLOWED = 800015L;
    public static final Long ONE_POOL_PROFIT_CENTER_IN_ONE_FLOW_NOT_ALLOWED = 800020L;
    public static final Long ONE_FLOW_NOT_ALLOWED = 800016L;
    public static final Long OUTPUT_INPUT_AMOUT_NOT_EQUAL = 800017L;
    public static final Long ITEM_TYPE_NOT_CONSISTENT = 800018L;

    public static final Long BA_WITHDRAW_STATUS = 800019L;

    /**
     * critical value
     */
    public static final Long CRITICAL_VALUE_NAME_ALREADY_EXISTS = 900001L;
    public static final Long CRITICAL_VALUE_ADD_POOL = 900002L;
    public static final Long CRITICAL_NEW_VALUE_NOT_NULL = 900003L;
    public static final Long CRITICAL_CREATE_POOL_DATA_CHECK_SUB = 900004L;
    public static final Long CRITICAL_CREATE_ATTACHMENT_NULL = 900005L;
    public static final Long CRITICAL_CREATE_POOL_IDENTICAL = 900006L;
    public static final Long CRITICAL_DATA_NOT_EXIST = 900007L;

    public static final Long  CRITICAL_DATA_VERSION_CHECK=900008L;
    public static final Long CRITICAL_VALUE_APPROVE_FQ_FY_CHECK = 90009L;

    public static final Long CRITICAL_VALUE_FILE_UPLOADING_FILE_SIZE_NAME=90010L;
    public static final Long CRITICAL_VALUE_FILE_UPLOADING_FILE_SIZE=90011L;
    public static final Long CRITICAL_VALUE_FILE_UPLOAD_FILE_TYPE=90012L;

    public static final Long CRITICAL_VALUE_CHECK_POOL_CODE_ACTIVE=90013L;

    public static final Long CRITICAL_VALUE_CHECK_POOL_INFORMATION =90014L;
    public static final Long CRITICAL_VALUE_CHECK_POOL_USING_ERROR = 90015L;

    public static final Long CRITICAL_VALUE_SUBMIT_CHECK = 90016L;

    public static final Long DUPLICATE_INVITE = 250000L;
    public static final Long ALREADY_INVITED = 250001L;
    public static final Long INVALID_INVITE_CODE = 250002L;
    public static final Long DUPLICATE_REGISTER = 250003L;
    public static final Long SUPPLIER_NOT_FOUND = 250004L;
    public static final Long CONTACT_SUPPLIER_ADMIN = 250005L;
    public static final Long WAITING_FOR_ACTIVATION = 250006L;
    public static final Long SUPPLIER_HAS_BEEN_SUSPEND = 250007L;
    public static final Long SUPPLIER_STATUS_IS_WRONG = 250008L;
    public static final Long SUPPLIER_BANK_SETTING_IS_NULL = 250009L;
    public static final Long COMPANY_NAME_NOT_MATCH = 250010L;
    public static final Long ERP_IDS_IS_NULL = 250011L;
    public static final Long DIFFERENT_UNIQUE_IDS_IS_NULL = 250012L;
    public static final Long STATUS_IS_WRONG = 250013L;
    public static final Long SUPPLIER_UPDATE_FAILED = 250014L;
    public static final Long SUPPLIER_REMOTE_ACTIVE_FAILED = 250015L;
    public static final Long SUPPLIER_REMOTE_SUSPEND_FAILED = 250016L;
    public static final Long SUPPLIER_REMOTE_ON_HOLD_FAILED = 250017L;
    public static final Long SUPPLIER_REMOTE_CANCEL_HOLD_FAILED = 250018L;
    public static final Long SUPPLIER_SUBMIT_INVOICES_ID_EMPTY = 250019L;
    public static final Long SUPPLIER_CODE_RELATION_EXIST = 250020L;
    public static final Long VENDOR_CODE_IS_NULL = 250021L;
    public static final Long SUPPLIER_ID_NOT_FOUND = 250022L;
    public static final Long REGISTER_LENOVO_ID_FAILED = 250023L;
    public static final Long AVAILABLE_CURRENCY_IS_NULL = 250024L;
    public static final Long SUPPLIER_BANK_SETTING_DUPLICATE = 250025L;
    public static final Long REDIRECT_TO_LOGIN = 250100L;
    public static final Long USER_ACCOUNT_NOT_FOUND = 250101L;
    public static final Long INVOICE_SELECT_TYPE_WRONG = 250200L;
    public static final Long BANK_NAME_EXIST = 250300L;
    public static final Long BANK_NOT_FOUND = 250301L;
    public static final Long BANK_VENDOR_IS_NULL = 250302L;
    public static final Long BANK_CURRENCY_IS_NULL = 250303L;
    public static final Long BANK_HAS_ENTITY_REL = 250320L;
    public static final Long BANK_HAS_SUPPLIER_REL = 250321L;
    public static final Long ENTITY_NAME_EXIST = 250400L;
    public static final Long ENTITY_NOT_FOUND = 250401L;
    public static final Long ENTITY_HAS_SUPPLIER_REL = 250402L;
    public static final Long ENTITY_CURRENCY_IS_NULL = 250403L;
    public static final Long ENTITY_BANK_SETTING_NOT_FOUND = 250500L;
    public static final Long ENTITY_COMPANY_CODE_IS_NULL = 250600L;
    public static final Long ENTITY_COMPANY_CODE_RELATION_EXIST = 250601L;
    public static final Long INVOICE_HYGIENE_CHECK_FAILED = 250700L;
    public static final Long INVOICE_LIST_IS_NULL = 250701L;
    public static final Long INVOICE_IS_NULL = 250702L;
    public static final Long INVOICE_CURRENCY_CHECK_FAILED = 250703L;
    public static final Long INVOICE_FINANCING_TENOR_CHECK_FAILED = 250704L;
    public static final Long INVOICE_MATURITY_DATE_CHECK_FAILED = 250705L;
    public static final Long INVOICE_LIMIT_CHECK_FAILED = 250706L;
    public static final Long INVOICE_MATURITY_DATE_IS_NULL = 250707L;
    public static final Long ILLEGAL_SUBMIT_INVOICE_ID = 250720L;
    public static final Long OTHER_USER_OPERATING = 250721L;
    public static final Long ILLEGAL_PARAM_INVOICE_TYPE = 250730L;
    public static final Long DECREASE_LIMIT_FAILED = 250750L;
    public static final Long NO_MATCHED_INVOICE_BATCH = 250751L;
    public static final Long SOFR_RATE_NOT_FOUND = 250760L;
    public static final Long LESS_THAN_MIN_AMOUNT = 250761L;
    public static final Long SUBMIT_FAILED = 250762L;
    public static final Long ILLEGAL_MATURITY_DATE = 250763L;
    public static final Long DUPLICATE_BATCH_NUMBER = 250764L;

    public static final Long INVOICE_INVOICE_TYPE_ERROR = 300003L;
    public static final Long INVOICE_REFERENCE_IS_DUPLICATION_ERROR = 300004L;
    public static final Long INVOICE_TENOR_ERROR = 300005L;
    public static final Long ISSUE_DATEFUTURE_DATE_ERROR = 300006L;
    public static final Long INVOICEPAYDATE_OR_INVOICEDUEDATE_ERROR = 300007L;
    public static final Long INVOICE_REFERENCE_IS_EMPTY_ERROR = 300008L;
    public static final Long REFERENCE_START_WITH_DNF_ERROR = 300009L;

    public static final Integer SUPPLIER_ACCOUNT_EXIST = 350000;

    public static final Long REPORT_ROW_OUT_ERROR = 500000L;

    public static final Map<Long, String> ErrDescription = new HashMap<>();

    public static String getErrorMessage(Long errorCode) {
        Object message = getWordByKey(errorCode);
        if (message != null) {
            return message.toString();
        }
        return "";
    }

    /**
     * @param @param  errorCode
     * @param @param  paramstr 业务参数；譬如单据编码,用户账号
     * @param @return 参数
     * @return String 返回类型
     * @throws
     * @Title: getErrorMessage
     * @Description: 获取提示信息
     */
    public static String getErrorMessage(Long errorCode, Object... paramstrs) {
        Object message = getWordByKey(errorCode);
        if (message != null) {
            return String.format(message.toString(), paramstrs);
        }
        return "";
    }

    /**
     * @param @param  errorCode
     * @param @return 参数
     * @return Object 返回类型
     * @throws
     * @Title: getWordByKey
     * @Description: 获取提示信息
     */
    private static Object getWordByKey(Long errorCode) {
        //todo 引用redis 可能会有更好的方式, 之后优化
        //todo 如word:100013 在外面的 需要考虑如何处理, 目前是设置过期时间为1年
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        Object message = null;
        if (applicationContext != null) {
            RedisUtils redis = applicationContext.getBean(RedisUtils.class);
            String jsonWord = redis.get(REDIS_NAME_PREFIX + errorCode,String.class);
            if (StringUtils.isNotEmpty(jsonWord)) {
                Word sysWording = JSONArray.parseObject(jsonWord, Word.class);
                message = sysWording.getTextInEn();
            }
        }
        if (message == null) {
            message = ErrDescription.get(errorCode);
        }
        return message;
    }

    static {
        ErrDescription.put(EXCEL_EXPORT_SHEET_CHECK,"Invalid template, pls use the correct template");
        ErrDescription.put(DB_RECORD_REPEAT,"This record already exists in the database.");
        ErrDescription.put(SERVER_ERROR,"Server error, please contact the administrator");
        ErrDescription.put(REQUEST_METHOD_IS_NOT_SUPPORTED,"Request method is not supported");
        ErrDescription.put(ERR_USER_NOT_EXIST, "用户编码不存在！");
        ErrDescription.put(ERR_NOT_ALLOW_UPDATE_SUPER_ADMIN, "不允许修改超级管理员用户！");

        ErrDescription.put(MAINTENENCE_ADD, "*Warning: Duplicate Factor. Please confirm if you still need to submit it.");
        ErrDescription.put(MAINTENENCE_EDIT, "*Error: Duplicate Factor. Please update Factor Value");
        ErrDescription.put(MAINTENENCE_ADD_ALL_FIELD_REPEAT, "Error: Duplicate entry <%s> is found with the same Factor");
        ErrDescription.put(MAINTENENCE_ADD_NOT_ALL_FIELD_REPEAT, "Warning：Duplicate entry <%s> is found with different Factor. Are you sure to overwrite the Factor for the entry?");


        ErrDescription.put(MASTER_DATA_REPEAT, "Error: Duplicate Data. Please update Value");

        ErrDescription.put(SALE_OFFICE_IS_NULL, "Error: sale office is null.  Please select sale office");

        ErrDescription.put(POOL_DATA_REPEAT, "There is \"By Order\" Pool code %s created already with same granularity. System doesn't allow to create duplicated by order pools");
        ErrDescription.put(POOL_IS_ACTIVE, "Pool is Active, can not be deleted");
        ErrDescription.put(POOL_CURRENCY_NOT_FOUND, "Pool currency did not exists.");
        ErrDescription.put(POOL_EXPORT_NO_DATA, "no record report");
        ErrDescription.put(POOL_DATA_INCOMPLETE, "Pool' business group or region market is wanted");
        ErrDescription.put(POOL_CODE_NOT_FOUND, "Pool code not found.");
        ErrDescription.put(POOL_PARAM_NO_FISCAL_YEAR_OR_QUARTER, "fiscal year or quarter or pool code is required.");
        ErrDescription.put(POOL_BUDGET_VALUE_INSUFFICIENT, "pool budget value is insufficient,now budget value is %s");
        ErrDescription.put(UPDATE_POOL_IS_DELTA_EMPTY, "isDelta is empty!");
        ErrDescription.put(POOLID_IS_EMPTY, "isDelta is empty!");
        ErrDescription.put(POOL_IMPORT_DATA_REPEAT, "Duplicate entry is found in row %s");

        /* budget 错误码配置 */
        ErrDescription.put(BUDGET_VERSION_REPEAT, "Version already exists.");
        ErrDescription.put(BUDGET_CREATE_BUDGET_NAME_CHECK, "Budget Name already exists");
        ErrDescription.put(BUDGET_CREATE_POOL_DATA_CHECK, "System doesn't allow to select occupied Pools, please check below: %s");
        ErrDescription.put(BUDGET_CREATE_ADD_POOL, "Please select Pool");
        ErrDescription.put(BUDGET_CREATE_ADJUSTED_VALUE_CHECK, "please validate Adjusted Value to make sure New Value be not negative");
        ErrDescription.put(BUDGET_CREATE_POOL_DATA_REPEAT, "Duplicate fund Pool code %s. Please update Value");
        ErrDescription.put(BUDGET_CREATE_POOL_DATA_CHECK_SUB, "-[%s] is used by an approving Budget [%s].");
        ErrDescription.put(BUDGET_COPY_NORMAL_CHECK, "Budget cannot be copied, please confirm again.");
        ErrDescription.put(BUDGET_EXPORT_NO_DATA, "No record found");
        ErrDescription.put(BUDGET_CREATE_BUDGET_VALUE_CHECK, "Please enter Budget Value");
        ErrDescription.put(BUDGET_RECLASS_CREATE_POOL_REPEAT_SAME_FLOW, "One Pool can only be selected once in one flow.");
        ErrDescription.put(BUDGET_RECLASS_CREATE_POOL_REPEAT_DIFFERENT_FLOW, "The relationship between the Pools already exists, please maintain them in one flow.");
        ErrDescription.put(BUDGET_RECLASS_CREATE_POOL_SEGMENT_NO_INTERSECTION, "The segment doesn't match the added pool");
        ErrDescription.put(BUDGET_RECLASS_CREATE_POOL_CURRENCY_DIFF, "You can only choose Pools with the same P currency in this flow, or pls. change your selection of Currency.");
        ErrDescription.put(BUDGET_NORMAL_CREATE_POOL_REPEAT, "One Pool can only be selected once.");
        ErrDescription.put(BUDGET_CREATE_POOL_NOT_EXIST, "Pool Code doesn't exist.");
        ErrDescription.put(BUDGET_CREATE_RECLASS_VALUE_NULL_CHECK, "Please enter Reclass Value");
        ErrDescription.put(BUDGET_CREATE_RECLASS_VALUE_NOT_NULL_CHECK, "Please check Reclass Value");
        ErrDescription.put(BUDGET_DATA_NOT_EXIST, "Budget not found");
        ErrDescription.put(BUDGET_DATA_VERSION_CHECK, "Please check budget data version value");
        ErrDescription.put(BUDGET_CREATE_POOL_ITEM_TYPE_CHECK, "The same pool can only be added to the output field or input field");
        ErrDescription.put(BUDGET_UPLOAD_ERROR_POOL_CODE_ERROR,"Pool Code not found in database");
        ErrDescription.put(BUDGET_UPLOAD_ERROR_POOL_NOTMATCH_ERROR,"Pool not match mandatory information");
        ErrDescription.put(BUDGET_UPLOAD_ERROR_POOL_USING_ERROR,"Pool using by approving budget");
        ErrDescription.put(BUDGET_UPLOAD_ERROR_PERCENTAGE_ERROR,"Invalid Release Percentage");
        ErrDescription.put(BUDGET_UPLOAD_ERROR_ADJUSTED_VALUE_ERROR,"Invalid adjusted value");
        ErrDescription.put(BUDGET_RECLASS_UPLOAD_ERROR_POOL_SEGMENT_ERROR,"The Pool segment doesn't match");
        ErrDescription.put(BUDGET_RECLASS_UPLOAD_ERROR_ONLY_CHOOSE_POOLS_ERROR,"You can only choose Pools with the same P currency in one flow");
        ErrDescription.put(BUDGET_RECLASS_UPLOAD_ERROR_RECLASS_VALUE_ERROR,"Reclass value can`t be more than output pool available budget");

        ErrDescription.put(BUDGET_FILE_UPLOADING_FILE_SIZE_NAME,"The file name should not exceed 150 digits.");

        ErrDescription.put(BUDGET_FILE_UPLOADING_FILE_TYPE,"Not available for .exe file");
        ErrDescription.put(WORD_CODE_REPEAT, "word code or word key is repeat!");
        ErrDescription.put(EXCEL_IMPORT_ERROR, "Export to Excel failed, template column data error!");

        ErrDescription.put(BUDGET_CREATE_POOL_AVAILABLE_CHECK, "Pool  %s available budget cannot be negative");

        /* email 错误码配置 */
        ErrDescription.put(EMAIL_SEND_REPEAT, "This email has already been sent, please do not send it again!");
        ErrDescription.put(EMAIL_SEND_FAIL, "Email Failed to send!");
        ErrDescription.put(EMAIL_NORMAL_PARAMS_ERROR, "normalParams num not match");
        ErrDescription.put(EMAIL_TABLE_PARAMS_ERROR, "tableParams num not match");
        ErrDescription.put(EMAIL_TEMPLATE_STATUS_ERROR, "email template status error");
        ErrDescription.put(EMAIL_TEMPLATE_CHANGE_INACTIVE_ERROR, "This email template is unique and does not allow to change to Inactive status!");
        ErrDescription.put(EMAIL_TEMPLATE_DATA_ERROR, "This email template data error");
        ErrDescription.put(EMAIL_TEMPLATE_PARAMS_NO_MATCH, "This email template can not match the params '%s'!");
        ErrDescription.put(EMAIL_TITLE_PARAMS_NO_MATCH, "This email title can not match the params '%s'!");
        ErrDescription.put(EMAIL_TEMPLATE__NOT_EXSIT, "This email template did not exsit!");
        ErrDescription.put(EMAIL_TABLE_PARAMS_NOT_STRING, "table params is not a String!");
        ErrDescription.put(EMAIL_RECIPIENT_CONFIG_EXSIT, "Email recipient configuration in the same Business Group, Geo, Job Type already exists.");
        ErrDescription.put(EMAIL_RECIPIENT_EMPTY, "Please add email recipient.");


        ErrDescription.put(MODULE_IS_EMPTY, "Module is empty!");
        ErrDescription.put(ITCODES_IS_EMPTY, "ItCodes is empty!");
        ErrDescription.put(MSGINFO_IS_EMPTY, "Msginfo is empty!");
        ErrDescription.put(MSGTOPIC_IS_EMPTY, "Msgtopic is empty!");
        ErrDescription.put(MSGTYPE_IS_EMPTY, "Msgtype is empty!");
        ErrDescription.put(ITCODES_IS_NOT_ONE, "when setting read flag ,ItCodes should be only one item!");
        ErrDescription.put(HAD_READ_FLAG_ERROR, "had read flag should be 0 or 1!");
        ErrDescription.put(ID_IS_EMPTY, "id is empty!");



        ErrDescription.put(PLS_SELECT_BIZ_TABLE, "please select a biz table");
        ErrDescription.put(EXCHANGE_RATE_FROM_NOT_EXSIT, "exchange rate data error,request param(from) not exsit!");
        ErrDescription.put(EXCHANGE_RATE_TO_NOT_EXSIT, "exchange rate data error,request param(to) not exsit!");
        ErrDescription.put(EXCHANGE_RATE_DATE_DIFFERENT, "exchange rate date is different!");
        ErrDescription.put(SALES_ORG_EXSIT, "The same sales org code with different information exists. Please check the existing record first.");
        ErrDescription.put(SALES_OFFICE_EXSIT , "The sales office %s already exist.");
        ErrDescription.put(SALES_OFFICE_NOT_NULL , "At least one sales office should be linked to the sales org");

        /* accrual 错误码配置 */
        ErrDescription.put(ACCRUAL_PRIORITY_CREATE_OR_UPDATE_DATA_REPEAT, "Duplicate entry Level %s is found");
        ErrDescription.put(ACCRUAL_PRIORITY_CREATE_VERSION_REPEAT, "Cannot add, version is not active!");
        ErrDescription.put(ACCRUAL_PRIORITY_UPDATE_VERSION_REPEAT, "Cannot update, version is not active!");
        ErrDescription.put(ACCRUAL_PRIORITY_DIMENSION_CHECK, "Please check factor dimension %s");

        ErrDescription.put(ACCRUAL_CREATE_ATTRIBUTE, "Factor Attribute Array is null");
        ErrDescription.put(ACCRUAL_CREATE_VERSION_NAME_IS_USD, "Version Name has been used");
        ErrDescription.put(ACCRUAL_CREATE_NO_AVAILABLE_VERSION, "No Available Version, Please create new version first");
        ErrDescription.put(ACCRUAL_CREATE_VERSION_NAME_REPEAT, "Duplicate Version name is not allowed");
        ErrDescription.put(ACCRUAL_CHECK_EFFECTIVE_END_DATE, "Validity overlap is not allowed");
        ErrDescription.put(ACCRUAL_EXPORT_NO_DATA,"No record report");
        ErrDescription.put(MAINTENENCE_ADD_NOT_ALL_FIELD_REPEAT_CURRENCY,"GTN factor could not be maintained with multiple currencies");
        ErrDescription.put(ACCRUAL_MAINTENENCE_DELETE_CHECK_ALL,"You have selected %s GTN factor(s).#Please confirm to delete %s factor(s).");

        ErrDescription.put(ACCRUAL_MAINTENENCE_DELETE_CHECK_SOME,"You have selected %s GTN factor(s).# %s GTN factor(s)have an Effective Date in previous quarters or have already been matched with a billing order for GTN accrual calculation.They are:%s # Please confirm to delete %s factor(s).");

        ErrDescription.put(ACCRUAL_MAINTENENCE_DELETE_CHECK_NOT_ALL,"You have selected %s GTN factor(s). # %s GTN factor(s)have an Effective Date in previous quarters or have already been matched with a billing order for GTN accrual # calculation.They are:%s");
        ErrDescription.put(ACCRUAL_DELETE_FACTOR_CHECK_NO_ID,"No GTN factor is selected for deletion");
        ErrDescription.put(ACCRUAL_CREATE_VERSION_CHECK_START_DATE,"Validity overlap is not allowed");

        ErrDescription.put(ACCRUAL_COMPUTE_FACTOR_CONFIG_ERROR,"Configuration error in GTN factor table. Please check the factor data of factor id %s");
        ErrDescription.put(ACCRUAL_COMPUTE_FACTOR_MATCH_EFFECTIVE_EMPTY,"No GTN factor could be matched");
        ErrDescription.put(ACCRUAL_COMPUTE_FACTOR_MATCH_DIMENSION_EMPTY,"No GTN factor could be matched");
        ErrDescription.put(ACCRUAL_COMPUTE_FACTOR_CALCULATE_RESULT_EMPTY,"factor data calculation result is empty");
        ErrDescription.put(ACCRUAL_COMPUTE_FACTOR_ACTIVE_VERSION_EMPTY,"factor data activated version is empty. Please check the version data of business group %s and geo code %s");
        ErrDescription.put(ACCRUAL_COMPUTE_TASK_CHECK,"calculation task data verification error, %s");
        ErrDescription.put(ACCRUAL_COMPUTE_TASK_EXECUTION,"calculation task execution error, %s");
        ErrDescription.put(ACCRUAL_COMPUTE_TASK_INTERRUPT,"calculation task execution interruption, %s");

        ErrDescription.put(ACCRUAL_COMPUTE_TOTAL_FACTOR_VALUE_NEGATIVE,"Negative GTN factor is matched for %s in %s");
        ErrDescription.put(ACCRUAL_COMPUTE_POOL_MATCH_DIMENSION_EMPTY,"No GTN pool could be matched for %s");
        ErrDescription.put(ACCRUAL_COMPUTE_POOL_MATCH_RESULT_NOT_UNIQUE,"Multiple GTN pool could be matched");
        ErrDescription.put(ACCRUAL_COMPUTE_POOL_CALCULATE_RESULT_EMPTY,"pool data calculation result is empty");
        ErrDescription.put(ACCRUAL_FACTOR_RULE_DATA_CHECK,"Please check factor rule data");
        ErrDescription.put(ACCRUAL_LATE_TO_NORMAL_CHECK,"Please check late billing data %s");
        ErrDescription.put(ACCRUAL_LATE_TO_NORMAL_DATE_CHECK,"Please check version date %s");
        ErrDescription.put(ACCRUAL_LATE_TO_NORMAL_RESET_CHECK,"Please reset factor id %s");
        ErrDescription.put(ACCRUAL_FACTOR_ERROR_TO_RECALCULATE_CHECK,"Please check factor error billing data %s");
        ErrDescription.put(ACCRUAL_POOL_ERROR_TO_RECALCULATE_CHECK,"Please check pool error billing data %s");
        ErrDescription.put(ACCRUAL_COMPUTE_FACTOR_CALCULATE_ERROR,"factor data calculation error %s");

        ErrDescription.put(ACCRUAL_UPLOAD_FIELD_CHECK,"Mandatory field %s is not input with value");
        ErrDescription.put(ACCRUAL_UPLOAD_EFFECTIVE_DATE_CHECK,"Effective Date is not input in the correct format");
        ErrDescription.put(ACCRUAL_UPLOAD_EFFECTIVE_DATE_QUARTER_CHECK,"Effective Date could not be in the previous quarter");
        ErrDescription.put(ACCRUAL_UPLOAD_CURRENCY_CHECK,"Incorrect Currency");
        ErrDescription.put(ACCRUAL_UPLOAD_CURRENCY_FIELD_CHECK,"Currency is not input with value");
        ErrDescription.put(ACCRUAL_UPLOAD_FACTOR_VALUE_CHECK,"Factor Value is not input in the correct format");
        ErrDescription.put(ACCRUAL_UPLOAD_BG_CHECK,"Incorrect Business Group");
        ErrDescription.put(ACCRUAL_UPLOAD_GEO_CHECK,"Incorrect Geo");
        ErrDescription.put(ACCRUAL_UPLOAD_DICT_CHECK," does not exist");
        ErrDescription.put(ACCRUAL_UPLOAD_FILE_NAME,"The file name should not exceed 150 characters");
        ErrDescription.put(ACCRUAL_UPLOAD_FILE_TYPE,"Incorrect file format");
        ErrDescription.put(ACCRUAL_UPLOAD_FILE_SIZE,"The file size should not exceed 100MB");
        ErrDescription.put(ACCRUAL_UPLOAD_CHECK_METHOD,"Method does not exist.");
        ErrDescription.put(ACCRUAL_UPLOAD_DATA_ZERO,"Blank file template");
        ErrDescription.put(ACCRUAL_UPLOAD_CHECK_TABLE_HEADER,"Wrong file template");
        ErrDescription.put(ACCRUAL_UPLOAD_CHECK_EXCEL_IDENTICAL,"Duplicate entry is found in row %s");
        ErrDescription.put(ACCRUAL_UPLOAD_CHECK_EXCEL_UPDATE,"Duplicate entry is found in row %s");
        ErrDescription.put(ACCRUAL_UPLOAD_CHECK_EXCEL_DIFFERENT_CURRENCY,"GTN factor could not be maintained in multiple currencies (in row %s)");
        ErrDescription.put(ACCRUAL_UPLOAD_CHECK_EXCEL_TOTAL_ERROR,"excel data total(%s) not equals successCount(%s) + errorCount(%s), code error");
        /* payment 错误码配置 */
        ErrDescription.put(PAYMENT_SUB_CASH_REQUIRED, "Invoice/Debit Note attachment is mandatory for cash/cash correction payment;");
        ErrDescription.put(PAYMENT_SUB_INVOICE_CLAIM_STATUS, "Invoice Claim is not Approved.");
        ErrDescription.put(PAYMENT_SUB_BLOCK, "%s has been blocked.");
        ErrDescription.put(PAYMENT_SUB_POOL_SALES_ORG_REQUIRED, "The pool must have Sales Org.");
        ErrDescription.put(PAYMENT_SUB_POOL_SALES_OFFICE_REQUIRED, "The pool must have Sales Office.");
        ErrDescription.put(PAYMENT_SUB_PAYMENT_SUB_MODE_POSITIVE, "The Total Payment Amount Must be Positive.");
        ErrDescription.put(PAYMENT_SUB_PAYMENT_SUB_MODE_NEGATIVE, "The Total Payment Amount Must be Negative.");
        ErrDescription.put(PAYMENT_SUB_LAST_PAYMENT, "The payment cannot be submitted due to final payment for the program already exists.");
        ErrDescription.put(PAYMENT_SUB_ITEM_REQUIRED, "A PaymentItem must be included.");
        ErrDescription.put(PAYMENT_SUB_TOTAL_AMOUNT_EQUAL, "The sum of the Payment Header and PaymentItem must be equal.");
        ErrDescription.put(PAYMENT_SUB_STATUS_APPROVING, "Approving cannot be modified.");
        ErrDescription.put(PAYMENT_SUB_POOL_RETURN_DATA_NULL, "No corresponding data is found,pool code field: %s.");
        ErrDescription.put(PAYMENT_SAVE_POOL_AMOUNT_OVERSPENDING, "Payment can not be submitted. There is overspending in pool available commitment, please save and make pool balance adjustment.");
        ErrDescription.put(PAYMENT_SAVE_STATUS_APPROVING, "You cannot save or commit because the status is already approving.");
        ErrDescription.put(PAYMENT_WITHDRAW_STATUS, "Payment code: %s,The rollback can be performed only when the state is approving.");
        ErrDescription.put(PAYMENT_CALCULATE_AMOUNT_PAYMENT_AMOUNT_T, "0 is not allowed.");
        ErrDescription.put(PAYMENT_EXPORT_NO_DATA, "No record found");
        ErrDescription.put(PAYMENT_FILE_UPLOADING_FILE_SIZE, "The total file size should not exceed 100M.");
        ErrDescription.put(PAYMENT_FILE_UPLOADING_FILE_SIZE_INVOICE, "The file size should not exceed 10M.");
        ErrDescription.put(PAYMENT_FILE_UPLOADING_FILE_SIZE_NAME, "The file name should not exceed 150 digits.");
        ErrDescription.put(PAYMENT_FILE_UPLOADING_FILE_SIZE_TYPE, "Incorrect file format.");
        ErrDescription.put(PAYMENT_FILE_TEMPLATE, "Wrong file template, %s field missing.");
        ErrDescription.put(PAYMENT_FILE_TEMPLATE_DATA, "Blank mass upload template detected.");
        ErrDescription.put(PAYMENT_BLOCK_SAVE_REQUIRED, "Please verify data accuracy.");
        ErrDescription.put(PAYMENT_BLOCK_SAVE_ALREADY_EXIST, "The block is already exist.");
        ErrDescription.put(PAYMENT_BLOCK_SAVE_MUTUAL, "Sold to No. does not match with Vendor Code.");
        ErrDescription.put(PAYMENT_OFFSET_SUBMIT_VALUE, "Offset Amount (T) must be less than or equal to Recovery Payment Amount (T).");
        ErrDescription.put(PAYMENT_OFFSET_SUBMIT_VALUE_NO_0, "Offset Value cannot be 0.");
        ErrDescription.put(PAYMENT_OFFSET_SUBMIT_PROFIT_CENTER_MATCH, "Profit Center is not match.");
        ErrDescription.put(PAYMENT_OFFLINE_REPEAT, "The same configuration data for the dimension already exists");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_NULL, "GEO,Business Group,Region/Market can not be empty");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_SECOND_FIRST_AMOUNT_NULL, "First Clip Amount ($) and Second Clip Amount ($) can not  be null at the same time");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_FIRST_AMOUNT_GT_0, "First Clip Amount must ≥ 0");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_FIRST_NULL, "First Re-determined Payment Mode can not be empty");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_SECOND_AMOUNT_GT_0, "Second Clip Amount must ≥ 0");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_AMOUNT_SECOND_GT_FIRST, "Second Clip Amount must > First Clip Amount");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_MODE_SECOND_NULL, "Second Re-determined Payment Mode can not be empty");
        ErrDescription.put(PAYMENT_MODE_TRANSFORM_DUPLICATION, "Configure data duplication");
        ErrDescription.put(PAYMENT_MODE_EXIST, "%s does not exist");

        /* claim 错误码配置 */
        ErrDescription.put(CLAIM_EXPORT_NO_DATA, "No record report");
        ErrDescription.put(CLAIM_UPDATE_PAYMENT_FAIL, "claim update payment fail");
        ErrDescription.put(CLAIM_SEARCH_PAYMENT_FAIL, "claim search payment fail");
        ErrDescription.put(CLAIM_DOWNlOAD_EXCEED, "claim download number exceeds %s !");
        ErrDescription.put(CLAIM_PLEASE_UPLOAD_INVOICE, "Please upload Invoice");
        ErrDescription.put(CLAIM_NUMBER_ERROR, "claim number data error!");
        ErrDescription.put(CLAIM_INVOICE_EMPTY, "claim invoice is empty!");
        ErrDescription.put(PENDING_APPROVER_NULL, "pending approver is null!");
        ErrDescription.put(CLAIM_APPROVE_EXPORT_NO_DATA, "No record found");
        ErrDescription.put(REJECT_NEED_REMARK, "reject need remark!");
        ErrDescription.put(REJECT_NEED_REASON, "reject need reason!");
        ErrDescription.put(REVIEW_NEED_REASON, "review need reason!");
        ErrDescription.put(ONLY_UPDATE_SOME_STATUS, "You are only allowed to update invoice with status Not Ready for Claim, Waiting for Claim and Rejected.");
        ErrDescription.put(INCENTIVE_ID_EMPTY, "incentive id is empty");
        ErrDescription.put(CLAIM_WITHDRAW_STATUS, "Claim number: %s,The rollback can be performed only when the state is approving.");
        ErrDescription.put(WORKFLOW_ERROR, "The approval process may not be configured or problematic, please contact your administrator");
        ErrDescription.put(PAYMENT_STATUS_ERROR, "Payment status must be Draft / Rejected / Closed / Cancelled");

        ErrDescription.put(SIC_ERROR_NOT_RECEIVED, "Have not received invoiceFileUrl from SIC,pls try again !");
        ErrDescription.put(INVOICE_UPLOAD_FAILED, "Invoice upload failed- pls try again or contact helpdesk!");
        ErrDescription.put(FILE_NO_FOUND, "file no found");
        ErrDescription.put(SIC_INCREASE_API_ABNORMAL, "SIC Increase API abnormal!");
        ErrDescription.put(UPLOAD_TAX_NOT_ITALY, "Tax Invoice Mass-upload is allowed only when Lenovo Country and Partner Country are Italy.");
        ErrDescription.put(FILE_NAME_ERROR, "Please upload tax invoice file with Invoice Number as the file name.");
        ErrDescription.put(REVIEW_MAPPING_ERROR, "review mapping size should be one");
        ErrDescription.put(REVIEW_MAPPING_EXIST, "The same configuration item already exists");
        ErrDescription.put(REVIEW_MAPPING_PARAMS_ERROR, "Please select at least one condition");

        ErrDescription.put(COMMON_PLEASE_ENTER, "Please Enter ");
        ErrDescription.put(OUT_OF_RANGE_FQ_ERROR, " must be in the selected Fiscal Quarter.");
        ErrDescription.put(FORMAT_ERROR, "is not input in the correct format");
        ErrDescription.put(PAYMENT_CODE_NOT_EXIST, "payment code not_exist");
        ErrDescription.put(CLAIM_DATA_REPEAT_CHECK,"The same configuration item already exists");
        ErrDescription.put(BLANK_FILE, "Blank mass upload template detected");
        ErrDescription.put(FLOWABLE_DOA_ADD_DATA_CHECK,"Validity of dimension overlap is not allowed.");


        ErrDescription.put(BAL_PLEASE_SELECT_POOL, "Please select Pool");
        ErrDescription.put(BAL_DETAIL_NOT_COMPLETE, "Please complete the adjustment detail. ");
        ErrDescription.put(POOL_CODE_NOT_EXIST, "Pool Code does not exist.");
        ErrDescription.put(POOL_CODE_NO_MATCH_POOL, "Pool code does not match adjustment info.");
        ErrDescription.put(POOL_CODE_IS_EMPTY, "please input pool code.");
        ErrDescription.put(OUTPUT_INPUT_AT_LEAST_ONE, "Both Output and Input should have at least 1 item");
        ErrDescription.put(COMPLETE_CURRENT_FLOW, "Please complete current flow before adding new flow");
        ErrDescription.put(ALL_SHOULE_BE_THE_SAME_FISCALYQ, "All pools in Output and Input should be in the same Fiscal Year and Quarter");
        ErrDescription.put(SHOULE_BE_THE_SAME_FISCALYQ, "Pools in Output should not be in the same Fiscal Year and Quarter or after the pools in Input");
        ErrDescription.put(SHOULE_BE_THE_SAME_BEFORE_FISCALYQ, "Pools in Output should not be in the same Fiscal Year and Quarter or before the pools in Input");
        ErrDescription.put(ONE_POOL_IN_ONE_FLOW_NOT_ALLOWED, "One Pool in one flow for both output and input is not allowed");
        ErrDescription.put(ONE_POOL_PROFIT_CENTER_IN_ONE_FLOW_NOT_ALLOWED, "One Pool in one flow with the same profit center for both output and input is not allowed");
        ErrDescription.put(FROM_TO_POOL_NOT_SAME, "From Pool and To Pool can be the same");
        ErrDescription.put(FROM_TO_POOL_NOT_SAME_FY_FQ, "From and To Pool should be in the same Fiscal Year and Quarter");
        ErrDescription.put(FROM_SHOULD_AFTER_TO_FY_Q, "From Pool should not be in the same Fiscal Year and Quarter or after the To Pool");
        ErrDescription.put(POOL_ITEM_ZERO, "The pool balance must be greater than zero");
        ErrDescription.put(ONE_FLOW_NOT_ALLOWED, "Multiple different Pools in both Output and Input for one flow is not allowed");
        ErrDescription.put(POOL_CHECK_CANNELLED,"Pool can't be canceled as there is critical value, budget, accrual by order, accrual adjustment, program, payment information linked with the pool.");
        ErrDescription.put(OUTPUT_INPUT_AMOUT_NOT_EQUAL, "Total output amount must be equal to the total input amount");
        ErrDescription.put(ITEM_TYPE_NOT_CONSISTENT, "Item Type of all items must be consistent.");
        ErrDescription.put(BA_WITHDRAW_STATUS, "Adjustment code: %s,The rollback can be performed only when the state is approving.");
        ErrDescription.put(CLOSE_ERROR_1, "Pool can't be closed because Pool ending balance(P) by Profit center by Company code level is not 0.");
        ErrDescription.put(CLOSE_ERROR_2, "Pool can't be closed because Pool ending balance is not 0, and there is unclosed program under the pools.");
        ErrDescription.put(CLOSE_ERROR_3, "There is unclosed program under the pools.");



        ErrDescription.put(FLOWABLE_DOA_ADD_Approval_GROUP_CHECK, "There is a similar Approval Group already maintained in the system.");
        ErrDescription.put(CRITICAL_VALUE_NAME_ALREADY_EXISTS, "Critical Value Name already exists");
        ErrDescription.put(CRITICAL_VALUE_ADD_POOL, "Please select Pool");
        ErrDescription.put(CRITICAL_NEW_VALUE_NOT_NULL, "Please complete critical value detail");
        ErrDescription.put(CRITICAL_CREATE_POOL_DATA_CHECK_SUB, "[%s] is used by approving Critical Value request [%s] ");
        ErrDescription.put(CRITICAL_CREATE_ATTACHMENT_NULL,"Attachment is mandatory to upload");
        ErrDescription.put(CRITICAL_CREATE_POOL_IDENTICAL,"One Pool can only be selected once");
        ErrDescription.put(CRITICAL_DATA_NOT_EXIST,"CriticalValue not found");
        ErrDescription.put(CRITICAL_DATA_VERSION_CHECK, "Please check CriticalValue data version value");
        ErrDescription.put(CRITICAL_VALUE_APPROVE_FQ_FY_CHECK,"Please reject the critical value request, as critical value of previous quarter's pool shall be 0 value");
        ErrDescription.put(FLOWABLE_DOA_MATCH_ERROR, "There is no approver maintained. Please go to ”Delegation of Authority“ to maintain approvers first.");
        ErrDescription.put(CRITICAL_VALUE_FILE_UPLOADING_FILE_SIZE_NAME,"The file name should not exceed 150 digits.");
        ErrDescription.put(CRITICAL_VALUE_FILE_UPLOADING_FILE_SIZE,"The total file size should not exceed 100M.");
        ErrDescription.put(CRITICAL_VALUE_FILE_UPLOAD_FILE_TYPE,"Incorrect file format");
        ErrDescription.put(CRITICAL_VALUE_CHECK_POOL_CODE_ACTIVE,"Pool Code does not exist.");
        ErrDescription.put(CRITICAL_VALUE_CHECK_POOL_INFORMATION,"Pool code does not match adjustment info.");
        ErrDescription.put(CRITICAL_VALUE_CHECK_POOL_USING_ERROR,"There is a Critical value Request [%s]  for the pool in approving status.");
        ErrDescription.put(CRITICAL_VALUE_SUBMIT_CHECK,"Only current quarter's critical value request can be submitted");

        ErrDescription.put(DUPLICATE_INVITE,"重复发送邀请");
        ErrDescription.put(ALREADY_INVITED,"该供应商已被邀请过");
        ErrDescription.put(INVALID_INVITE_CODE,"不可用的邀请码");
        ErrDescription.put(DUPLICATE_REGISTER,"重复注册");
        ErrDescription.put(SUPPLIER_NOT_FOUND,"未找到供应商");
        ErrDescription.put(CONTACT_SUPPLIER_ADMIN,"请联系你的管理员");
        ErrDescription.put(WAITING_FOR_ACTIVATION,"请等待激活");
        ErrDescription.put(SUPPLIER_HAS_BEEN_SUSPEND,"该供应商已被冻结");
        ErrDescription.put(SUPPLIER_STATUS_IS_WRONG,"供应商状态异常");
        ErrDescription.put(REDIRECT_TO_LOGIN,"跳转回登录页");
        ErrDescription.put(USER_ACCOUNT_NOT_FOUND,"未找到账户");
        ErrDescription.put(SUPPLIER_BANK_SETTING_IS_NULL,"供应商的银行绑定关系为空");
        ErrDescription.put(ERP_IDS_IS_NULL,"供应商ERP ID为空");
        ErrDescription.put(DIFFERENT_UNIQUE_IDS_IS_NULL,"Different Unique ID为空");
        ErrDescription.put(COMPANY_NAME_NOT_MATCH,"公司名称不匹配");
        ErrDescription.put(STATUS_IS_WRONG,"状态参数异常");
        ErrDescription.put(SUPPLIER_UPDATE_FAILED,"供应商状态更新失败");
        ErrDescription.put(SUPPLIER_REMOTE_ACTIVE_FAILED,"供应商qdp激活失败");
        ErrDescription.put(SUPPLIER_REMOTE_SUSPEND_FAILED,"供应商qdp禁用失败");
        ErrDescription.put(SUPPLIER_REMOTE_ON_HOLD_FAILED,"供应商qdp onHold失败");
        ErrDescription.put(SUPPLIER_REMOTE_CANCEL_HOLD_FAILED,"供应商qdp取消onHold失败");
        ErrDescription.put(SUPPLIER_SUBMIT_INVOICES_ID_EMPTY,"提交的发票id列表为空");
        ErrDescription.put(SUPPLIER_CODE_RELATION_EXIST,"供应商关系已存在");
        ErrDescription.put(VENDOR_CODE_IS_NULL,"供应商代码为空");
        ErrDescription.put(SUPPLIER_ID_NOT_FOUND,"供应商ID未找到");
        ErrDescription.put(REGISTER_LENOVO_ID_FAILED,"供应商注册LenovoID失败");
        ErrDescription.put(AVAILABLE_CURRENCY_IS_NULL,"可用币种为空");
        ErrDescription.put(SUPPLIER_BANK_SETTING_DUPLICATE,"供应商银行配置重复");

        ErrDescription.put(INVOICE_SELECT_TYPE_WRONG,"查询方式不正确");

        ErrDescription.put(BANK_NAME_EXIST,"银行名称已存在");
        ErrDescription.put(BANK_NOT_FOUND,"银行未找到");
        ErrDescription.put(BANK_VENDOR_IS_NULL,"银行VendorCode为空");
        ErrDescription.put(BANK_CURRENCY_IS_NULL,"银行币种为空");
        ErrDescription.put(BANK_HAS_ENTITY_REL,"银行与公司存在关联关系，无法禁用");
        ErrDescription.put(BANK_HAS_SUPPLIER_REL,"银行与供应商存在关联关系，无法禁用");

        ErrDescription.put(ENTITY_NAME_EXIST,"实体名称已存在");
        ErrDescription.put(ENTITY_NOT_FOUND,"实体未找到");
        ErrDescription.put(ENTITY_HAS_SUPPLIER_REL,"实体与供应商存在关联关系，无法禁用");
        ErrDescription.put(ENTITY_CURRENCY_IS_NULL,"实体币种为空");

        ErrDescription.put(ENTITY_BANK_SETTING_NOT_FOUND,"实体银行配置未找到");

        ErrDescription.put(ENTITY_COMPANY_CODE_IS_NULL,"实体与公司代码关联关系为空");
        ErrDescription.put(ENTITY_COMPANY_CODE_RELATION_EXIST,"实体与公司代码关联关系已存在");

        ErrDescription.put(INVOICE_HYGIENE_CHECK_FAILED,"发票HygieneCheck失败");
        ErrDescription.put(INVOICE_LIST_IS_NULL,"invoice list is empty");
        ErrDescription.put(INVOICE_IS_NULL,"invoice object is null");
        ErrDescription.put(INVOICE_CURRENCY_CHECK_FAILED,"currency check failed,invoice number:");
        ErrDescription.put(INVOICE_FINANCING_TENOR_CHECK_FAILED,"financing tenor check failed,invoice number:");
        ErrDescription.put(INVOICE_MATURITY_DATE_CHECK_FAILED,"maturity date check failed,invoice number:");
        ErrDescription.put(INVOICE_LIMIT_CHECK_FAILED,"limit check failed");
        ErrDescription.put(INVOICE_MATURITY_DATE_IS_NULL,"maturity date check failed,maturity date is null,invoice number:");

        ErrDescription.put(ILLEGAL_SUBMIT_INVOICE_ID,"非法的发票id");
        ErrDescription.put(OTHER_USER_OPERATING,"当前有其他的用户正在进行融资操作，请稍后再试");
        ErrDescription.put(ILLEGAL_PARAM_INVOICE_TYPE,"非法的发票类型参数");
        ErrDescription.put(DECREASE_LIMIT_FAILED,"扣减银行可用额度失败");
        ErrDescription.put(NO_MATCHED_INVOICE_BATCH,"没有匹配的发票批次");

        ErrDescription.put(SOFR_RATE_NOT_FOUND,"未找到SOFR rate值");
        ErrDescription.put(LESS_THAN_MIN_AMOUNT,"融资金额不能小于最小金额");
        ErrDescription.put(SUBMIT_FAILED,"融资提交失败");
        ErrDescription.put(ILLEGAL_MATURITY_DATE,"非法的Confirmed Maturity Date值");
        ErrDescription.put(DUPLICATE_BATCH_NUMBER,"生成批次号重复");

        ErrDescription.put(INVOICE_INVOICE_TYPE_ERROR,"Invoice Type Not Debit Memo Or Credit Memo");
        ErrDescription.put(INVOICE_REFERENCE_IS_DUPLICATION_ERROR,"Invoice Reference is Duplication");
        ErrDescription.put(INVOICE_TENOR_ERROR,"Invoice tenor >= Supplier tenor date");
        ErrDescription.put(ISSUE_DATEFUTURE_DATE_ERROR,"Issue Date > Future Date");
        ErrDescription.put(INVOICEPAYDATE_OR_INVOICEDUEDATE_ERROR,"InvoicePayDate Or InvoiceDueDate is Empty");
        ErrDescription.put(INVOICE_REFERENCE_IS_EMPTY_ERROR,"Invoice Reference is Empty");
        ErrDescription.put(REFERENCE_START_WITH_DNF_ERROR,"Reference Start With DNF");
        ErrDescription.put(REPORT_ROW_OUT_ERROR,"Row out");
    }
}
