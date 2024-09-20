package com.wc.single.sky.common.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author guowb1
 * @date 2022/9/2 19:15
 */
public class StringDeserializer implements ObjectDeserializer {

    private static final List<String> NULL_VALUE = Collections.unmodifiableList(Arrays.asList("N/A"));

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object value = parser.parse();
        String valueStr = String.valueOf(value);
        if (NULL_VALUE.contains(valueStr)) {
            return null;
        }
        if(Objects.equals(String.class.getTypeName(), type.getTypeName())) {
            return (T) valueStr;
        } else if (Objects.equals(BigDecimal.class.getTypeName(), type.getTypeName())) {
            return (T) new BigDecimal(valueStr.trim());
        } else if (Objects.equals(Long.class.getTypeName(), type.getTypeName())) {
            return (T) Long.valueOf(new BigDecimal(valueStr.trim()).longValue());
        } else if (Objects.equals(Integer.class.getTypeName(), type.getTypeName())) {
            return (T) Integer.valueOf(new BigDecimal(valueStr.trim()).intValue());
        }
        return (T) value;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
