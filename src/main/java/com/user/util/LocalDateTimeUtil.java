package com.user.util;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * JDK8日期实用类
 *
 * TODO 待进一步完善，补充更多实用方法
 */
public class LocalDateTimeUtil {

    // 时间
    public static final String HHmm = "HHmm";

    // 日期
    public static final String ddMMM = "ddMMM";

    public static final String yyyyMM = "yyyyMM";

    public static final String ddMMyy = "ddMMyy";

    public static final String ddMMMyy = "ddMMMyy";

    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static final String YYYY_MM_dd = "YYYY-MM-dd";

    // 时间 + 日期
    public static final String yyyyMMdd_HHmm = "yyyyMMdd_HHmm";

    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";

    public static final String ddMMyyHHmm = "ddMMyyHHmm";

    public static final String yyyy_MM_ddHHmm = "yyyy-MM-dd HH:mm";

    public static final String dd_MMM_yyHHmm = "dd-MMM-yy HHmm";

    public static final String yyyyMMddHHmmss = "yyyyMMdd HH:mm:ss";

    public static final String yyyyMMdd_HH_mm_ss = "yyyyMMdd_HH_mm_ss";

    public static final String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static final String yyyy_MM_ddTHHmmss = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String yyyy_MM_ddHHmmssS = "yyyy-MM-dd HH:mm:ss.S";

    public static final String yyyy_MM_ddTHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String EEE_dd_MMM_yyyy_HHmmss_zzz = "EEE, dd MMM yyyy HH:mm:ss zzz";

    public static final String EEE_dd_MMM_yyyy_HHmmss = "EEE, dd MMM yyyy HH:mm:ss";

    private static final String UTC = "UTC";

    /**
     * 转时区
     *
     * @param localDateTime
     * @param originalZoneId
     * @param targetZoneId
     * @return
     */
    public static LocalDateTime convertDateTimezone(LocalDateTime localDateTime, ZoneId originalZoneId, ZoneId targetZoneId) {
        ZonedDateTime zoneDate = localDateTime.atZone(originalZoneId);
        ZonedDateTime utcDate = zoneDate.withZoneSameInstant(targetZoneId);
        return utcDate.toLocalDateTime();
    }

    public static LocalDateTime getUtcCurrentDateTime() {
        return convertDateTimezone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of(UTC));
    }

    /**
     * 把LocalDateTime转成String
     *
     * @param localDateTime
     * @param dateTimeFormat
     * @return
     */
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String dateTimeFormat) {
        return DateTimeFormatter.ofPattern(dateTimeFormat).format(localDateTime);
    }

    /**
     * 把LocalDate转成String
     *
     * @param localDate
     * @param dateFormat
     * @return
     */
    public static String formatLocalDateToString(LocalDate localDate, String dateFormat) {
        return DateTimeFormatter.ofPattern(dateFormat).format(localDate);
    }

    /**
     * 把Date String转成LocalDateTime
     *
     * @param stringDateTime
     * @param dateTimeFormat
     * @return
     */
    public static LocalDateTime parseStringToLocalDateTime(String stringDateTime, String dateTimeFormat) {
        if (stringDateTime == null) {
            return null;
        }
        return LocalDateTime.parse(stringDateTime, DateTimeFormatter.ofPattern(dateTimeFormat));
    }

    /**
     * 把date String转成LocalDate
     *
     * @param stringDateTime
     * @param dateFormat
     * @return
     */
    public static LocalDate parseStringToLocalDate(String stringDateTime, String dateFormat) {
        return LocalDate.parse(stringDateTime, DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * 获取两个Date String之间的小时数
     *
     * @param startDate
     * @param endDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static double getHoursBetweenDate(String startDate, String endDate, String format) throws ParseException {
        LocalDateTime t1 = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(format));
        LocalDateTime t2 = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern(format));
        Duration duration = Duration.between(t1, t2);
        double hours = duration.toHours();

        return hours;
    }

    /**
     * 获取两个日期的分钟数
     *
     * @param startDate
     * @param endDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static long getMinutesBetweenDate(String startDate, String endDate, String format) {
        LocalDateTime t1 = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(format));
        LocalDateTime t2 = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern(format));
        Duration duration = Duration.between(t1, t2);
        long minutes = duration.toMinutes();
        return minutes;
    }

    /**
     * Date转成LocalDateTime
     *
     * @param dateToConvert Date类型的日期
     * @return LocalDateTime类型的日期
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        if (null == dateToConvert) {
            return null;
        }
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转成Date
     *
     * @param dateToConvert
     * @return
     */
    public static Date convertToDate(LocalDateTime dateToConvert) {
        if (null == dateToConvert) {
            return null;
        }
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

}
