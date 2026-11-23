package com.henry.common.utils.array;

/**
 * 数组查找。
 * 已发布的 1.0.4 没有这个类。不使用 Android API。
 * {@code CollectionUtil.isEmpty(Object[])} 仍然可用，本类另有同名方法，两者都不修改对方。
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
     * 判断对象数组是否没有元素。
     * data 为 null 或长度为 0 时返回 true。含 null 元素但长度大于 0 时返回 false。
     * 不修改 {@code CollectionUtil.isEmpty(Object[])}，也不修改 {@link #isEmpty(int[])}。
     *
     * @param data 允许为 null
     * @return data 为 null 或长度为 0 时返回 true
     */
    public static boolean isEmpty(Object[] data) {
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
     * 查找对象数组中最后一个匹配元素的下标。
     * data 为 null 或没有匹配时返回 -1。比较规则与 {@link #indexOf(Object[], Object)} 相同。
     *
     * @param data  允许为 null
     * @param value 允许为 null
     * @return 最后一处匹配下标；未找到时返回 -1
     */
    public static int lastIndexOf(Object[] data, Object value) {
        if (data == null) {
            return -1;
        }
        for (int i = data.length - 1; i >= 0; i--) {
            if (value == null ? data[i] == null : value.equals(data[i])) {
                return i;
            }
        }
        return -1;
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

    /**
     * 查找 int 数组中最后一个等于 value 的下标。
     * data 为 null 或没有匹配时返回 -1。
     * 不修改 {@link #indexOf(int[], int)}，那个方法返回的是第一处匹配。
     *
     * @param data  允许为 null
     * @param value 要查找的值
     * @return 最后一处匹配下标；未找到时返回 -1
     */
    public static int lastIndexOf(int[] data, int value) {
        if (data == null) {
            return -1;
        }
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 拼接两个 int 数组，返回新数组。
     * 任一侧为 null 时按空数组处理。不修改传入的数组。
     * 两侧都是 null 时返回长度为 0 的新数组，不返回 null。
     * 两段长度之和超出 int 时抛出 {@link IllegalArgumentException}。
     * 不修改 {@link #isEmpty(int[])}。
     *
     * @param left  左段，允许为 null
     * @param right 右段，允许为 null
     * @return 新的拼接结果
     */
    public static int[] concat(int[] left, int[] right) {
        int leftLength = left == null ? 0 : left.length;
        int rightLength = right == null ? 0 : right.length;
        if (leftLength > Integer.MAX_VALUE - rightLength) {
            throw new IllegalArgumentException("concat length overflows");
        }
        int[] out = new int[leftLength + rightLength];
        if (leftLength > 0) {
            System.arraycopy(left, 0, out, 0, leftLength);
        }
        if (rightLength > 0) {
            System.arraycopy(right, 0, out, leftLength, rightLength);
        }
        return out;
    }
}
