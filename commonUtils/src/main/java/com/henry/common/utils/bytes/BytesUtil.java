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
}
