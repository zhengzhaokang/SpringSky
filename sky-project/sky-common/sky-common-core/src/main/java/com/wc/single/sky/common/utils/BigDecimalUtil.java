package com.wc.single.sky.common.utils;

import com.wc.single.sky.common.constant.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class BigDecimalUtil {
    private BigDecimalUtil() {
    }

    //A*B%+C-D
    public static BigDecimal getDifference(BigDecimal minuend, BigDecimal subtraction) {
        return minuend.multiply(subtraction).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    // A*B%+C
    public static BigDecimal getNewValue(BigDecimal a, BigDecimal b, BigDecimal c) {
        List<BigDecimal> decimalList = Arrays.asList(new BigDecimal("0"), new BigDecimal("0.0"), new BigDecimal("0.00"), new BigDecimal("0.000"));
        if (decimalList.contains(a) || decimalList.contains(b)) {
            return (c).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal decimal = b.divide(new BigDecimal(100));
        return decimal.multiply(a).add(c).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal formatPrecision(BigDecimal decimalValue) {
        return BigDecimalUtil.formatPrecision(decimalValue, null, null);
    }

    public static BigDecimal formatPrecision(BigDecimal decimalValue, Integer scale, RoundingMode roundingMode) {
        if (decimalValue == null) {
            return null;
        }
        if (scale == null) {
            scale = Constants.DEFAULT_SCALE;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_DOWN;
        }
        return decimalValue.setScale(scale, roundingMode);
    }

    public static String formatPrecisionAndSeparate(BigDecimal decimalValue) {
        return BigDecimalUtil.formatPrecisionAndSeparate(decimalValue, null, null);
    }

    public static String formatPrecisionAndSeparate(BigDecimal decimalValue,Integer scale) {
        return BigDecimalUtil.formatPrecisionAndSeparate(decimalValue, scale, null);
    }

    public static String formatPrecisionAndSeparate(BigDecimal decimalValue, Integer scale, RoundingMode roundingMode) {
        if (decimalValue == null) {
            return null;
        }
        if (scale == null) {
            scale = Constants.DEFAULT_SCALE;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_UP;
        }
        BigDecimal bigDecimal = decimalValue.setScale(scale, roundingMode);
        return String.format("%,."+scale+"f", bigDecimal);
    }

    /**
     * @deprecated BigDecimal取负数可以直接调用java.math.BigDecimal#negate()方法
     * 不用这样手动拼”-“号
     * @param money
     * @return
     */
    @Deprecated
    public static BigDecimal addMinusSign(BigDecimal money){
        if(money.compareTo(BigDecimal.ZERO)==0){
            return new BigDecimal(0);
        }
        BigDecimal result = new BigDecimal("-" + money.toString());
        return result;

    }
}
