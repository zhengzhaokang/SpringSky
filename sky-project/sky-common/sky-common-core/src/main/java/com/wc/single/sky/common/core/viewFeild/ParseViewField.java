package com.wc.single.sky.common.core.viewFeild;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ParseViewField {

    /**
     * 获取要解析的字段
     * @param viewFields
     * @param fieldName
     * @return
     */
    public static  JSONObject getValidViewField(List<JSONObject> viewFields, String fieldName) {
        if (null != viewFields && viewFields.size() > 0 && StrUtil.isNotBlank(fieldName)) {
            for (JSONObject object : viewFields) {
                if (null == object) {
                    continue;
                }
                JSONArray jsonArray = object.getJSONArray("fields");
                if (null == jsonArray) {
                   continue;
                }
                List<JSONObject> fieldObj = jsonArray.toJavaList(JSONObject.class);
                if (null == fieldObj || fieldObj.size() <= 0) {
                   continue;
                }
                for (JSONObject jsonObject : fieldObj) {
                    if (fieldName.equals(jsonObject.getString("__vModel__"))) {
                        return jsonObject;
                    }
                }
            }
        }
        return null;
    }
}
