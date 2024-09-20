package com.wc.single.sky.common.core.viewFeild;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.constant.ViewFieldJsonKeys;

/***
 * @author dhc
 */
public class ValidViewField {

    /**
     * 根据字段和验证的项目验证
     * @param fieldConfig 字段的配置
     * @param fieldVal 字段的值
     * @param validKey 要验证的项
     * @return true 成功 false 失败
     */
    public  static Boolean validViewField(JSONObject fieldConfig, String fieldVal,String validKey) {
        if (null != fieldConfig && StrUtil.isNotBlank(validKey)) {
            switch (validKey) {
                //非空判断
                case ViewFieldJsonKeys.REQUIRED:
                    //默认是不验证
                    Boolean re = fieldConfig.getBoolean(ViewFieldJsonKeys.REQUIRED) == null ? false : fieldConfig.getBoolean(ViewFieldJsonKeys.REQUIRED);
                    if (re && StrUtil.isBlank(fieldVal)) {
                        return Boolean.FALSE;
                    } else {
                        return Boolean.TRUE;
                    }
                //单选多选效验
                case ViewFieldJsonKeys.MULTIPLE:
                    Boolean mu = fieldConfig.getBoolean(ViewFieldJsonKeys.MULTIPLE) == null ? true : fieldConfig.getBoolean(ViewFieldJsonKeys.MULTIPLE);
                    //如果选择的是单选 但是字段多选了
                    if (!mu && StrUtil.isNotBlank(fieldVal) && fieldVal.contains(",")) {
                        return Boolean.FALSE;
                    } else {
                        return Boolean.TRUE;
                    }
                //正则效验 暂时先没做
                case ViewFieldJsonKeys.REGLIST:
                    return Boolean.TRUE;
                default:
                    break;
            }
        }
        return Boolean.FALSE;
    }
}
