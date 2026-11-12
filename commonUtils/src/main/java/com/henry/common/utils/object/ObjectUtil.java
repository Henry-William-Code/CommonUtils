package com.henry.common.utils.object;

/**
 * 对象的 null 安全判断。
 * 已发布的 1.0.3 没有这个类。不使用 Android API。
 * 字符串内容比较仍使用 {@code StringUtil.equals}，本类的 equals 按 {@link Object#equals(Object)} 比较任意对象。
 */
public class ObjectUtil {

    private ObjectUtil() {
    }

    /**
     * value 为 null 时返回 defaultValue，否则返回 value。
     * defaultValue 本身可以是 null。此时 value 为 null 也会返回 null，无法区分“原值就是 null”和“使用了默认值”。
     *
     * @param value        原值，允许为 null
     * @param defaultValue 原值为 null 时返回的值，允许为 null
     * @param <T>          值类型
     * @return value 或 defaultValue
     */
    public static <T> T defaultIfNull(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 比较两个对象是否相等。
     * 两者都为 null 时返回 true；只有一方为 null 时返回 false。
     * 其余情况调用 {@code left.equals(right)}，不比较数组内容。两个不同的数组对象即使元素相同也返回 false。
     *
     * @param left  允许为 null
     * @param right 允许为 null
     * @return 相等或同为 null 时返回 true
     */
    public static boolean equals(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }

    /**
     * 返回参数中第一个不是 null 的值。
     * values 本身为 null、没有参数，或全部元素都是 null 时返回 null。
     * 不调用元素的 equals。
     *
     * @param values 候选值，数组本身允许为 null
     * @param <T>    值类型
     * @return 第一个非 null 值；没有时返回 null
     */
    @SafeVarargs
    public static <T> T firstNonNull(T... values) {
        if (values == null) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                return values[i];
            }
        }
        return null;
    }
}
