package com.wc.single.sky.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.annotation.ImportValid;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.common.redis.util.RedisUtils;
import com.wc.single.sky.common.utils.MapDataUtil;
import com.wc.single.sky.system.domain.SysDictData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author daihuaicai
 * @param
 */
@Component
public class ValidImportFields {
    public static final String BUSINESS_GROUP = "Business Group";
    public static final String GEO = "Geo";

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    MasterDataValidate masterDataValidate;


//    public ImportResult<BaseImportDTO> validField(BaseImportDTO importEntity, Map<String, Object> data, List<JSONObject> fieldConfigs, Map<String, List<SysDictData>> dicMap, CustomerValidate customerValidate) throws QdpException, IllegalAccessException {
//        if (null != customerValidate) {
//            customerValidate.beforeCustomValidate(importEntity);
//            data = MapDataUtil.beanToMap(importEntity);
//        }
//        Field[] fields = importEntity.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            Class<?> type = field.getType();
//            ImportValid validPool = field.getAnnotation(ImportValid.class);
//            if (null != validPool) {
//                ReflectionUtils.makeAccessible(field);
//                if (String.class == type) {
//                    //当某个其他字段的值 是某个值的时候触发条件
//                    validField(field, importEntity, validPool, fieldConfigs, dicMap, data);
//                }else if (Date.class == type){
//                    //日期格式验证
//                    String fieldName = field.getName();
//                    String excelName = validPool.excelName();
//
//                    Object objVal = MapDataUtil.getObjVal(fieldName, data);
//                    //日期格式验证
//                    if (null == objVal) {
//                        ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.FORMAT_ERROR)));
//                    }
//                }
//            }
//        }
//        //自定义后置验证逻辑
//        if (null != customerValidate) {
//            customerValidate.afterCustomValidate(importEntity);
//        }
//        return StrUtil.isNotBlank(importEntity.getErrorMsg()) ? ImportResult.importFail("", importEntity) : ImportResult.importSuccess("", importEntity);
//    }

    private boolean fieldValInArray(String val, String[] array) {
        if (StrUtil.isBlank(val)) {
            return false;
        }
        for (String a : array) {
            if (val.equals(a)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 验证字段
     * @param field
     * @param importEntity
     * @param validPool
     * @param fieldConfigs
     * @param dicMap
     * @param data
     */
    private void validField(Field field, BaseImportDTO importEntity, ImportValid validPool, List<JSONObject> fieldConfigs, Map<String,List<SysDictData>> dicMap, Map<String,Object> data) {
        if (null == field || null == validPool || null == importEntity) {
            return;
        }

        //当某个字段的值是某个值的时候触发条件
        String isInField = validPool.isInField();
        String[] isInFieldValues = validPool.isInValues();
        //必填效验
        Boolean required = validPool.required();
        //日期格式验证
        String dateFormat = validPool.dateFormat();
        String fieldName = field.getName();
        boolean validFieldConf = validPool.fieldConfig();
        String excelName = validPool.excelName();

        String val = MapDataUtil.getStrVal(fieldName, data);
        if (StrUtil.isNotBlank(val)) {
            val = val.trim();
        }
        //字段配置效验
        boolean configResult = fieldConfig(validFieldConf, val, fieldName, excelName, fieldConfigs, importEntity);
        //根据条件判断是否必填
        if (StrUtil.isNotBlank(isInField) && isInFieldValues.length > 0) {
            String thisFieldVal = MapDataUtil.getStrVal(isInField, data);
            if (!fieldValInArray(thisFieldVal, isInFieldValues)) {
                required = false;
            }
        }
        //必填效验
        required(excelName, val, required, importEntity);
        //日期格式验证
        validDateFormat(val, dateFormat, importEntity, excelName);
        //是否是数字
        Boolean isNumber = validPool.isNumber();
        //是否是正整数
        Boolean isInteger = validPool.isPositiveNumber();
        //是否是正数
        Boolean isPositive = validPool.isPositive();
        //长度效验
        int length = validPool.length();
        //字典表验证
        String dicType = validPool.dicType();
        //masterData验证
        String masterData = validPool.masterData();
        //级联关系的字段
        String[] caRe = validPool.cascadeRelations();
        String dbName = validPool.dbName();
        //是否是正数效验
        isPositive(isPositive, excelName, val, importEntity);
        //数字效验
        isNumber(isNumber,excelName, val, importEntity);
        //整数效验
        isInteger(isInteger, excelName, val, importEntity);
        //长度效验
        length(length, fieldName, val, importEntity);
        //字典表验证
        validDic(dicType, val, dicMap, importEntity, excelName);
        //配置表验证完失败就不继续效验
        if (!configResult) {
            return;
        }
        if (StrUtil.isBlank(dbName)) {
            dbName = fieldName;
        }
        boolean needSearch = validPool.needToSearch();
        //masterData效验
        validMasterData(masterData,val, dbName, excelName, caRe, data, importEntity,needSearch);
    }

    /**
     * * 是否是正数
     * @param isPositive
     * @param fieldName
     * @param val
     * @param importDTO
     */
    private void isPositive(boolean isPositive, String fieldName, String val, BaseImportDTO importDTO) {
        if (importDTO.hasError(fieldName)) {
            return;
        }
        if (isPositive && StrUtil.isNotBlank(val) && (!NumberUtil.isNumber(val) || val.contains("-"))) {
            ValidField.setErrorMsg(importDTO, ValidField.buildReturnStr(fieldName, " is not input in the correct format"));
        }
    }

    private void validDateFormat(String val, String dateFormat, BaseImportDTO importEntity,String excelName) {
        if (StrUtil.isBlank(val) || StrUtil.isBlank(dateFormat) || null == importEntity) {
            return;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            Date date = format.parse(val);
            String formatStr = format.format(date);
            if (!val.equals(formatStr)) {
                ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.FORMAT_ERROR)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.FORMAT_ERROR)));
        }
    }
    /**
     * 必填效验
     * @param fieldName
     * @param val
     * @param required
     * @param importEntity
     */
    private void required(String fieldName, String val, boolean required, BaseImportDTO importEntity) {
        if (importEntity.hasError(fieldName)) {
            return;
        }
        if (required) {
            String str = ValidField.requiredFields(fieldName, val);
            if (StrUtil.isNotBlank(str)) {
                ValidField.setErrorMsg(importEntity, str);
            }
        }
    }

