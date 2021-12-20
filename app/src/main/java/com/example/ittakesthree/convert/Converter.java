package com.example.ittakesthree.convert;

import androidx.room.TypeConverter;

import java.util.Date;

/** 数据库类型转换器 */
public class Converter {

    @TypeConverter
    public static Date fromTimestamp(Long value)
    {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long datatoTimestamp(Date date)
    {
        return date == null ? null : date.getTime();
    }


}
