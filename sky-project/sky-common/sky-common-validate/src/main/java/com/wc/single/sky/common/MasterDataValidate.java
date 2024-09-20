package com.wc.single.sky.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.constant.MasterDataConstants;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.common.redis.util.RedisUtils;
import com.wc.single.sky.common.utils.MapDataUtil;
import com.wc.single.sky.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MasterDataValidate {

    @Autowired
    RedisUtils redisUtils;
    /**
     * * 实体字段和导入表头的映射关系
     * * 数据库的key 在前  excel的 title在后
     */
    private ConcurrentHashMap<String, String> headMapppingInfo;



    /**
     *   新版本的
     * @param val
     * @param masterData
     * @param dbField 字段名称
     * @param excelName 在excel的名称
     * @param casRel 级联关系
     * @param importData 导入的数据
     * @param importEntity 导入的实体
     *
     */
    @SuppressWarnings("all")
    public  void newMasterDataValid(String val, String masterData, String dbField, String excelName, String[] casRel, Map<String,Object> importData, BaseImportDTO importEntity, boolean needSearch) {
        switch (masterData) {
            case MasterDataConstants
                    .BIZ_BASE_APC:
                isInRedis(val, importEntity, NewRedisMasterDataKey.APC_CODE, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_BP_TYPE:
                isInRedis(val, importEntity, NewRedisMasterDataKey.BP_TYPE, excelName);
                break;
            case MasterDataConstants
                    .BIZ_GTN_CATEGORY_L0:
                isInRedis(val, importEntity, NewRedisMasterDataKey.GTN_CATEGORY_L0, excelName);
                break;
            case MasterDataConstants
                    .BIZ_GTN_CATEGORY_L1:
                isInRedis(val, importEntity, NewRedisMasterDataKey.GTN_CATEGORY_L1, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_BU:
                isInRedis(val, importEntity, NewRedisMasterDataKey.BW_BU, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_CURRENCY:
                isInRedis(val, importEntity, NewRedisMasterDataKey.CURRENCY, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_CUSTOMER:
                if (!needSearch) {
                    isInRedis(val, importEntity, NewRedisMasterDataKey.CUSTOMER, excelName);
                }
                break;
            case MasterDataConstants
                    .BIZ_BASE_END_CUSTOMER:
                isInRedis(val, importEntity, NewRedisMasterDataKey.END_CUSTOMER_ID, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_GTN_TYPE:
                validHierarchicalRelation(NewRedisMasterDataKey.GTN_TYPE, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants.BIZ_BASE_PRO_HIER_BY_LEVEL:
                isInRedis(val, importEntity, NewRedisMasterDataKey.PH_LEVEL, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_SALES_OFFICE:
                validHierarchicalRelation(NewRedisMasterDataKey.SALES_OFFICE, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_SALES_ORG:
                validHierarchicalRelation(NewRedisMasterDataKey.SALES_ORG, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_SEGMENT:
                validHierarchicalRelation(NewRedisMasterDataKey.SEGMENT, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_SEGMENT_LEVE1:
                validHierarchicalRelation(NewRedisMasterDataKey.SEGMENT_LEVEL1, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_SEGMENT_LEVE2:
                validHierarchicalRelation(NewRedisMasterDataKey.SEGMENT_LEVEL2, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_SEGMENT_LEVE3:
                validHierarchicalRelation(NewRedisMasterDataKey.SEGMENT_LEVEL3, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_REGION_MARKET:
                validHierarchicalRelation(NewRedisMasterDataKey.REGION_MARKET_CODE, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants
                    .BIZ_BASE_PROFIT_CENTER:
                isInRedis(val, importEntity, NewRedisMasterDataKey.PROFIT_CENTER, excelName);
                break;
            case MasterDataConstants
                    .BIZ_BASE_VENDOR_BRANK:
                if (!validVendorBank(dbField, importData)) {
                    ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ACCRUAL_UPLOAD_DICT_CHECK)));
                }
                break;
            case MasterDataConstants.VENDOR:
                isInRedis(val, importEntity, NewRedisMasterDataKey.VENDOR, excelName);
                break;
            case MasterDataConstants.BANK:
                isInRedis(val, importEntity, NewRedisMasterDataKey.BANK, excelName);
                break;
            case MasterDataConstants.BIZ_BASE_DC_DIVISION_MAPPING:
                validHierarchicalRelation(NewRedisMasterDataKey.BIZ_BASE_DC_DIVISION_MAPPING, excelName, importEntity, dbField, casRel, importData);
                break;
            case MasterDataConstants.BIZ_BASE_TOS:
                isInRedis(val, importEntity, NewRedisMasterDataKey.TOS, excelName);
                break;
            case MasterDataConstants.BIZ_BASE_MBG_CUSTOMER_DRM_TOMS_TOFI:
                isInRedis(val, importEntity, NewRedisMasterDataKey.KEY_ACCOUNT, excelName);
                break;
            case MasterDataConstants.BIZ_BASE_ORG_OFFICE:
                validHierarchicalRelation(NewRedisMasterDataKey.TERRITORY, excelName, importEntity, dbField, casRel, importData);
            default:
                break;
        }
    }

    private void validHierarchicalRelation(String masterDataKey, String excelName, BaseImportDTO importEntity, String dbField, String[] casRel, Map<String, Object> importData) {
        List<JSONObject> mappings = getByCache(masterDataKey);
        if (null != mappings && !validHierarchicalRelation(mappings, dbField, casRel, importData)) {
            ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ACCRUAL_UPLOAD_DICT_CHECK)));
        }
    }

    private void isInRedis(String val, BaseImportDTO importEntity, String masterDataKey, String excelName) {
        if (!isInRedis(val, masterDataKey)) {
            ValidField.setErrorMsg(importEntity, ValidField.buildReturnStr(excelName, DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ACCRUAL_UPLOAD_DICT_CHECK)));
        }
    }

    private boolean isInRedis(String val, String redisKey) {
        if (StrUtil.isBlank(val)) {
            return Boolean.FALSE;
        }
        Set<String> set = addValToSet(val);
        if (CollUtil.isNotEmpty(set)) {
            for (String s : set) {
                boolean isMember = redisUtils.isMember(redisKey, s);
                if (!isMember) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }


    private List<JSONObject> getByCache(String redisKey) {
        if (MasterDataCache.containsKey(redisKey)) {
            return MasterDataCache.get(redisKey);
        }
        Set<Object> set = redisUtils.members(redisKey);
        if (CollectionUtils.isEmpty(set)) {
            return new ArrayList<>();
        }
        List<JSONObject> jsonObjects = new ArrayList<>(set.size());
        for (Object obj : set) {
            if (null == obj) {
                continue;
            }
            JSONObject json=(JSONObject) JSON.toJSON(obj);
            jsonObjects.add(json);
        }
        if (CollectionUtils.isNotEmpty(jsonObjects)) {
            MasterDataCache.put(redisKey,jsonObjects);
        }
        return jsonObjects;
    }

    private boolean validVendorBank(String fieldName, Map<String,Object> importData) {
        String vendor = MapUtil.getStr(importData, "vendorCode");
        String bank = MapUtil.getStr(importData, fieldName);
        return isInRedis(vendor + "_" + bank, NewRedisMasterDataKey.VENDOR_BANK);
    }

    /**
     * 级联关系验证
     *
     * @param jsonObjects 缓存
     * @param fieldName   字段名称
     * @param hieRel      级联的字段
     * @param importData  导入的数据
     * @return
     */
    private boolean validHierarchicalRelation(List<JSONObject> jsonObjects, String fieldName, String[] hieRel, Map<String, Object> importData) {
        if (CollUtil.isEmpty(jsonObjects)) {
            return Boolean.TRUE;
        }
        Set<JSONObject> objects = new ConcurrentHashSet<>(jsonObjects);
        //需要验证的字段
        Set<String> fields = new LinkedHashSet<>();
        fields.add(fieldName);
        if (ArrayUtil.isNotEmpty(hieRel)) {
            CollUtil.addAll(fields, hieRel);
        }
        if (CollUtil.isNotEmpty(fields)) {
            return search(fields, objects, importData);
        }
        return Boolean.TRUE;
    }

    private boolean search(Set<String> fields, Set<JSONObject> objects, Map<String, Object> importData) {
        //这里遍历的是字段
        Set<JSONObject> thisFieldObject = null;
        for (String field : fields) {
            //这块必须用null判断
            if (null == thisFieldObject) {
                //第一次查找
                thisFieldObject = searchByDbData(objects, field, importData);
                //找不到就返回false
                if (CollUtil.isEmpty(thisFieldObject)) {
                    return Boolean.FALSE;
                } else {
                    continue;
                }
            }
            //后边的查找都是用上次查到的合集
            if (CollUtil.isNotEmpty(thisFieldObject)) {
                thisFieldObject = searchByDbData(thisFieldObject, field, importData);
                if (CollUtil.isEmpty(thisFieldObject)) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    private Set<JSONObject> searchByDbData(Set<JSONObject> dbData, String fieldName, Map<String, Object> importData) {
        Set<JSONObject> objects = new ConcurrentHashSet<>();
        //导入的值
        String importFieldVal = getImportVal(fieldName, importData);
        //涉及多选的情况
        Set<String> set = new HashSet<>();
        if (StrUtil.isNotBlank(importFieldVal) && importFieldVal.contains(",")) {
            set = addValToSet(importFieldVal);
        }else{
            set.add(importFieldVal);
        }
        if (CollUtil.isNotEmpty(set)) {
            //这里遍历的是字段的多选的值
            for (String s : set) {
                if (StrUtil.isBlank(s)) {
                    continue;
                }
                //收集等于该值的所有对象找不到就返回null
                Set<JSONObject> objectSet = searchVal(dbData, fieldName, s.trim());
                if (CollUtil.isEmpty(objectSet)) {
                    return new LinkedHashSet<>();
                }
                objects.addAll(objectSet);
            }
        }
        return objects;
    }

    private String getImportVal(String fieldName, Map<String, Object> importData) {
        if (headMapppingInfo == null) {
            return MapDataUtil.getStrVal(fieldName, importData);
        }
        if (headMapppingInfo.containsKey(fieldName)) {
            String excelTitle = MapUtil.getStr(headMapppingInfo,fieldName);
            if (StrUtil.isNotBlank(excelTitle)) {
                return MapUtil.getStr(importData, excelTitle);
            }
        }
        return MapUtil.getStr(importData, fieldName);
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

    private Set<JSONObject> searchVal(Set<JSONObject> dbData, String fieldName, String importFieldVal) {
        Set<JSONObject> collectSet = new ConcurrentHashSet<>();
        for (JSONObject jsonObject : dbData) {
            //实体的值
            String dbFieldVal = jsonObject.getString(fieldName);
            if (StrUtil.isBlank(dbFieldVal)) {
                String humpToLine = StringUtils.humpToLine(fieldName);
                dbFieldVal = jsonObject.getString(humpToLine);
            }
            if ("segmentCode".equals(fieldName)) {
                dbFieldVal = jsonObject.getString("parentSegment");
            }
            if (StrUtil.isBlank(dbFieldVal) || StrUtil.isBlank(importFieldVal)) {
                continue;
            }
            if (dbFieldVal.equalsIgnoreCase(importFieldVal)) {
                collectSet.add(jsonObject);
            }
        }
        return CollUtil.isEmpty(collectSet) ? null : collectSet;
    }

    public void setHeadMapppingInfo(Map<String,String> headMapppingInfo) {
        if (this.headMapppingInfo == null) {
            this.headMapppingInfo = new ConcurrentHashMap<>(headMapppingInfo);
        }
    }

}
