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
}