    /**
     * 验证masterData
     *
     * @param masterData
     * @param val
     * @param dbName
     * @param excelName
     * @param caRe
     * @param data         参数个数修改涉及范围较大 暂时不修改
     * @param importEntity
     */
    @SuppressWarnings("all")
    private void validMasterData(String masterData,String val, String dbName, String excelName, String[] caRe, Map<String,Object> data, BaseImportDTO importEntity,boolean needSearch) {
        if (importEntity.hasError(excelName)) {
            return;
        }
        if (StrUtil.isNotBlank(masterData) && StrUtil.isNotBlank(val)) {
            masterDataValidate.newMasterDataValid(val, masterData, dbName, excelName, caRe, data,importEntity,needSearch);
        }
    }

    private void isNumber(boolean isNumber, String excelName, String val, BaseImportDTO baseImportDTO) {
        if (null != baseImportDTO && baseImportDTO.hasError(excelName)) {
            return;
        }
        if (isNumber && StrUtil.isNotBlank(val)) {
            String result = ValidField.isNumber(excelName, val);
            if (StrUtil.isNotBlank(result)) {
                ValidField.setErrorMsg(baseImportDTO, result);
            }
        }
    }
    private void isInteger(boolean isInteger, String excelName, String val, BaseImportDTO baseImportDTO) {
        if (null != baseImportDTO && baseImportDTO.hasError(excelName)) {
            return;
        }
        if (isInteger && StrUtil.isNotBlank(val) ) {
            String result = ValidField.isInteger(excelName, val);
            if (StrUtil.isNotBlank(result)) {
                ValidField.setErrorMsg(baseImportDTO, result);
            }
        }
    }
    /**
     *
     * @param fieldConf 是否开启效验
     * @param val 值
     * @param fieldName 字段名称
     * @param excelName excel名称
     * @param fieldConfigs 配置列表
     * @param importEntity 数据实体
     * @return 效验成功true 失败false
     */
    private Boolean fieldConfig(boolean fieldConf, String val, String fieldName, String excelName, List<JSONObject> fieldConfigs, BaseImportDTO importEntity) {
        if (fieldConf && CollectionUtils.isNotEmpty(fieldConfigs)) {
            String fieldConfigStr = ValidField.validFieldConfig(val, fieldName, fieldConfigs, excelName);
            if (StrUtil.isNotBlank(fieldConfigStr) && !ValidField.NO_FIELD.equals(fieldConfigStr)) {
                importEntity.appendErrorMsg(fieldConfigStr);
                return Boolean.FALSE;
            }
            //如果字段配置表不存在就设置空值
            if (StrUtil.isNotBlank(val) && ValidField.NO_FIELD.equals(fieldConfigStr) && !"businessGroup".equals(fieldName) && !"geoCode".equals(fieldName) && !"keyAccount".equals(fieldName)) {
                //如果字段配置表不存在就设置空值
                ReflectUtil.setFieldValue(importEntity, fieldName, null);
                importEntity.appendErrorMsg(ValidField.buildReturnStr(excelName, " is not allowed for related Geo & Business Group"));
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 字典表效验
     * @param dicType
     * @param val
     * @param dicMap
     * @param importEntity
     * @param excelName
     */
    private void validDic(String dicType, String val, Map<String, List<SysDictData>> dicMap, BaseImportDTO importEntity, String excelName) {
        if (StrUtil.isNotBlank(dicType) && StrUtil.isNotBlank(val)) {
            boolean isHave = ValidField.findInDic(dicType, val, dicMap);
            if (!isHave) {
                ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, "does not exist"));
            }
        }
    }
    /**
     * 长度效验
     * @param length
     * @param fieldName
     * @param val
     * @param importEntity
     */
    private void length(int length, String fieldName, String val, BaseImportDTO importEntity) {
        if (importEntity.hasError(fieldName)) {
            return;
        }
        if (length > 0 && StrUtil.isNotBlank(val)) {
            String validStr = ValidField.validLength(fieldName, val, length);
            if (StrUtil.isNotBlank(validStr)) {
                ValidField.setErrorMsg(importEntity, validStr);
            }
        }
    }



    public static boolean isContains(String val1, String val2) {
        if (StrUtil.isBlank(val1) || StrUtil.isBlank(val2)) {
            return Boolean.FALSE;
        }
        Set<String> val1Set = addValToSet(val1);
        Set<String> val2Set = addValToSet(val2);
        Set<String> set = new HashSet<>();
        set.addAll(val1Set);
        set.retainAll(val2Set);
        if (CollUtil.isNotEmpty(set)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }




    /**
     * 添加到set集合中
     * @param val
     * @return
     */
    public static  Set<String> addValToSet(String val) {
        Set<String> set = new HashSet<>();
        if (StrUtil.isBlank(val)) {
            return set;
        }
        if (val.contains(",")) {
            String[] array = val.split(",");
            if (null != array && array.length > 0) {
                for (String str : array) {
                    set.add(str.trim());
                }
            }
        } else {
            set.add(val.trim());
        }
        return set;
    }



}
