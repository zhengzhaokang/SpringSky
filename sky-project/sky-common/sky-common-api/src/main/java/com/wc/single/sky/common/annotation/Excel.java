package com.wc.single.sky.common.annotation;

import com.wc.single.sky.common.utils.poi.ExcelHandlerAdapter;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * 自定义导出Excel数据注解
 * 
 * @author lovefamily
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * 导出时在excel中排序
     */
    public int sort() default Integer.MAX_VALUE;

    /**
     * 导出到Excel中的名字.
     */
    public String name() default "";

    /**
     * 日期格式, 如: yyyy-MM-dd
     */
    public String dateFormat() default "";

    /**
     * 读取内容转表达式 (如: 0=男,1=女,2=未知)
     */
    public String readConverterExp() default "";

    /**
     * 分隔符，读取字符串组内容
     */
    public String separator() default ",";

    /**
     * BigDecimal 精度 默认:-1(默认不开启BigDecimal格式化)
     */
    public int scale() default -1;

    /**
     * BigDecimal 舍入规则 默认:BigDecimal.ROUND_HALF_EVEN
     */
    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * 导出类型（0数字 1字符串）
     */
    public ColumnType cellType() default ColumnType.STRING;

    /**
     * 导出时在excel中每个列的高度 单位为字符
     */
    public double height() default 14;

    /**
     * 导出时在excel中每个列的宽 单位为字符
     */
    public double width() default 16;

    /**
     * 文字后缀,如% 90 变成90%
     */
    public String suffix() default "";

    /**
     * 当值为空时,字段的默认值
     */
    public String defaultValue() default "";

    /**
     * 提示信息
     */
    public String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容.
     */
    public String[] combo() default {};

    /**
     * 是否导出数据,应对需求:有时我们需要导出一份模板,这是标题需要但内容需要用户手工填写.
     */
    public boolean isExport() default true;

    /**
     * 另一个类中的属性名称,支持多级获取,以小数点隔开
     */
    public String targetAttr() default "";

    /**
     * 是否自动统计数据,在最后追加一行统计数据总和
     */
    public boolean isStatistics() default false;

    /**
     * 导出字段对齐方式（0：默认；1：靠左；2：居中；3：靠右）
     */
    public Align align() default Align.LEFT;

    /**
     * 自定义数据处理器
     */
    public Class<?> handler() default ExcelHandlerAdapter.class;

    /**
     * 自定义数据处理器参数
     */
    public String[] args() default {};

    /**
     * 千位分隔，必须指定BigDecimal精度
     */
    public boolean thousandSeparate() default false;

    public enum Align
    {
        AUTO(0), LEFT(1), CENTER(2), RIGHT(3);
        private final int value;

        Align(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

     Colour tableHeadColour() default Colour.GREY_50_PERCENT;

    public enum Colour {


        AQUA(IndexedColors.AQUA.getIndex()),
        AUTOMATIC(IndexedColors.AUTOMATIC.getIndex()),
        BLUE(IndexedColors.BLUE.getIndex()),
        BLUE_GREY(IndexedColors.BLUE_GREY.getIndex()),
        BRIGHT_GREEN(IndexedColors.BRIGHT_GREEN.getIndex()),
        BROWN(IndexedColors.BROWN.getIndex()),
        CORAL(IndexedColors.CORAL.getIndex()),
        CORNFLOWER_BLUE(IndexedColors.CORNFLOWER_BLUE.getIndex()),
        DARK_BLUE(IndexedColors.DARK_BLUE.getIndex()),
        DARK_GREEN(IndexedColors.DARK_GREEN.getIndex()),
        DARK_RED(IndexedColors.DARK_RED.getIndex()),
        DARK_TEAL(IndexedColors.DARK_TEAL.getIndex()),
        DARK_YELLOW(IndexedColors.DARK_YELLOW.getIndex()),
        GOLD(IndexedColors.GOLD.getIndex()),
        GREEN(IndexedColors.GREEN.getIndex()),
        GREY_25_PERCENT(IndexedColors.GREY_25_PERCENT.getIndex()),
        GREY_40_PERCENT(IndexedColors.GREY_40_PERCENT.getIndex()),
        GREY_50_PERCENT(IndexedColors.GREY_50_PERCENT.getIndex()),
        GREY_80_PERCENT(IndexedColors.GREY_80_PERCENT.getIndex()),
        INDIGO(IndexedColors.INDIGO.getIndex()),
        LAVENDER(IndexedColors.LAVENDER.getIndex()),
        LEMON_CHIFFON(IndexedColors.LEMON_CHIFFON.getIndex()),
        LIGHT_BLUE_(IndexedColors.LIGHT_BLUE.getIndex()),
        LEMON_CHIFFON_TWO(IndexedColors.LEMON_CHIFFON.getIndex()),
        LIGHT_BLUE_TWO(IndexedColors.LIGHT_BLUE.getIndex()),
        LIGHT_CORNFLOWER_BLUE(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()),
        LIGHT_GREEN(IndexedColors.LIGHT_GREEN.getIndex()),
        LIGHT_ORANGE(IndexedColors.LIGHT_ORANGE.getIndex()),
        LIGHT_TURQUOISE(IndexedColors.LIGHT_TURQUOISE.getIndex()),
        LIGHT_YELLOW(IndexedColors.LIGHT_YELLOW.getIndex()),
        LIME(IndexedColors.LIME.getIndex()),
        MAROON(IndexedColors.MAROON.getIndex()),
        OLIVE_GREEN(IndexedColors.OLIVE_GREEN.getIndex()),
        ORANGE(IndexedColors.ORANGE.getIndex()),
        ORCHID(IndexedColors.ORCHID.getIndex()),
        PALE_BLUE(IndexedColors.PALE_BLUE.getIndex()),
        PINK(IndexedColors.PINK.getIndex()),
        PLUM(IndexedColors.PLUM.getIndex()),
        RED(IndexedColors.RED.getIndex()),
        ROSE(IndexedColors.ROSE.getIndex()),
        ROYAL_BLUE(IndexedColors.ROYAL_BLUE.getIndex()),
        SEA_GREEN(IndexedColors.SEA_GREEN.getIndex()),
        SKY_BLUE(IndexedColors.SKY_BLUE.getIndex()),
        TAN(IndexedColors.TAN.getIndex()),
        TEAL(IndexedColors.TEAL.getIndex()),
        TURQUOISE(IndexedColors.TURQUOISE.getIndex()),
        VIOLET(IndexedColors.VIOLET.getIndex()),
        WHITE(IndexedColors.WHITE.getIndex()),
        YELLOW(IndexedColors.YELLOW.getIndex());
        private final short value;

        Colour(short value) {
            this.value = value;
        }
        public short value() {
            return this.value;
        }

    }

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    Type type() default Type.ALL;


    public enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    public enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}