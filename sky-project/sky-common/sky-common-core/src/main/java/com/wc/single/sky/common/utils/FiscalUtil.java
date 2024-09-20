package com.wc.single.sky.common.utils;

import com.wc.single.sky.common.exception.QdpException;

import java.util.Calendar;
import java.util.Date;

public class FiscalUtil {


    /**
     * @Description 获取日期的财年
     *
     * @Author sangsenlin@foreveross.com
     * @Date 2019/9/5 11:22
     * @Param [date]
     * @return java.lang.String FY2223
     **/
    public static String getFiscalYear(Date date){
        int year = getYear(date);
        int month = getMonth(date);
        boolean subFlag = (month == Calendar.APRIL) || month == Calendar.FEBRUARY || month == Calendar.MARCH;
        if (subFlag){
            year --;
        }
        String yearStr = year + "";
        String yearNextStr = (Integer.valueOf(yearStr)+1) + "";
        return "FY" + StringUtils.substring(yearStr, yearStr.length()-2, yearStr.length())
                +StringUtils.substring(yearNextStr, yearNextStr.length()-2, yearNextStr.length());
    }

    public static String getFiscalYearWithOutFY(Date date) {
        int year = getYear(date);
        int month = getMonth(date);
        boolean subFlag = (month == Calendar.APRIL) || month == Calendar.FEBRUARY || month == Calendar.MARCH;
        if (subFlag) {
            year--;
        }
        String yearStr = year + "";
        String yearNextStr = (Integer.valueOf(yearStr) + 1) + "";
        return StringUtils.substring(yearStr, yearStr.length() - 2, yearStr.length())
                + StringUtils.substring(yearNextStr, yearNextStr.length() - 2, yearNextStr.length());
    }

    /**
     * @Description 获取日期的财季
     *
     * @Author sangsenlin@foreveross.com
     * @Date 2019/9/5 11:22
     * @Param [date]
     * @return java.lang.String
     **/
    public static String getFiscalQuarter(Date date){
        int month = getMonth(date);
        String quarterStr = "Q";
        boolean firstQuarter = (month == Calendar.APRIL) || month == Calendar.FEBRUARY || month == Calendar.MARCH;
        if (firstQuarter){
            return quarterStr + 4;
        }

        boolean secondQuarter = (month == Calendar.MAY) || month == Calendar.JUNE || month == Calendar.JULY;
        if (secondQuarter){
            return quarterStr + 1;
        }

        boolean threeQuarter = (month == Calendar.AUGUST) || month == Calendar.SEPTEMBER || month == Calendar.OCTOBER;
        if (threeQuarter){
            return quarterStr + 2;
        }

        return quarterStr + 3;
    }

    /*
    * 用于posting fy
    * 如
    * 2021-11-05 则返回2021
    * 2022-01-05 则返回2021
    * 2022-04-05 则返回2022
    * 2022-08-05 则返回2022
    * */
    public static String getFiscalYearCalendar(Date date){
        int year = getYear(date);
        int month = getMonth(date);
        boolean subFlag = (month == Calendar.APRIL) || month == Calendar.FEBRUARY || month == Calendar.MARCH;
        if (subFlag){
            year --;
        }
        return year + "";
    }


    /**
     * @Description 处理财年和财年季度规则,匹配对应的reviewFlag
     * @param quarter
     * @return
     */
    public static String getLastQuarter(String quarter){
        // 若为Q1,则上一季度为Q4,同时年度为FY1819;
        String afterQuarter="Q1";
        if(quarter.equals("Q1")){
            afterQuarter="Q4";
        }else{
            // 季度减1,年份不变
            String  quarterNumber=quarter.substring(1,2);
            Integer quarterNumberAfter=Integer.parseInt(quarterNumber)-1;
            afterQuarter="Q"+quarterNumberAfter;
        }
        return afterQuarter;
    }
    /***
     * @Description    根据季度, 换算年份;
     * @param year
     * @param quarter
     **/
    public static String getLastYear(String year, String quarter){
        // 若为Q1,则上一季度为Q4
        Integer yearInt=Integer.parseInt(year);
        if(quarter.equals("Q1")){
            yearInt--;
        }
        return yearInt.toString();
    }

    public static void main(String[] args) {
        System.out.println(getLastYearByFiscalYear("FY2223","Q3"));
        System.out.println(getFiscalYear(new Date()));
        System.out.println(getFiscalYear(new Date(1630823262000L))); //2021-09-05 14:27:42
        System.out.println(getFiscalYear(new Date(1638685662000L))); //2021-12-05 14:27:42
        System.out.println(getFiscalYear(new Date(1644042462000L))); //2022-02-05 14:27:42
        System.out.println(getFiscalYear(new Date(1648794462000L))); //2022-04-01 14:27:42
    }


    /***
     * @Description    根据FY2223, 获取上一财年 如FY2122;
     * @param fy
     * @param fq
     **/
    public static String getLastYearByFiscalYear(String fy, String fq){
        // 若为Q1,则上一季度为Q4
        String fyBegin = fy.substring(2, 4);
        Integer yearInt=Integer.parseInt(fyBegin);
        yearInt--;
        if(fq.equals("Q1")){
            fy= "FY"+(yearInt--)+fyBegin;
        }
        return fy;
    }


    /**
     * 获取日期时间的年份，如2017-02-13，返回2017
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期时间的月份，如2017年2月13日，返回2
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static Date getLastDayOfQuarter(int year, int quarter) {
        int lastMonth = quarter * 3 - 1;
        // 根据月获取开始时间
        // 根据月获取开始时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, lastMonth);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();

    }

    public static String getFiscalYearByClaim(String claimNumber) {
        String[] claimParams = claimNumber.split("_");
        if(claimParams.length!=4){
            throw new QdpException("claimNumber error!"+ claimNumber);
        }
        String year = claimParams[0];
        String quarter = claimParams[1].replace("Q", "");
        Date lastDayOfQuarter = getLastDayOfQuarter(Integer.parseInt(year), Integer.parseInt(quarter));
        return getFiscalYearWithOutFY(lastDayOfQuarter);
    }
}
