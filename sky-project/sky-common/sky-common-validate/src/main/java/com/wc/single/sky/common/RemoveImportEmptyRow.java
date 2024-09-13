package com.wc.single.sky.common;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RemoveImportEmptyRow {

    private RemoveImportEmptyRow() {
    }

    /**
     * 去除属性全部为null的行
     * @param list
     * @return
     */
    public static <T> List<T> removeNullValue(List<T> list) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> newList = new ArrayList<>();
        for (T dto : list) {
            if (null != dto && !checkObjAllFieldsIsNull(dto)) {
                newList.add(dto);
            }
        }
        return newList;
    }
    /**
     * 检查实体 是否所有属性都为空
     * @param object
     * @return
     */
    private static boolean checkObjAllFieldsIsNull(Object object) {
        for (Field f : object.getClass().getDeclaredFields()) {
            ReflectionUtils.makeAccessible(f);
            try {
                if (f.get(object) != null && StrUtil.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
