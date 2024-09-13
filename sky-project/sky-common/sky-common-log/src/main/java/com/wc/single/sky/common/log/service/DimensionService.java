package com.wc.single.sky.common.log.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.system.domain.dto.SysBusinessOperatorLogDTO;

public class DimensionService{


    public static void setDimension(SysBusinessOperatorLogDTO operatorLogDTO, Object operationData) {
        if (null != operationData) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(operationData);
            String geoCode = jsonObject.getString("geoCode");
            if (StrUtil.isBlank(geoCode)) {
                geoCode = jsonObject.getString("areaType");
            }
            String bg = jsonObject.getString("businessGroup");
            String salesOrg = jsonObject.getString("salesOrgCode");
            String salesOffice = jsonObject.getString("salesOfficeCode");
            String regionMarket = jsonObject.getString("regionMarketCode");
            String segment = jsonObject.getString("segmentCode");
            String gtnType = jsonObject.getString("gtnTypeCode");
            String  version = jsonObject.getString("versionCode");

            if (StrUtil.isBlank(gtnType)) {
                gtnType = jsonObject.getString("gtnType");
            }
            if (StrUtil.isNotBlank(geoCode)) {
                operatorLogDTO.setGeoCode(geoCode);
            }
            if (StrUtil.isNotBlank(bg)) {
                operatorLogDTO.setBusinessGroup(bg);
            }
            if (StrUtil.isNotBlank(salesOrg)) {
                operatorLogDTO.setSalesOrgCode(salesOrg);
            }
            if (StrUtil.isNotBlank(salesOffice)) {
                operatorLogDTO.setSalesOfficeCode(salesOffice);
            }
            if (StrUtil.isNotBlank(gtnType)) {
                operatorLogDTO.setGtnTypeCode(gtnType);
            }
            if (StrUtil.isNotBlank(segment)) {
                operatorLogDTO.setSegmentCode(segment);
            }
            if (StrUtil.isNotBlank(regionMarket)) {
                operatorLogDTO.setRegionMarketCode(regionMarket);
            }
            if (null != version) {
                operatorLogDTO.setAccrualVersion(version);
            }
        }
    }

    public static void setAccrualDimension(SysBusinessOperatorLogDTO operatorLogDTO, Object operationData) {
        if (null != operationData) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(operationData);
            String geoCode = jsonObject.getString("area_type");
            String bg = jsonObject.getString("business_group");
            String salesOrg = jsonObject.getString("sales_org_code");
            String salesOffice = jsonObject.getString("sales_office_code");
            String regionMarket = jsonObject.getString("region_market_code");
            String segment = jsonObject.getString("segment_code");
            String gtnType = jsonObject.getString("gtn_type");
            String  version = jsonObject.getString ("version_code");

            if (StrUtil.isBlank(gtnType)) {
                gtnType = jsonObject.getString("gtnType");
            }
            if (StrUtil.isNotBlank(geoCode)) {
                operatorLogDTO.setGeoCode(geoCode);
            }
            if (StrUtil.isNotBlank(bg)) {
                operatorLogDTO.setBusinessGroup(bg);
            }
            if (StrUtil.isNotBlank(salesOrg)) {
                operatorLogDTO.setSalesOrgCode(salesOrg);
            }
            if (StrUtil.isNotBlank(salesOffice)) {
                operatorLogDTO.setSalesOfficeCode(salesOffice);
            }
            if (StrUtil.isNotBlank(gtnType)) {
                operatorLogDTO.setGtnTypeCode(gtnType);
            }
            if (StrUtil.isNotBlank(segment)) {
                operatorLogDTO.setSegmentCode(segment);
            }
            if (StrUtil.isNotBlank(regionMarket)) {
                operatorLogDTO.setRegionMarketCode(regionMarket);
            }
            if (null != version) {
                operatorLogDTO.setAccrualVersion(version);
            }
        }
    }
}
