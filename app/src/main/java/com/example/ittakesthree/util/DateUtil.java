package com.example.ittakesthree.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** 日期格式化工具类 */
public class DateUtil {

    public static String dateToString(Date date)
    {
        if(date == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format.format(date);
    }

    public static Date stringToDate(String date) throws ParseException {
        if(date == null || date.length() == 0)
            return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format.parse(date);
    }
}
