package com.welltech.water.equipmentreceiver.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    private static Long timeFrom;

    static {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            timeFrom = df.parse("2010-01-01 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int calcNowTime(){
        Date date = new Date();
        Long dif = (date.getTime() - timeFrom) / 1000;
        return dif.intValue();
    }

    public static Date getTime(int dif){
        Long difLong = (long)dif;
        Long timestamp = timeFrom + 1000 * difLong;
        return new Date(timestamp);
    }

    public static String getYearMonth(){
        DateFormat df = new SimpleDateFormat("yyyyMM");
        return df.format(new Date());
    }

    public static Date parseFullTime(String dateStr){
        Date result = null;
        if(StringUtils.isNotBlank(dateStr)) {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                result = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String printDate(Date date) {
        if (date == null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

}
