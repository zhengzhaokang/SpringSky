package com.wc.single.sky.common.utils;

import cn.hutool.core.lang.Assert;
import jodd.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Map通用处理方法
 *
 * @author lovefamily
 */
public class MapDataUtil {
    public static Map<String, Object> convertDataMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator<?> entries = properties.entrySet().iterator();
        Map.Entry<?, ?> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Entry<?, ?>) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public static String getStrVal(String key, Map map) {
        if (map != null && map.size() > 0) {
            Object obj = map.get(key);
            if (obj != null) {
                return obj.toString();
            }
        }
        return null;
    }

    public static Object getObjVal(String key, Map map) {
        if (map != null && map.size() > 0) {
            Object obj = map.get(key);
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    /**
     * 对象转Map
     *
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> beanToMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        return map;
    }

    /**
     * map转对象
     *
     * @param map
     * @param beanClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapToBean(Map map, Class<T> beanClass) throws Exception {
        T object = beanClass.newInstance();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(object, map.get(field.getName()));
            }
        }
        return object;
    }

    public static <T> T mapToEntity(Map<?, ?> map, Class<T> clazz) {
        // 验证Map和实体类Class对象是否为空
        Assert.notNull(map, "map cannot be null");
        Assert.notNull(clazz, "entity class cannot be null");
        try {
            List<Field> allFields = new ArrayList<>();
            T t = clazz.newInstance();
            Class<?> aClass = clazz;
            do {
                // 利用Java反射获取自身属性以及父类属性
                allFields.addAll(Stream.of(aClass.getDeclaredFields()).collect(Collectors.toList()));
                aClass = aClass.getSuperclass();
                // Object类为超级大类，到了Object类，继承关系已到了顶点，不需要处理了
            } while (!StringUtil.equals(aClass.getTypeName(), Object.class.getTypeName()));
            // 对实体类的属性进行遍历
            for (Field field : allFields) {
                // 获取属性名称
                String fieldName = field.getName();
                // 属性对应的实体类名称，这里需要一一对应，否则取不到值
                Object data = map.get(fieldName);
                // 按照Java约定的getter、setter方法命名规范，拼装setter方法名
                String methodName = String.format("set{}{}", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
                // 获取setter方法的Method对象
                Method method = clazz.getMethod(methodName, field.getType());
                // 利用Java反射调用实体对象的setter方法对属性进行赋值操作
                try {
                    method.invoke(t, getData(data, field.getType()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return t;
        }  catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 这里的这个方法是为了兼容Sql语句查询后返回的Map。
     * 在使用Mybatis查询后返回的数据类型为了兼容都使用了大类型，本来是int类型的数据
     * 但还是返回了long类型，在这里就做了特殊处理，如果实体类中属性为int，Map中类型为Long时，
     * 就对类型进行转换。在这里还可以做其他的兼容处理
     */
    private static Object getData(Object data, Class<?> type) throws ParseException {
        String typeName = type.getTypeName();
        if (StringUtil.equals(typeName, Integer.class.getTypeName())) {
            if (data instanceof Long) {
                return ((Long) data).intValue();
            }
        }
        if (StringUtil.equals(typeName, Date.class.getTypeName())) {
            if (data instanceof String) {
                return DateUtils.dateParse(data.toString(), DateUtils.YYYY_MM_DD_HH_MM_SS);
            }
        }
        return data;
    }

}
