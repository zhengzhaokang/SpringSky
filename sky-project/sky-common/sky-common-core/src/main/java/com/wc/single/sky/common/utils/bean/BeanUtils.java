package com.wc.single.sky.common.utils.bean;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.utils.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Bean 工具类
 *
 * @Author lovefamily
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * Bean方法名中属性名开始的下标
     */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /**
     * 匹配getter方法的正则表达式
     */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /**
     * 匹配setter方法的正则表达式
     */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src  源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     *
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     *
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     *
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    /**
     * 当前类实体转map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> objConvertMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object) != null ? field.get(object) : null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            map.put(field.getName(), value);
        }
        return map;
    }

    /**
     * 对象之间拷贝
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * * 将map的值赋值给bean
     *
     * @param bean
     * @param map
     */
    public static void setMapToBean(Object bean, Map<String, Object> map) {
        if (null == bean || MapUtil.isEmpty(map)) {
            return;
        }
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String fieldName = key;
            if (key.contains("_")) {
                fieldName = StringUtils.toCamelCase(key);
            }
            Field field = isHasField(fieldName, bean);
            if (null == field) {
                continue;
            }
            Class clazz = field.getType();
            if (ClassUtil.isBasicType(clazz) || clazz.getName().equals("java.lang.String")) {

                ReflectUtil.setFieldValue(bean, fieldName, map.get(key));
            }
            if (!ClassUtil.isBasicType(clazz) && !clazz.getName().equals("java.lang.String")) {
                if (clazz.getName().equals("java.util.List")) {
                    List<Object> list = (List<Object>) map.get(key);
                    for (Object obj : list) {
                        if (null == obj) {
                            continue;
                        }
                        Class objClass = obj.getClass();
                        LinkedHashMap linkedHashMap = (LinkedHashMap) obj;
                        if (null == linkedHashMap) {
                            return;
                        }
                        try {
                            setMapToBean(objClass.newInstance(), linkedHashMap);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    ReflectUtil.setFieldValue(bean, fieldName, list);
                }

            }

        }
    }

    public static void setMapValueToBean(Object obj, Map<String, Object> map) {
        if (null == obj || MapUtil.isEmpty(map)) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (map.containsKey(fieldName)) {
                Object value = map.get(fieldName);
                if (null != value) {
                    ReflectUtil.setFieldValue(obj, fieldName, value);
                }
            }
        }
    }

    public static boolean isHas(String fieldName, Object bean) {
        return null == isHasField(fieldName, bean) ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Field isHasField(String fieldName, Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (null == field) {
                continue;
            }
            String name = field.getName();
            if (fieldName.equals(name)) {
                return field;
            }
        }
        return null;
    }


    /**
     * * 对象List之间拷贝
     *
     * @param sourceList       原数据集合
     * @param targetList       目标集合
     * @param targetClass      目标类
     * @param ignoreProperties 忽略字段
     * @param <T>
     * @param <E>
     */
    public static <T, E> void copyListProperties(List<E> sourceList, List<T> targetList, Class<T> targetClass, String... ignoreProperties) {
        sourceList.stream().map(source -> clone(source, targetClass, ignoreProperties)).forEach(targetList::add);
    }

    /**
     * 对象之间拷贝-有返回值
     */
    public static <T, E> E clone(T source, Class<E> classType, String... ignoreProperties) {
        if (null == source) {
            return null;
        }
        E targetInstance;
        try {
            targetInstance = classType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        org.springframework.beans.BeanUtils.copyProperties(source, targetInstance, ignoreProperties);
        return targetInstance;
    }

    public static boolean isSameByAssignProperties(Object based, Object compared, String... assignProperties) {
        Map<Field, Object> fieldObjectMap = compareByAssignProperties(based, compared, assignProperties);
        return fieldObjectMap == null || fieldObjectMap.isEmpty();
    }

    public static boolean isSameByIgnoreProperties(Object based, Object compared, String... ignoreProperties) {
        Map<Field, Object> fieldObjectMap = compareByIgnoreProperties(based, compared, ignoreProperties);
        return fieldObjectMap == null || fieldObjectMap.isEmpty();
    }

    private static Map<Field, Object> compareByAssignProperties(Object based, Object compared, String... assignProperties) {
        Map<Field, Object> compareResult = new HashMap<Field, Object>();
        try {
            List<String> assignPropertyList = Arrays.asList(assignProperties);

            Map<String, Field> basedFieldMap = objectReflex(based);
            Map<String, Field> comparedFieldMap = objectReflex(compared);

            Set<String> basedFieldNameSet = basedFieldMap.keySet();
            Set<String> comparedFieldNameSet = comparedFieldMap.keySet();
            Set<String> assignPropertySet = new HashSet<>(assignPropertyList);

            basedFieldNameSet.retainAll(comparedFieldNameSet);
            basedFieldNameSet.retainAll(assignPropertySet);
            if (basedFieldNameSet == null || basedFieldNameSet.isEmpty()) {
                return compareResult;
            }
            for (String basedFieldName : basedFieldNameSet) {
                Field basedField = basedFieldMap.get(basedFieldName);
                basedField.setAccessible(true);
                Field comparedField = comparedFieldMap.get(basedFieldName);
                comparedField.setAccessible(true);
                Object basedFieldValue = basedField.get(based);
                Object comparedFieldValue = comparedField.get(compared);
                if (basedFieldValue instanceof Comparable
                        && comparedFieldValue instanceof Comparable
                        && basedFieldValue != null
                        && comparedFieldValue != null
                        && basedFieldValue.getClass().equals(comparedFieldValue.getClass())) {
                    if (((Comparable) basedFieldValue).compareTo(((Comparable) comparedFieldValue)) != 0) {
                        compareResult.put(basedField, basedFieldValue);
                    }
                    continue;
                }
                if (!Objects.equals(basedFieldValue, comparedFieldValue)) {
                    compareResult.put(basedField, basedFieldValue);
                }
            }
            return compareResult;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Map<Field, Object> compareByIgnoreProperties(Object based, Object compared, String... ignoreProperties) {
        Map<Field, Object> compareResult = new HashMap<Field, Object>();
        try {
            List<String> ignorePropertyList = Arrays.asList(ignoreProperties);

            Map<String, Field> basedFieldMap = objectReflex(based);
            Map<String, Field> comparedFieldMap = objectReflex(compared);

            Set<String> basedFieldNameSet = basedFieldMap.keySet();
            Set<String> comparedFieldNameSet = comparedFieldMap.keySet();
            Set<String> ignorePropertySet = new HashSet<>(ignorePropertyList);

            basedFieldNameSet.retainAll(comparedFieldNameSet);
            basedFieldNameSet.removeAll(ignorePropertySet);
            if (basedFieldNameSet == null || basedFieldNameSet.isEmpty()) {
                return compareResult;
            }
            for (String basedFieldName : basedFieldNameSet) {
                Field basedField = basedFieldMap.get(basedFieldName);
                basedField.setAccessible(true);
                Field comparedField = comparedFieldMap.get(basedFieldName);
                comparedField.setAccessible(true);
                Object basedFieldValue = basedField.get(based);
                Object comparedFieldValue = comparedField.get(compared);
                if (basedFieldValue instanceof Comparable
                        && comparedFieldValue instanceof Comparable
                        && basedFieldValue != null
                        && comparedFieldValue != null
                        && basedFieldValue.getClass().equals(comparedFieldValue.getClass())) {
                    if (((Comparable) basedFieldValue).compareTo(((Comparable) comparedFieldValue)) != 0) {
                        compareResult.put(basedField, basedFieldValue);
                    }
                    continue;
                }
                if (!Objects.equals(basedFieldValue, comparedFieldValue)) {
                    compareResult.put(basedField, basedFieldValue);
                }
            }
            return compareResult;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Map<String, Object> object2MapByIgnoreProperties(Object object, String... ignoreProperties) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<String> ignorePropertyList = Arrays.asList(ignoreProperties);

            Map<String, Field> fieldMap = objectReflex(object);
            Set<String> fieldNameSet = fieldMap.keySet();
            Set<String> ignorePropertySet = new HashSet<>(ignorePropertyList);

            fieldNameSet.removeAll(ignorePropertySet);
            if (fieldNameSet == null || fieldNameSet.isEmpty()) {
                return resultMap;
            }
            for (String fieldName : fieldNameSet) {
                Field field = fieldMap.get(fieldName);
                field.setAccessible(true);
                resultMap.put(fieldName, field.get(object));
            }
            return resultMap;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Map<String, Object> object2MapByAssignProperties(Object object, String... assignProperties) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<String> assignPropertyList = Arrays.asList(assignProperties);

            Map<String, Field> fieldMap = objectReflex(object);
            Set<String> fieldNameSet = fieldMap.keySet();
            Set<String> assignPropertySet = new HashSet<>(assignPropertyList);

            fieldNameSet.retainAll(assignPropertySet);
            if (fieldNameSet == null || fieldNameSet.isEmpty()) {
                return resultMap;
            }
            for (String fieldName : fieldNameSet) {
                Field field = fieldMap.get(fieldName);
                field.setAccessible(true);
                resultMap.put(fieldName, field.get(object));
            }
            return resultMap;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Map<String, Field> objectReflex(Object object) {
        if (object == null) {
            return new HashMap<>();
        }
        List<Field> fieldList = new ArrayList<Field>();
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        return fieldList.stream().collect(Collectors.toMap(Field::getName, field -> field, (k1, k2) -> k2));
    }

    /**
     * 根据字段名称获取字段值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (null == obj || StrUtil.isBlank(fieldName)) {
            return null;
        }
        // 获取该对象的Class
        Class objClass = obj.getClass();
        // 初始化返回值
        Object result = null;
        // 获取所有的属性数组
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            // 属性名称
            String currentFieldName = field.getName();
            try {
                if (currentFieldName.equals(fieldName)) {
                    field.setAccessible(true);
                    result = field.get(obj);
                    return result; // 通过反射拿到该属性在此对象中的值(也可能是个对象)
                }

            } catch (SecurityException e) {
                // 安全性异常
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // 非法参数
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // 无访问权限
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 获取非空属性
     *
     * @param source
     * @return 所有非空的属性名
     */
    public static String[] getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
