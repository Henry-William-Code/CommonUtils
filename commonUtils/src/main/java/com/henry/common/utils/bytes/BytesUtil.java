package com.henry.common.utils.bytes;

/**
 * byte 数组的空判断和拼接。
 * 不处理流，也不代替 {@code ArrayUtil} 的 int 或 Object 数组方法。
 * 不使用 Android API。
 */
public class BytesUtil {

    private BytesUtil() {
    }

    /**
     * 判断 byte 数组是否没有内容。
     * data 为 null 或长度为 0 时返回 true。
     *
     * @param data 允许为 null
     * @return 没有内容时返回 true
     */
    public static boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }

    /**
     * 拼接两个 byte 数组，返回新数组。
     * 任一侧为 null 时按空数组处理。不修改传入的数组。
     * 两侧都是 null 时返回长度为 0 的新数组，不返回 null。
     * 两段长度之和超出 int 时抛出 {@link IllegalArgumentException}。
     *
     * @param left  左段，允许为 null
     * @param right 右段，允许为 null
     * @return 新的拼接结果
     */
    public static byte[] concat(byte[] left, byte[] right) {
        int leftLength = left == null ? 0 : left.length;
        int rightLength = right == null ? 0 : right.length;
        if (leftLength > Integer.MAX_VALUE - rightLength) {
            throw new IllegalArgumentException("concat length overflows");
        }
        byte[] out = new byte[leftLength + rightLength];
        if (leftLength > 0) {
            System.arraycopy(left, 0, out, 0, leftLength);
        }
        if (rightLength > 0) {
            System.arraycopy(right, 0, out, leftLength, rightLength);
        }
        return out;
    }

    /**
     * 查找 byte 数组中第一处等于 value 的下标。
     * data 为 null 或没有匹配时返回 -1。不修改 data。
     * 比较的是 8 位值，{@code (byte) 0xff} 与 {@code (byte) -1} 相同。
     *
     * @param data  允许为 null
     * @param value 要查找的字节
     * @return 第一处匹配下标；未找到时返回 -1
     */
    public static int indexOf(byte[] data, byte value) {
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
     * 查找 pattern 在 data 中首次出现的起始下标。
     * 找不到时返回 -1。data 或 pattern 为 null 时返回 -1。
     * pattern 为空数组且 data 不是 null 时返回 0，包括 data 也是空数组。
     * 比较的是 8 位值，{@code (byte) 0xff} 与 {@code (byte) -1} 相同。不修改入参。
     * 不修改 {@link #indexOf(byte[], byte)}。
     *
     * @param data    原数组，允许为 null
     * @param pattern 要查找的字节序列，允许为 null
     * @return 首次出现的起始下标；未找到时返回 -1
     */
    public static int indexOf(byte[] data, byte[] pattern) {
        if (data == null || pattern == null) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        if (pattern.length > data.length) {
            return -1;
        }
        int limit = data.length - pattern.length;
        for (int i = 0; i <= limit; i++) {
            boolean matched = true;
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 查找 byte 数组中最后一处等于 value 的下标。
     * data 为 null 或没有匹配时返回 -1。不修改 data。
     * 比较规则与 {@link #indexOf(byte[], byte)} 相同，{@code (byte) 0xff} 与 {@code (byte) -1} 相同。
     *
     * @param data  允许为 null
     * @param value 要查找的字节
     * @return 最后一处匹配下标；未找到时返回 -1
     */
    public static int lastIndexOf(byte[] data, byte value) {
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
     * 判断 byte 数组是否包含指定字节。
     * data 为 null 时返回 false。不修改 data。
     * 比较的是 8 位值，{@code (byte) 0xff} 与 {@code (byte) -1} 相同。
     * 查找使用 {@link #indexOf(byte[], byte)}，不修改 {@link #indexOf(byte[], byte)} 和 {@link #lastIndexOf(byte[], byte)}。
     *
     * @param data  允许为 null
     * @param value 要查找的字节
     * @return 找到时返回 true
     */
    public static boolean contains(byte[] data, byte value) {
        return indexOf(data, value) >= 0;
    }

    /**
     * 判断 data 是否以 prefix 开头。
     * data 或 prefix 为 null 时返回 false。prefix 为空数组且 data 不是 null 时返回 true，包括 data 也是空数组。
     * prefix 比 data 长时返回 false。不修改入参。
     * 比较的是 8 位值，{@code (byte) 0xff} 与 {@code (byte) -1} 相同。
     * 不修改 {@link #indexOf(byte[], byte)}。
     *
     * @param data   原数组，允许为 null
     * @param prefix 前缀，允许为 null
     * @return 以前缀开头时返回 true
     */
    public static boolean startsWith(byte[] data, byte[] prefix) {
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

    /**
     * 判断 data 是否以 suffix 结尾。
     * 规则与 {@link #startsWith(byte[], byte[])} 对称。
     * data 或 suffix 为 null 时返回 false。suffix 为空数组且 data 不是 null 时返回 true，包括 data 也是空数组。
     * suffix 比 data 长时返回 false。不修改入参。
     * 比较的是 8 位值，{@code (byte) 0xff} 与 {@code (byte) -1} 相同。
     * 不修改 {@link #startsWith(byte[], byte[])}。
     *
     * @param data   原数组，允许为 null
     * @param suffix 后缀，允许为 null
     * @return 以后缀结尾时返回 true
     */
    public static boolean endsWith(byte[] data, byte[] suffix) {
        if (data == null || suffix == null) {
            return false;
        }
        if (suffix.length > data.length) {
            return false;
        }
        int offset = data.length - suffix.length;
        for (int i = 0; i < suffix.length; i++) {
            if (data[offset + i] != suffix[i]) {
                return false;
            }
        }
        return true;
    }
}
