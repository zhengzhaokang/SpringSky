package com.wc.single.sky.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author lovefamily
 */
@Log4j2
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String MM = "MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_YYYY_MM_DD_EN = "dd-MM-yyyy";

    public static final String DATETIME_FORMAT = "yyyyMMddHHmm";
    public static final String JAN = "01";
    public static final String APR = "04";
    public static final String JUL = "07";
    public static final String OCT = "10";

    private static String[] parsePatterns = {
            "MM/dd/yyyy", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyyMMdd",};

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_A_PATTERN = "d/MM/yyyy HH:mm:ss a";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_ONLY_NUMBERS_HHMM = "yyyyMMddHHmm";
    public static final String DATE_Space_PATTERN = "dd MMM yyyy";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String MONTH_PATTERN_YYYY = "yyyy";
    public static final String DATE_NO_PATTERN = "yyyyMMdd";
    public static final String DATE_EN_PATTERN = "dd-MMM-yyyy";
    public static final String DATE_ONLY_NUMBERS = "yyyyMMddHHmmss";

    public static final String QDP_DEFAULT_DATE_PATTERN = "MM/dd/yyyy";
    public static final String QDP_DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String QDP_DEFAULT_DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm:ss";

    public static final String MMDDYYYYHHMMSS = "MMddyyyyHHmmss";


    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM
     *
     * @return String
     */
    public static String getMonth() {
        return dateTimeNow(MM);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            log.error("时间格式化失败：{}", e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000L * 24L * 60L * 60L;
        long nh = 1000L * 60L * 60L;
        long nm = 1000L * 60L;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 时间加减月数
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param months    加减的月数
     * @return Date
     */
    public static Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return c.getTime();
    }

    /**
     * 时间格式化成字符串
     *
     * @param date    Date
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN， 如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 时间格式化成字符串 英文方式
     *
     * @param date    Date
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN， 如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String dateEnFormat(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return sdf.format(date);
    }


    /**
     * 获取当前时间 格式yyyyMMddHHmmss
     */
    public static String getLocalDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_ONLY_NUMBERS));
    }

    /**
     * @param format
     * @return java.lang.String
     * @Description 时间戳转日期格式
     * @author liuhuayong
     * @date 2021/5/26 11:42
     */
    public static String timestampParse(Long timestamp, String format, Locale english) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, english);
        String parseTime = sdf.format(new Date(timestamp));
        return parseTime;
    }

    public static boolean isNumericzidai(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 将日期时间格式成只有日期的字符串（可以直接使用dateFormat，Pattern为Null进行格式化）
     *
     * @param dateTime Date
     * @return
     * @throws ParseException
     */
    public static String dateTimeToDateString(Date dateTime) throws ParseException {
        String dateTimeString = DateUtils.dateFormat(dateTime, DateUtils.DATE_TIME_PATTERN);
        return dateTimeString.substring(0, 10);
    }

    /**
     * 字符串解析成时间对象
     *
     * @param dateTimeString String
     * @param pattern        StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN，如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date dateParse(String dateTimeString, String pattern) throws ParseException {
        if (StringUtils.isBlank(pattern)) {
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeString);
    }

    /**
     * 获取当前时间所属季度的第一天
     *
     * @param date
     * @return
     */
    public static Date getCurrentQuarterStartDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentMonth = calendar.get(Calendar.MONTH);
        //两个int相除，取整除部分，忽略余数
        calendar.set(Calendar.MONTH, currentMonth / 3 * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间所属季度的最后一天
     *
     * @param date
     * @return
     */
    public static Date getCurrentQuarterEndDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentMonth = calendar.get(Calendar.MONTH);
        //两个int相除，取整除部分，忽略余数
        calendar.set(Calendar.MONTH, currentMonth / 3 * 3 + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMinDate(Date a, Date b) {
        if (a == null) {
            a = new Date();
        }
        if (b == null) {
            b = new Date();
        }
        if (a.before(b)) {
            return a;
        }
        return b;
    }

    public static Date getMaxDate(Date a, Date b) {
        if (a == null) {
            a = new Date();
        }
        if (b == null) {
            b = new Date();
        }
        if (a.after(b)) {
            return a;
        }
        return b;
    }

    public static Date getDateZero(Date date) {

        Date parse = null;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DateFormatUtils.format(date, "yyyy-MM-dd 00:00:00"));
        } catch (ParseException e) {
            log.error("### getDateZero ParseException is ", e);
            return date;
        }
        return parse;
    }

    /**
     * 获得当日0点
     */
    public static Date getZero() throws ParseException {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));

    }

    /**
     * 获得当日最后时间
     */
    public static Date getEnd() throws ParseException {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));

    }

    /**
     * 获取当前时间所属季度
     *
     * @param date
     * @return
     */
    public static int getCurrentQuarter(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentMonth = calendar.get(Calendar.MONTH);
        //两个int相除，取整除部分，忽略余数
        return (currentMonth / 3 * 3 + 3) / 3;
    }

    public static int getCurrentYear(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    public static Date getMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(9999, Calendar.DECEMBER, calendar.getActualMaximum(Calendar.DECEMBER), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获取上一个月1号0点0分0秒的时间
     */
    public static String getBeforeFirstMonthdate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至00
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至00
        calendar.set(Calendar.MINUTE, 00);
        //将秒至00
        calendar.set(Calendar.SECOND, 00);
        String format1 = format.format(calendar.getTime());
        return format1;
    }

    /**
     * 获取上个月的最后一天23点59分59秒的时间
     */
    public static String getBeforeLastMonthdate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        String format = sf.format(calendar.getTime());
        return format;
    }

    /**
     * 获取当前月1号0点0分0秒的时间
     */
    public static String getFirstMonthdate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至00
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至00
        calendar.set(Calendar.MINUTE, 00);
        //将秒至00
        calendar.set(Calendar.SECOND, 00);
        String format1 = format.format(calendar.getTime());
        return format1;
    }

    /**
     * 获取上个月第一天
     *
     * @param month
     * @return
     */
    public static String getFirstDayOfLastMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        calendar.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        //将小时至00
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至00
        calendar.set(Calendar.MINUTE, 00);
        //将秒至00
        calendar.set(Calendar.SECOND, 00);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstDayDate = sdf.format(calendar.getTime());
        return firstDayDate;
    }


    /**
     * 获取上个月的最后一天23点59分59秒的时间
     */
    public static String getLastMonthdateStr() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)-1;
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        String format = sf.format(calendar.getTime());
        return format;
    }

    /**
     * 获取上个月的最后一天00点00分00秒的时间
     */
    public static Date getLastMonthdate() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 00);
        //将秒至59
        calendar.set(Calendar.SECOND, 00);
        String format = sf.format(calendar.getTime());
        return dateParse(format, DateUtils.DATE_TIME_PATTERN);
    }

    /**
     * 获取上一个月1号0点0分0秒的时间
     */
    public static String getLastQFirstMonthdateStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至00
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至00
        calendar.set(Calendar.MINUTE, 00);
        //将秒至00
        calendar.set(Calendar.SECOND, 00);
        String format1 = format.format(calendar.getTime());
        return format1;
    }

    /**
     * 获取上一个月1号0点0分0秒的时间
     */
    public static Date getLastQFirstMonthdate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至00
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至00
        calendar.set(Calendar.MINUTE, 00);
        //将秒至00
        calendar.set(Calendar.SECOND, 00);
        String format1 = format.format(calendar.getTime());
        return dateParse(format1, DateUtils.DATE_TIME_PATTERN);
    }




    /**
     * 当前季度开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前财季的开始时间
     *
     * @return
     */
    public static String getLastQStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, -3);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(cal.getTime());
    }

    /**
     * 当前财季的结束时间
     *
     * @return
     */
    public static String getLastQEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.SECOND, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(cal.getTime());
    }

    public static String getDateFormat(Date date, String formatStr) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(formatStr)) {
            return new SimpleDateFormat(formatStr).format(date);
        }
        return null;
    }


    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int dateBetweenIncludeToday(Date startDate, Date endDate) throws ParseException {
        return dateBetween(startDate, endDate) + 1;
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，不包含今天
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int dateBetween(Date startDate, Date endDate) throws ParseException {
        Date dateStart = dateParse(dateFormat(startDate, DATE_PATTERN), DATE_PATTERN);
        Date dateEnd = dateParse(dateFormat(endDate, DATE_PATTERN), DATE_PATTERN);
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
    }


    public static ArrayList<String> test(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
            // fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        System.out.println(result);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        System.out.println(result);
        return result;
    }


    /**
     * 获取过去的12个月
     */
    public static List<String> getPastTwelveYearMonth(Integer featureMonth) {
        if (null == featureMonth) {
            featureMonth = 0;
        }
        int numberOfMonths = 12; // 获取前6个月的月份
        String[] lastTwelveMonths = new String[numberOfMonths];
        Calendar calendar = Calendar.getInstance();
        //如果当前日期大于二月份的天数28天或者29天会导致计算月份错误，会多出一个三月份，故设置一个靠前日期解决此问题
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //实际月份+1
        featureMonth++;
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + featureMonth);
        for (int i = 0; i < numberOfMonths; i++) {
            if (calendar.get(Calendar.MONTH) != 0) {
                if (calendar.get(Calendar.MONTH) > 0 && calendar.get(Calendar.MONTH) < 10) {
                    lastTwelveMonths[11 - i] = calendar.get(Calendar.YEAR) + "0" + (calendar.get(Calendar.MONTH));
                } else {
                    lastTwelveMonths[11 - i] = calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH));
                }
            } else {
                lastTwelveMonths[11 - i] = calendar.get(Calendar.YEAR) - 1 + "" + 12;
            }
            //逐次往前推1个月
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        }
        return Arrays.asList(lastTwelveMonths);
    }

    public static List<String> getLastYear(int numberOfYears) {
        List<String> lastYears = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int i = 0; i < numberOfYears; i++) {
            lastYears.add(year - i + "");
        }
        Collections.reverse(lastYears);
        return lastYears;
    }

    /**
     * 格式化月份
     */
    public static String fillZero(int i) {
        String month = "";
        if (i < 10) {
            month = "0" + i;
        } else {
            month = String.valueOf(i);
        }
        return month;
    }

    public static List<String> get12Month(int year) {
        Calendar calendar = Calendar.getInstance();
        int cyear = calendar.get(Calendar.YEAR);
        int cmonth = calendar.get(Calendar.MONTH);
        int allYear = 12;
        if (cyear == year) {
            allYear = cmonth + 1;
        }
        List<String> monthList = new ArrayList<>();
        for (int i = 1; i <= allYear; i++) {
            monthList.add(year + "-" + fillZero(i));
        }
        return monthList;
    }

    /**
     * 获取传入日期第一天日期时间
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取传入日期最后一天日期时间
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static void main(String[] args) {
//        System.out.println(getLastQFirstMonthdateStr());//todo  错误
//        System.out.println(getBeforeFirstMonthdate());
//        System.out.println(getFirstMonthdate());
//        System.out.println(getLastMonthdateStr());
//        System.out.println(getMonth());

        //System.out.println(getCurrentQuarterStartTime());
        // System.out.println(getLastQStartTime());
        //System.out.println(getLastQEndTime());
        System.out.println(getLastMonthdateStr());
        try {


            String endTime = DateUtils.dateFormat(getLastDayOfMonth(DateUtils.dateParse("2023-10-12 23:00:00", DateUtils.DATE_TIME_PATTERN)), DateUtils.DATE_TIME_PATTERN);
            System.out.println(endTime );
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
