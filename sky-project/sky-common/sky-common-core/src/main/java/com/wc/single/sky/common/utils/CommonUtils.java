package com.wc.single.sky.common.utils;

import com.wc.single.sky.common.exception.QdpException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
  * @Description
  * @author liuhuayong
  * @date 2019/12/20 10:04
  */
public  class CommonUtils {

    public static void isNullList(@Nullable List<?> list, String message)  {
        if(null==list || list.size()==0){
            throw new RuntimeException(message);
        }
    }
    public static boolean isNullList(@Nullable List<?> list)  {
        if(null==list || list.size()==0){
            return true;
        }
        return false;
    }

    public static void  stringIsEmpty(@Nullable Object object, String message) {
        if (StringUtils.isEmpty(object)) {
            throw new QdpException(message);
        }
    }

    public static void stringIsEmpty(@Nullable Object object, String message, int code) {
        if (StringUtils.isEmpty(object)) {
            throw new QdpException(message, code);
        }
    }

    public static void stringIsNotEmpty(@Nullable Object object, String message) {
        if (!StringUtils.isEmpty(object)) {
            throw new QdpException(message);
        }
    }

    public static void integerEmpty(@Nullable Integer integer, String message) {
        if (null == integer || integer == 0) {
            throw new RuntimeException(message);
        }
    }

    public static boolean isNullIntegerEmpty(@Nullable Integer integer, String message) {
        if(null==integer || integer==0){
            return true;
        }
        return false;
    }

    public static boolean stringIsEmpty(@Nullable Object object){
        return StringUtils.isEmpty(object);
    }

    public static boolean stringIsNotEmpty(@Nullable Object object) {
        return !StringUtils.isEmpty(object);
    }

    public static void isNull(@Nullable Object object, String message) {
        if (ObjectUtils.isEmpty(object)) {
            throw new QdpException(message);
        }
    }

    public static void isNull(@Nullable Object object, String message, int code) {
        if (ObjectUtils.isEmpty(object)) {
            throw new QdpException(message, code);
        }
    }


    public static void collectIsEmpty(@Nullable Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty() || collection.size() == 0) {
            throw new RuntimeException(message);
        }
    }

    public static boolean isNullCollect(@Nullable Collection<?> collection) {
        if (collection == null || collection.isEmpty() || collection.size() == 0) {
            return true;
        }
        return  false;
    }
    public static boolean isNullObject(@Nullable Object object) {
        return ObjectUtils.isEmpty(object);
    }

    public static void isParamTure(@Nullable Boolean bl, String message) {
        if(bl){
            throw new RuntimeException(message);
        }
    }

    public static String[] insertStringArr(String[] arr, String str) {
        int size = arr.length;  //获取数组长度
        String[] tmp = new String[size + 1];  //新建临时字符串数组，在原来基础上长度加一
        for (int i = 0; i < size; i++){  //先遍历将原来的字符串数组数据添加到临时字符串数组
            tmp[i] = arr[i];
        }
        tmp[size] = str;  //在最后添加上需要追加的数据
        return tmp;  //返回拼接完成的字符串数组
    }


    /***
     * @Author liuhuayong
     * @Description  根据updateTime 跟当前服务器时间,判断是否超过48小时
     * @Date 2020/11/20 15:50
     * @param: updateTime
     * @return: boolean
     **/
    public static boolean validWithin24Hours(Date updateTime){
        boolean isValidBool=false;
        Date startDate=CommonUtils.getStartData24HourIgnoreTheWeekend(updateTime);
        // 判断当前服务器日期与startDate差值,是否相差24H( 1天 ),如果是,则返回true;否则false;
        Date currDate=new Date();
        if(currDate.compareTo(startDate)>=0){
            isValidBool=true;
        }
        return  isValidBool;
    }


    /***
     * @Author liuhuayong
     * @Description 判断 基于开始时间计算,往后延期48H,忽略周六周日;
     * @Date 2020/11/20 20:18
     * @param: currDate
     * @return: java.util.Date
     **/
    public static Date getStartData24HourIgnoreTheWeekend(Date currDate){
        Date startDate=currDate;
        // 判断今天是否是周四/周五/周六,如果是,时间推迟 4天( 忽略周六和周日)
        if(currDate.getDay()==5 || currDate.getDay()==6){
            startDate=DateUtils.dateAddDays(currDate,3);
        }
        // 判断今天是否是周日,如果是周日,时间推迟 4天( 忽略周日)
        else if(currDate.getDay()==0){
            startDate=DateUtils.dateAddDays(currDate,2);
        }// 判断今天是否是周日,如果是周日,时间推迟 4天( 忽略周日)
        else{
            startDate=DateUtils.dateAddDays(currDate,1);
        }
        return startDate;
    }

    /**
     * 获取忽略周末的开始日期。
     * 如果当前日期是周六，则将开始日期设置为下周一。
     * 如果当前日期是周日，则将开始日期设置为下周一。
     * 如果当前日期是周一至周五，则将开始日期设置为当前日期。
     * 返回开始日期的最后时刻。
     *
     * @param currDate 当前日期
     * @return 忽略周末后的开始日期的最后时刻
     */
    public static Date getStartDateIgnoreTheWeekend(Date currDate) {
        Date startDate=currDate;
        // 6是周六
        if(currDate.getDay()==6){
            startDate=DateUtils.dateAddDays(currDate,2);
        }
        // 0是周日
        else if(currDate.getDay()==0){
            startDate=DateUtils.dateAddDays(currDate,1);
        }
        else{
            startDate=DateUtils.dateAddDays(currDate,0);
        }
        //返回那天的最后时刻
        return dateToStringBeginOrEnd(startDate,false);
    }


    /*
    * 是周五六日 则变为下周一的最后时刻 23:59:59
    * 是周一二三四 则变为当天的最后时刻 23:59:59
    *
    * */
    public static Date getDeadTimeIgnoreTheWeekend(Date currDate) {
        Date startDate;
        // 5是周五
        if(currDate.getDay()==5){
            startDate=DateUtils.dateAddDays(currDate,3);
        }else if(currDate.getDay()==6){
            // 6是周六
            startDate=DateUtils.dateAddDays(currDate,2);
        }else if(currDate.getDay()==0){
            // 0是周日
            startDate=DateUtils.dateAddDays(currDate,1);
        }
        else{
            startDate=DateUtils.dateAddDays(currDate,0);
        }
        //返回那天的最后时刻 ,false代表取最后时刻
        return dateToStringBeginOrEnd(startDate,false);
    }


    public static Date dateToStringBeginOrEnd(Date date, Boolean flag) {
        Calendar calendar1 = Calendar.getInstance();
        //获取某一天的0点0分0秒 或者 23点59分59秒
        if (Boolean.TRUE.equals(flag)) {
            calendar1.setTime(date);
            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                    0, 0, 0);
            return calendar1.getTime();
        }else{
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                    23, 59, 59);
            return calendar1.getTime();
        }
    }



    /* 根据指定属性去重
    * 配合stream使用
    * 案例
    * list.stream().filter(CommonUtils.distinctByKey(BizBaseProfitCenter::getDummyGtnMtm)).collect(Collectors.toList());
    * */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
