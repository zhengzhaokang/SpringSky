package com.wc.single.sky.common.log;

import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.utils.CommonUtils;
import com.wc.single.sky.common.utils.DateUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializableFieldCompare {


    public static <T> String compare(Class<T> type, T newObject, T oldObject) throws Exception {
        HashMap<String, Object> LogMap = getStringObjectHashMap(type, newObject, oldObject);
        if (LogMap == null) return "#";
        return new JSONObject(LogMap).toString();
    }

    public static <T> Map<String, Object> compareMap(Class<T> type, T newObject, T oldObject) throws Exception {
        Map<String, Object> LogMap = getStringObjectHashMap(type, newObject, oldObject);
        if (LogMap == null) return new HashMap<>();
        return LogMap;
    }

    private static <T> HashMap<String, Object> getStringObjectHashMap(Class<T> type, T newObject, T oldObject)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        HashMap<String, Object> LogMap = new HashMap<>();
        Class<?> newObj = newObject.getClass();
        Class<?> oldObj = oldObject.getClass();
        Field[] newFields = type.getDeclaredFields();
        for (int i = 0; i < newFields.length; i++) {
            FieldCompare newAnnotation = newFields[i].getAnnotation(FieldCompare.class);
            if (null != newAnnotation) {

                newFields[i].setAccessible(true);//关闭字段的安全检测
                Object oldVal =  newFields[i].get(oldObject);//获得指定对象的属性值
                Object newVal =  newFields[i].get(newObject);

           /*     PropertyDescriptor newPd = new PropertyDescriptor(newFields[i].getName(), newObj);
                Method getMethodNew = newPd.getReadMethod();
                Object oldVal = getMethodNew.invoke(oldObject);
                Object newVal = getMethodNew.invoke(newObject);*/
                boolean eq = false;
                if (oldVal instanceof String && null != newVal) {
                    String s1 = String.valueOf(oldVal).trim();
                    String s2 = String.valueOf(newVal).trim();
                    eq = !s1.equals(s2);
                } else if (oldVal instanceof Integer&&null!= newVal) {
                    int i1 = (Integer) oldVal;
                    int i2 = (Integer) newVal;
                    eq = i1 != i2;
                } else if (oldVal instanceof Double&&null != newVal) {
                    double d1 = (Double) oldVal;
                    double d2 = (Double) newVal;
                    eq = d1 != d2;
                } else if (oldVal instanceof BigDecimal&&null != newVal) {
                    BigDecimal b1 = (BigDecimal) oldVal;
                    BigDecimal b2 = (BigDecimal) newVal;
                    eq = b1.compareTo(b2) != 0;
                } else if (oldVal instanceof Long &&null != newVal) {
                    long l1 = (Long) oldVal;
                    long l2 = (Long) newVal;
                    eq = l1 != l2;
                } else if (oldVal instanceof Date &&null != newVal) {
                    Date l1 = (Date) oldVal;
                    Date l2 = (Date) newVal;

                    eq = l1.getTime() != l2.getTime();
                } else if (oldVal instanceof List &&null != newVal) {
                    String l1 = StringUtils.join(oldVal,",");
                    String l2 = StringUtils.join(newVal,",");
                    eq = l1 != l2;
                }
                String s1 =valueConvert(oldVal,newAnnotation);
                String s2 = valueConvert(newVal,newAnnotation);
                if (eq) {
                    Map<String, String> map = codeToNameMap(newAnnotation);
                    String join = newAnnotation.join();
                    if(StringUtils.isNotBlank(join)){
                        PropertyDescriptor joinField = new PropertyDescriptor(join, newObj);
                        Method getMethodJoin = joinField.getReadMethod();
                        Object oldValJoin = getMethodJoin.invoke(newObject);
                        System.out.println(oldObj);

                        if (map.size() > 0) {
                            String message=oldValJoin + newAnnotation.name() + Constants.DATA_COMPARISON_TIPS + "[" + map.get(s1) + "] to [" + map.get(s2) + "].";
                            LogMap.put(newAnnotation.name(),message);
                        } else {
                            String old=  StringUtils.isNotBlank(s1)?s1:" ";
                            String newValue=  StringUtils.isNotBlank(s2)?s2:" ";
                            String message=oldValJoin+" " + newAnnotation.name() + Constants.DATA_COMPARISON_TIPS + "[" + old + "] to [" + newValue + "].";
                            LogMap.put(newAnnotation.name(),message);
                        }
                    }else{
                        if (map.size() > 0) {
                            String message=newAnnotation.name() + Constants.DATA_COMPARISON_TIPS + "[" + map.get(s1) + "] to [" + map.get(s2) + "].";
                            LogMap.put(newAnnotation.name(),message);
                        } else {
                            String old=  StringUtils.isNotBlank(s1)?s1:" ";
                            String newValue=  StringUtils.isNotBlank(s2)?s2:" ";
                            String message=newAnnotation.name()+" " + Constants.DATA_COMPARISON_TIPS + "[" + old + "] to [" + newValue + "].";
                            LogMap.put(newAnnotation.name(),message);
                        }
                    }

                }
            }
        }
        if(MapUtils.isEmpty(LogMap)){
            return null;
        }
        return LogMap;
    }

    private static String valueConvert(Object value,FieldCompare newAnnotation){
        if(null!=value){
            if(value instanceof Date){
               Date result= (Date) value;
               String dataFormat = "MM/dd/yyyy";
                if(CommonUtils.stringIsNotEmpty(newAnnotation.dateFormat())){
                    dataFormat=newAnnotation.dateFormat();
                }
              return   DateUtils.dateFormat(result,dataFormat);
            }else{
                return value.toString().trim();
            }
        }else{
            return "";
        }

    }

    private static Map<String, String> codeToNameMap(FieldCompare newAnnotation) {
        Map<String, String> map = new HashMap<>();
        String properties = newAnnotation.properties();
        if (StringUtils.isNotBlank(properties)) {
            String[] propertiesArr = properties.split(",");
            for (String propertie : propertiesArr) {
                String[] split = propertie.split(":");
                map.put(split[0], split[1]);
            }
        }
        return map;
    }
}
