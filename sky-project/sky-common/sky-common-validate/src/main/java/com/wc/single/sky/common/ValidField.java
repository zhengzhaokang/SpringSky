package com.wc.single.sky.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.constant.ViewFieldJsonKeys;
import com.wc.single.sky.common.core.viewFeild.ParseViewField;
import com.wc.single.sky.common.core.viewFeild.ValidViewField;
import com.wc.single.sky.system.domain.SysDictData;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 专门用于验证字段的类
 * 忽略大小写
 * @author daihuaicai
 */
public class ValidField {

    private ValidField() {
    }

    /**
     * 字段不在配置表的情况
     */
    public static final String NO_FIELD = "This field is not included";


    /**
     * 验证bg
     *
     * @param importVal
     * @return
     */
    public static  String validBGGeo(String importVal, String field, String dicKey, Map<String, List<SysDictData>> dicMap) {
        //必填效验
        String requiredMsg = requiredFields(field, importVal);
        if (StrUtil.isNotBlank(requiredMsg)) {
            return requiredMsg;
        }
        //多选效验
        if (importVal.contains(",")) {
            return buildReturnStr(field, "does not support multiple selection");
        }
        //验证在字典表是否存在
        if (!findInDic(dicKey, importVal, dicMap)) {
            return buildReturnStr(field, "does not exist");
        }
        return "";
    }



    /**
     * 必填校验 多了一层 __config__
     * @param jsonObject
     * @param
     * @param fieldVal
     * @return
     */
    public static String validRequired(JSONObject jsonObject,String fieldVal,String excelName) {

        Object obj = jsonObject.get("__config__");
        if (null != obj) {
            //必填效验
            boolean re = ValidViewField.validViewField(JSON.parseObject(obj.toString()), fieldVal, ViewFieldJsonKeys.REQUIRED);
            if (!re) {
                return buildReturnStr(excelName, "is mandatory");
            }
        }
        return "";
    }

    /**
     * 多选效验
     * @param jsonObject
     * @param
     * @param fieldVal
     * @return
     */
    public static String validMultiple(JSONObject jsonObject,String fieldVal,String excelName) {
        boolean re = ValidViewField.validViewField(jsonObject, fieldVal, ViewFieldJsonKeys.MULTIPLE);
        if (!re) {
            return buildReturnStr(excelName, "does not support multiple selection");
        }
        return "";
    }


    /**
     * 效验字段配置表
     * @param fieldVal
     * @param field
     * @param viewFields
     * @return
     */
    public static String validFieldConfig(String fieldVal, String field, List<JSONObject> viewFields,String excelName) {
        //根据字段名称获取字段配置
        JSONObject jsonObject = ParseViewField.getValidViewField(viewFields, field);
        if (null != jsonObject) {
            //必填效验
            String requiredVal = validRequired(jsonObject,fieldVal,excelName);
            if (StrUtil.isNotBlank(requiredVal)) {
                return requiredVal;
            }
            //多选校验
            String muVal = validMultiple(jsonObject,fieldVal,excelName);
            if (StrUtil.isNotBlank(muVal)) {
                return muVal;
            }
            //全部校验通过返回空字符串
            return "";
        }
        return NO_FIELD;
    }
    /**
     * 验证字典表是否存在
     *
     * @param type 字典表类型
     * @param val  导入的值
     * @return true 存在 false 不存在
     */
    public static  boolean findInDic(String type, String val, Map<String, List<SysDictData>> dicMap) {
        List<SysDictData> sysDictDataList = dicMap.get(type);
        if (CollUtil.isEmpty(sysDictDataList)) {
            return Boolean.FALSE;
        }
        Set<String> set = new HashSet<>();
        //处理多选的情况
        if (StrUtil.isNotBlank(val) && val.contains(",")) {
            set.addAll(ValidImportFields.addValToSet(val));
        }else{
            set.add(val);
        }
        if (CollUtil.isNotEmpty(set)) {
                for (String s : set) {
                    if (!findDic(sysDictDataList, s)) {
                        return Boolean.FALSE;
                    }
                }
        }
        return Boolean.TRUE;
    }

    private static boolean findDic(List<SysDictData> sysDictDataList, String val) {
        for (SysDictData sysDictData : sysDictDataList) {
            if (null != sysDictData && StrUtil.isNotBlank(sysDictData.getDictValue()) && sysDictData.getDictValue().equalsIgnoreCase(val)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
    /**
     * 必填效验
     *
     * @param field
     * @param value
     * @return 如果返回空证明验证成功
     */
    public  static String requiredFields(String field, String value) {
        if (StrUtil.isBlank(value) && StrUtil.isNotBlank(field)) {
            return buildReturnStr(field, "is mandatory");
        }
        return "";
    }

    public static String validLength(String field, String val, int length) {
        if (StrUtil.isNotBlank(val) && StrUtil.isNotBlank(field) && val.length() > length) {
            return buildReturnStr(field, "only " + length + " digits are allowed");
        }
        return "";
    }

    /**
     * 验证是否是数字
     * @param field
     * @param val
     * @return
     */
    public static String isNumber(String field, String val) {
        if (StrUtil.isNotBlank(field) && StrUtil.isNotBlank(val) && !NumberUtil.isNumber(val)) {
            return buildReturnStr(field, "Only numbers are allowed for Amount.");
        }
        return "";
    }

    /**
     * 是否是整数
     * @param str 参数
     * @param isPositive 是否验证正整数
     * @return
     */
    public static boolean isInteger(String str,boolean isPositive) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (!isPositive){
            return pattern.matcher(str).matches();
        }
        //继续检查是否是正整数
        if(pattern.matcher(str).matches()){
            return Integer.parseInt(str) >0;
        }
        return pattern.matcher(str).matches();
    }

    public static String isInteger(String excelName,String fieldVal) {
        if (StrUtil.isBlank(fieldVal)) {
            return "";
        }
        if (!isInteger(fieldVal, true)) {
            return buildReturnStr(excelName, "Only whole number is allowed for " + excelName);
        }
        return "";
    }

    /**
     * 构建返回字符串结果
     *
     * @param field 字段
     * @param suff  后缀
     * @return 构建结果
     */
    public static  String buildReturnStr(String field, String suff) {
        StringBuilder builder;
        if (StrUtil.isNotBlank(field) && StrUtil.isNotBlank(suff)) {
            builder = new StringBuilder();
            builder.append("[");
            builder.append(field);
            builder.append("]");
            builder.append(" "+suff);
            return builder.toString();
        }
        return "";
    }

    public static void setErrorMsg(BaseImportDTO importEntity, String msg) {
       synchronized (ValidField.class){
           String errorMsg = importEntity.getErrorMsg();
           if (StrUtil.isNotBlank(errorMsg)) {
               if (!errorMsg.contains(msg)) {
                   importEntity.appendErrorMsg(msg);
               }
           }else{
               importEntity.appendErrorMsg(msg);
           }

       }

    }

}
