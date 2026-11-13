package com.henry.common.utils.time;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间：2019-08-23 14:29 <br>
 * 作者：龚鹏<br>
 * 描述：时间处理工具类
 */
public class TimeUtil {


    /**
     * 根据时间值获取时间格式
     *
     * @param format
     * @param time
     * @return
     */
    public static String getFormatTime(String format, Date time) {
        return getFormatTime(format, time.getTime());
    }

    /**
     * 根据毫秒值获取时间格式
     *
     * @param format
     * @param time
     * @return
     */
    public static String getFormatTime(String format, String time) {
        try {
            return getFormatTime(format, Long.parseLong(time));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 根据毫秒值获取时间格式
     *
     * @param format
     * @param time
     * @return
     */
    public static String getFormatTime(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 是否包含当前时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isContainsTime(String startTime, String endTime) {
        try {
            if (Long.parseLong(startTime) < System.currentTimeMillis() && System.currentTimeMillis() < Long.parseLong(endTime)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 是否大于结束时间
     *
     * @param endTime
     * @param endTime
     * @return
     */
    public static boolean isAfterEndTime(String endTime) {
        if(TextUtils.isEmpty(endTime)) {
            return true;
        }

        try {
            if (System.currentTimeMillis() < Long.parseLong(endTime)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


    /**
     * 文本字符串转为时间类型
     * @param format
     * @param dateText
     * @return
     */
    public static Date stringSwitchDate(String format, String dateText) {
        try{
            return new SimpleDateFormat(format).parse(dateText);
        }catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }

    }

    /**
     * 计算两个毫秒时间戳相差的整天数。
     * 以 86400000 毫秒为 1 天做整数除法，向 0 截断，不按设备时区折算日历日期。
     * endMillis 小于 startMillis 时结果为负数。
     * 两个时间戳的差值无法用 long 表示时抛出 {@link ArithmeticException}。
     *
     * @param startMillis 开始时间，毫秒
     * @param endMillis   结束时间，毫秒
     * @return 相差的整天数
     */
    public static long daysBetween(long startMillis, long endMillis) {
        long diff = endMillis - startMillis;
        if (((endMillis ^ startMillis) & (endMillis ^ diff)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return diff / 86400000L;
    }

    /**
     * 在毫秒时间戳上增加或减少整天。
     * 一天按 86400000 毫秒计算，不使用设备时区，也不处理夏令时。
     * days 可以为负数或 0。days 先提升为 long 再乘以一天的毫秒数，这个乘法不会溢出。
     * 相加的结果无法用 long 表示时抛出 {@link ArithmeticException}。
     *
     * @param millis 起始毫秒
     * @param days   增加的天数，负数表示向前
     * @return 增加后天的毫秒时间戳
     */
    public static long addDays(long millis, int days) {
        long delta = (long) days * 86400000L;
        long sum = millis + delta;
        if (((millis ^ sum) & (delta ^ sum)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return sum;
    }

    /**
     * 在毫秒时间戳上增加或减少整小时。
     * 一小时按 3600000 毫秒计算，不使用设备时区，也不处理夏令时。
     * hours 可以为负数或 0。hours 先提升为 long 再乘以一小时的毫秒数，这个乘法不会溢出。
     * 相加的结果无法用 long 表示时抛出 {@link ArithmeticException}。
     * 不代替 {@link #addDays(long, int)}。
     *
     * @param millis 起始毫秒
     * @param hours  增加的小时数，负数表示向前
     * @return 增加后的毫秒时间戳
     */
    public static long addHours(long millis, int hours) {
        long delta = (long) hours * 3600000L;
        long sum = millis + delta;
        if (((millis ^ sum) & (delta ^ sum)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return sum;
    }

    /**
     * 在毫秒时间戳上增加或减少整分钟。
     * 一分钟按 60000 毫秒计算，不使用设备时区，也不处理夏令时。
     * minutes 可以为负数或 0。minutes 先提升为 long 再乘以一分钟的毫秒数，这个乘法不会溢出。
     * 相加的结果无法用 long 表示时抛出 {@link ArithmeticException}。
     * 不代替 {@link #addHours(long, int)} 或 {@link #addDays(long, int)}。
     *
     * @param millis  起始毫秒
     * @param minutes 增加的分钟数，负数表示向前
     * @return 增加后的毫秒时间戳
     */
    public static long addMinutes(long millis, int minutes) {
        long delta = (long) minutes * 60000L;
        long sum = millis + delta;
        if (((millis ^ sum) & (delta ^ sum)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return sum;
    }

    /**
     * 在毫秒时间戳上增加或减少整秒。
     * 一秒按 1000 毫秒计算，不使用设备时区，也不处理夏令时。
     * seconds 可以为负数或 0。seconds 先提升为 long 再乘以一秒的毫秒数，这个乘法不会溢出。
     * 相加的结果无法用 long 表示时抛出 {@link ArithmeticException}。
     * 不代替 {@link #addMinutes(long, int)}、{@link #addHours(long, int)} 或 {@link #addDays(long, int)}。
     *
     * @param millis  起始毫秒
     * @param seconds 增加的秒数，负数表示向前
     * @return 增加后的毫秒时间戳
     */
    public static long addSeconds(long millis, int seconds) {
        long delta = (long) seconds * 1000L;
        long sum = millis + delta;
        if (((millis ^ sum) & (delta ^ sum)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return sum;
    }

    /**
     * 判断两个毫秒时间戳是否落在同一个 UTC 日。
     * 日界是 {@code floor(millis / 86400000)}，不是设备本地日历，也不是 Java 整数除法的向 0 截断。
     * 因此纪元前不足一天的时间不会和 1970-01-01 算成同一天：{@code isSameDay(-1L, 0L)} 为 false。
     *
     * @param millisA 毫秒时间戳
     * @param millisB 毫秒时间戳
     * @return 同一个 UTC 日时返回 true
     */
    public static boolean isSameDay(long millisA, long millisB) {
        return utcDayIndex(millisA) == utcDayIndex(millisB);
    }

    /**
     * 返回 millis 所在 UTC 日的起始毫秒。
     * 日界与 {@link #isSameDay(long, long)} 相同，使用 {@code floor(millis / 86400000)}，不是设备时区。
     * 结果是 86400000 的整数倍。{@link Long#MIN_VALUE} 所在 UTC 日的起点小于 long 最小值，因此抛出 {@link ArithmeticException}。
     *
     * @param millis 毫秒时间戳
     * @return 该 UTC 日 00:00:00.000 的毫秒值
     */
    public static long startOfUtcDay(long millis) {
        long day = utcDayIndex(millis);
        long start = day * 86400000L;
        if (day != 0 && start / day != 86400000L) {
            throw new ArithmeticException("long overflow");
        }
        return start;
    }

    /**
     * 返回 millis 所在 UTC 日的最后一毫秒。
     * 日界与 {@link #startOfUtcDay(long)} 相同。结果等于该日起点再加 86399999 毫秒。
     * {@link Long#MIN_VALUE} 的日起点无法表示，{@link Long#MAX_VALUE} 所在日的终点大于 long 最大值，这两种情况都抛出 {@link ArithmeticException}。
     *
     * @param millis 毫秒时间戳
     * @return 该 UTC 日 23:59:59.999 的毫秒值
     */
    public static long endOfUtcDay(long millis) {
        long start = startOfUtcDay(millis);
        long delta = 86399999L;
        long end = start + delta;
        if (((start ^ end) & (delta ^ end)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return end;
    }

    private static long utcDayIndex(long millis) {
        long day = millis / 86400000L;
        if (millis < 0 && millis % 86400000L != 0) {
            day--;
        }
        return day;
    }
}