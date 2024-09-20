package com.wc.single.sky.disclosures.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * EnumUtil For
 *
 * @author tsj
 * @date 2022/12/12 16:37
 * @since 1.8
 */
@Slf4j
public class EnumUtil {

    public static boolean isInclude(Class enumClass, String code) {
        List enumList = EnumUtils.getEnumList(enumClass);
        for (int i = 0; i < enumList.size(); i++) {
            Object en = enumList.get(i);
            Class<?> enClass = en.getClass();
            try {
                Method method = enClass.getMethod("getCode");
                Object invoke = method.invoke(en, null);
                if (invoke.toString().equals(code)) {
                    return true;
                }
            } catch (Exception e) {
                log.error("枚举执行getCode方法失败...");
            }
        }
        return false;
    }


    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String code) {
        if (c != null && code != null) {
            try {
                return Enum.valueOf(c, code.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                log.error("getEnumFromString error",ex);
            }
        }
        return null;
    }
}
