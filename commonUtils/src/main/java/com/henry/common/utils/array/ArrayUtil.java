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

    /**
     * 拼接两个对象数组，返回新的 {@code Object[]}。
     * 任一侧为 null 时按空数组处理。两侧都是 null 时返回长度为 0 的新数组，不返回 null。
     * 不修改传入的数组。长度之和超出 int 时抛出 {@link IllegalArgumentException}。
     * 结果始终是 {@code Object[]}，不按元素的运行时类型再包装成 {@code String[]} 或其他数组类型。
     * 两个参数都是 null 时，本方法与 {@link #concat(int[], int[])} 都匹配，调用处需要强制转换。
     * 不修改 {@link #concat(int[], int[])}。
     *
     * @param left  左段，允许为 null
     * @param right 右段，允许为 null
     * @return 新的 Object[]
     */
    public static Object[] concat(Object[] left, Object[] right) {
        int leftLength = left == null ? 0 : left.length;
        int rightLength = right == null ? 0 : right.length;
        if (leftLength > Integer.MAX_VALUE - rightLength) {
            throw new IllegalArgumentException("concat length overflows");
        }
        Object[] out = new Object[leftLength + rightLength];
        if (leftLength > 0) {
            System.arraycopy(left, 0, out, 0, leftLength);
        }
        if (rightLength > 0) {
            System.arraycopy(right, 0, out, leftLength, rightLength);
        }
        return out;
    }

    /**
     * 返回 int 数组的一段，结果是新数组。
     * data 为 null 时返回 null。不修改 data。
     * 要求 {@code 0 <= startInclusive <= endExclusive <= data.length}，否则抛出 {@link IndexOutOfBoundsException}。
     * 起止相等时返回长度为 0 的新数组。endExclusive 超过长度时不补 0。
     *
     * @param data           原数组，允许为 null
     * @param startInclusive 起始下标，包含
     * @param endExclusive   结束下标，不包含
     * @return 新数组；data 为 null 时返回 null
     */
    public static int[] subarray(int[] data, int startInclusive, int endExclusive) {
        if (data == null) {
            return null;
        }
        if (startInclusive < 0 || endExclusive < startInclusive || endExclusive > data.length) {
            throw new IndexOutOfBoundsException("subarray range is invalid");
        }
        int[] out = new int[endExclusive - startInclusive];
        if (out.length > 0) {
            System.arraycopy(data, startInclusive, out, 0, out.length);
        }
        return out;
    }

    /**
     * 返回对象数组的一段，结果是新的 {@code Object[]}。
     * 规则与 {@link #subarray(int[], int, int)} 相同。
     * data 为 null 时返回 null。不修改 data，也不补 null。
     * 要求 {@code 0 <= startInclusive <= endExclusive <= data.length}，否则抛出 {@link IndexOutOfBoundsException}。
     * 起止相等时返回长度为 0 的新数组。结果的运行时类型始终是 {@code Object[]}，即使入参是 {@code String[]}。
     * 传入 {@code null} 字面量时与 {@code int[]} 重载存在歧义，调用处需要强制转换。这与 {@link #isEmpty(Object[])} 相同。
     * 不修改 {@link #subarray(int[], int, int)}。
     *
     * @param data           原数组，允许为 null
     * @param startInclusive 起始下标，包含
     * @param endExclusive   结束下标，不包含
     * @return 新的 {@code Object[]}；data 为 null 时返回 null
     */
    public static Object[] subarray(Object[] data, int startInclusive, int endExclusive) {
        if (data == null) {
            return null;
        }
        if (startInclusive < 0 || endExclusive < startInclusive || endExclusive > data.length) {
            throw new IndexOutOfBoundsException("subarray range is invalid");
        }
        Object[] out = new Object[endExclusive - startInclusive];
        if (out.length > 0) {
            System.arraycopy(data, startInclusive, out, 0, out.length);
        }
        return out;
    }

    /**
     * 判断 int 数组是否以 prefix 开头。
     * 规则与 {@code BytesUtil.startsWith(byte[], byte[])} 对称。
     * data 或 prefix 为 null 时返回 false。prefix 为空数组且 data 不是 null 时返回 true，包括 data 也是空数组。
     * prefix 比 data 长时返回 false。不修改入参。
     * 目前只有 {@code int[]} 这一个重载，{@code null} 字面量不会和 {@code Object[]} 冲突。
     *
     * @param data   原数组，允许为 null
     * @param prefix 前缀，允许为 null
     * @return 以前缀开头时返回 true
     */
    public static boolean startsWith(int[] data, int[] prefix) {
        if (data == null || prefix == null) {
            return false;
        }
        if (prefix.length > data.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
