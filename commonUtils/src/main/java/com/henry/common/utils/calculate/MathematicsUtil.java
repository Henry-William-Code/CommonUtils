package com.henry.common.utils.calculate;

/**
 * =============================================
 * 时间：2021-06-28 19:21 </br>
 * 作者：龚鹏</br>
 * 描述：数学相关的计算类
 * ============================================
 */
public class MathematicsUtil {



    /**
     * 数学中次幂计算
     *
     * @param number 被计算的数据
     * @param times  次幂的大小
     * @return
     */
    public static int power(int number, int times) {
        if (times < 1) {
            return 0;
        } else if (times == 1) {
            return number;
        } else {
            return number * power(number, times - 1);
        }
    }

    /**
     * 把 value 限制在闭区间 [min, max] 内。
     * min 大于 max 时抛出 {@link IllegalArgumentException}，不会交换两个边界。
     *
     * @param value 原值
     * @param min   下界，包含
     * @param max   上界，包含
     * @return value 小于 min 时返回 min，大于 max 时返回 max，否则返回 value
     */
    public static int clamp(int value, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min is greater than max");
        }
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * 计算最大公约数，结果为非负整数。
     * 负数按绝对值参与计算。gcd(0, 0) 返回 0。
     * 任一参数为 {@link Integer#MIN_VALUE} 时抛出 {@link IllegalArgumentException}，
     * 因为该值的绝对值无法用 int 表示。
     *
     * @param a 整数
     * @param b 整数
     * @return 非负的最大公约数
     */
    public static int gcd(int a, int b) {
        if (a == Integer.MIN_VALUE || b == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("gcd argument out of range");
        }
        int x = Math.abs(a);
        int y = Math.abs(b);
        while (y != 0) {
            int remainder = x % y;
            x = y;
            y = remainder;
        }
        return x;
    }

    /**
     * 计算最小公倍数，结果为非负整数。
     * 任一参数为 0 时返回 0，包括 lcm(0, 0)。负数按绝对值计算。
     * 任一参数为 {@link Integer#MIN_VALUE}，或结果大于 {@link Integer#MAX_VALUE} 时，
     * 抛出 {@link IllegalArgumentException}。
     *
     * @param a 整数
     * @param b 整数
     * @return 非负的最小公倍数
     */
    public static int lcm(int a, int b) {
        if (a == Integer.MIN_VALUE || b == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("lcm argument out of range");
        }
        if (a == 0 || b == 0) {
            return 0;
        }
        int divisor = gcd(a, b);
        long result = (Math.abs((long) a) / divisor) * Math.abs((long) b);
        if (result > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("lcm overflow");
        }
        return (int) result;
    }
}
