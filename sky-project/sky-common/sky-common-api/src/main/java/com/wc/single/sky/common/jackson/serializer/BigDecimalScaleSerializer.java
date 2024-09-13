package com.wc.single.sky.common.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wc.single.sky.common.annotation.BigDecimalToString;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author guowb1
 * @description BigDecimal序列化
 * @date 2022/6/23 18:22
 */
public class BigDecimalScaleSerializer extends StdSerializer<BigDecimal> implements ContextualSerializer {
    private int scale;
    private RoundingMode roundingMode;
    private boolean thousandSeparate;

    public BigDecimalScaleSerializer() {
        super(BigDecimal.class);
    }

    public BigDecimalScaleSerializer(int scale, RoundingMode roundingMode, boolean thousandSeparate) {
        super(BigDecimal.class);
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.thousandSeparate = thousandSeparate;
    }

    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeNull();
        } else {
            BigDecimal bigDecimalValue = value.setScale(this.scale, this.roundingMode);
            String bigDecimalValueStr = null;
            if (thousandSeparate) {
                bigDecimalValueStr = String.format("%,." + scale + "f", bigDecimalValue);
            } else {
                bigDecimalValueStr = String.valueOf(bigDecimalValue);
            }
            gen.writeString(bigDecimalValueStr);
        }

    }

    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        boolean thousandSeparate = true;
        if (null != property) {
            BigDecimalToString ann = property.getAnnotation(BigDecimalToString.class);
            if (null != ann) {
                scale = ann.scale();
                roundingMode = ann.roundingMode();
                thousandSeparate = ann.thousandSeparate();
            }
        }

        return new BigDecimalScaleSerializer(scale, roundingMode, thousandSeparate);
    }
}
