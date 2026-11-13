package com.henry.common.utils.array;

/**
 * 数组查找。
 * 已发布的 1.0.4 没有这个类。不使用 Android API。
 * 对象数组是否为空仍使用 {@code CollectionUtil.isEmpty(Object[])}，本类不提供该方法。
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    /**
     * 判断 int 数组是否没有元素。
     *
     * @param data 允许为 null
     * @return data 为 null 或长度为 0 时返回 true
     */
    public static boolean isEmpty(int[] data) {
        return data == null || data.length == 0;
    }

    /**
     * 查找对象数组中第一个匹配元素的下标。
     * data 为 null 或没有匹配时返回 -1。
     * value 允许为 null；元素和 value 都为 null 时算匹配。
     * 比较使用 {@link Object#equals(Object)}，元素本身是数组时只按引用相等，不比较数组内容。
     *
     * @param data  允许为 null
     * @param value 允许为 null
     * @return 第一个匹配下标；未找到时返回 -1
     */
    public static int indexOf(Object[] data, Object value) {
        if (data == null) {
            return -1;
        }
        for (int i = 0; i < data.length; i++) {
            if (value == null ? data[i] == null : value.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断对象数组是否包含指定元素。规则与 {@link #indexOf(Object[], Object)} 相同。
     *
     * @param data  允许为 null
     * @param value 允许为 null
     * @return 找到时返回 true
     */
    public static boolean contains(Object[] data, Object value) {
        return indexOf(data, value) >= 0;
    }

    /**
     * 查找 int 数组中第一个等于 value 的下标。
     * data 为 null 或没有匹配时返回 -1。
     *
     * @param data  允许为 null
     * @param value 要查找的值
     * @return 第一个匹配下标；未找到时返回 -1
     */
    public static int indexOf(int[] data, int value) {
        if (data == null) {
            return -1;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断 int 数组是否包含指定值。
     * data 为 null 时返回 false。
     *
     * @param data  允许为 null
     * @param value 要查找的值
     * @return 找到时返回 true
     */
    public static boolean contains(int[] data, int value) {
        return indexOf(data, value) >= 0;
    }
}
