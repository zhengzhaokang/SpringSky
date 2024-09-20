package com.wc.single.sky.common;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 去除前后空格工具类
 * @author daihuaicai
 */
public class TrimUtil {

    private TrimUtil() {
    }

    public  static void trim(Object obj) {
        if (null == obj) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldValue(obj, fields);
    }
    private static void fieldValue(Object obj, Field[] declaredFields1) {
        for (Field field : declaredFields1) {
            String type = field.getType().getCanonicalName();
            if (StringUtils.equals("java.lang.String", type)) {
                ReflectionUtils.makeAccessible(field);
                Object object = null;
                try {
                    object = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (null != object) {
                   trimValue(field,object,obj);
                }
            }
        }
    }

    private static void trimValue(Field field, Object value, Object obj) {
        StringBuilder trim = new StringBuilder();
        String s = value.toString();
        //对多选的值进行空格去除
        if (s.contains(",")) {
            String[] split = s.split(",");
            for (String s1 : split) {
                trim.append(s1.trim());
                trim.append(",");
            }
            //去除字符串最后的逗号
            String str = trim.substring(0,trim.length()-1);
            try {
                ReflectUtil.setFieldValue(obj, field, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                ReflectUtil.setFieldValue(obj, field, s.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
