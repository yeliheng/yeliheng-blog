package com.yeliheng.blogcommon.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateUtils {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String UNSIGNED_DATETIME_PATTERN = "yyyyMMddHHmmss";

    public static final String UNSIGNED_DATE_PATTERN = "yyyyMMdd";

    /**
     * 获取当前日期和时间字符串.
     *
     * @return 日期时间字符串
     */
    public static String getLocalDateTimeStr() {
        return format(LocalDateTime.now(), DATETIME_PATTERN);
    }

    /**
     * 获取当前日期和时间
     *
     * @return 日期时间
     */
    public static Date getLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        return Date.from(LocalDateTime.parse(getLocalDateTimeStr(), dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取无符号的当前日期和时间字符串.
     *
     * @return 无符号日期时间字符串
     */
    public static String getUnsignedLocalDateTimeStr() {
        return format(LocalDateTime.now(), UNSIGNED_DATETIME_PATTERN);
    }

    /**
     * 获取当前日期字符串.
     *
     * @return 日期字符串
     */
    public static String getLocalDateStr() {
        return format(LocalDate.now(), DATE_PATTERN);
    }

    /**
     * 获取当前时间字符串.
     *
     * @return 时间字符串
     */
    public static String getLocalTimeStr() {
        return format(LocalTime.now(), TIME_PATTERN);
    }

    /**
     * 获取日期时间字符串
     *
     * @param temporal 需要转化的日期时间
     * @param pattern  时间格式
     * @return 目标字符串
     */
    public static String format(TemporalAccessor temporal, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(temporal);
    }

    /**
     * 将Date转换为yyyyMMdd_HHmmss格式的字符串
     */
    public static String dateToUnsignedString(Date date) {
        return new SimpleDateFormat(UNSIGNED_DATETIME_PATTERN).format(date);
    }

    /**
     * 将Date转换为yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String dateToDateTimeString(Date date) {
        return new SimpleDateFormat(DATETIME_PATTERN).format(date);
    }


}
