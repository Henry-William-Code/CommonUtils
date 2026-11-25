package com.henry.common.utils.size;

/**
 * 按 1024 进制格式化和解析字节大小。单位是 B、KB、MB、GB、TB、PB、EB。
 * 已发布的 1.0.8 没有这个类。不使用 Android API。
 */
public class ByteSizeUtil {

    private static final long KB = 1L << 10;
    private static final long MB = 1L << 20;
    private static final long GB = 1L << 30;
    private static final long TB = 1L << 40;
    private static final long PB = 1L << 50;
    private static final long EB = 1L << 60;
    private static final long[] UNITS = {EB, PB, TB, GB, MB, KB};
    private static final String[] LABELS = {"EB", "PB", "TB", "GB", "MB", "KB"};

    private ByteSizeUtil() {
    }

    /**
     * 把字节数格式化成可读文本。
     * {@code 0} 返回 {@code 0 B}。{@link Long#MIN_VALUE} 抛出 {@link ArithmeticException}。
     * 负数先格式化绝对值，再加 {@code -}。数字和单位之间有一个空格。
     * 从 EB 到 KB 选择能用整数或恰好一位小数精确表示、且数值绝对值大于等于 1 的最大单位。
     * 一位小数必须精确，{@code abs * 10} 能被该单位整除，不四舍五入。
     * 对 1024 进制，精确的一位小数只有 {@code .5}。没有任何这样的单位时，用整数字节加 {@code B}。
     * 整数写成 {@code 1 KB}，不写成 {@code 1.0 KB}。
     * 除 {@link Long#MIN_VALUE} 外，{@code parse(format(n))} 等于 n。
     *
     * @param bytes 字节数
     * @return 带单位的文本
     */
    public static String format(long bytes) {
        if (bytes == Long.MIN_VALUE) {
            throw new ArithmeticException("long overflow");
        }
        long absolute = bytes < 0L ? -bytes : bytes;
        String sign = bytes < 0L ? "-" : "";
        for (int i = 0; i < UNITS.length; i++) {
            long unit = UNITS[i];
            if (absolute < unit || absolute % (unit / 2L) != 0L) {
                continue;
            }
            if (absolute % unit == 0L) {
                return sign + (absolute / unit) + " " + LABELS[i];
            }
            return sign + (absolute / unit) + ".5 " + LABELS[i];
        }
        return sign + absolute + " B";
    }

    /**
     * 把字节大小文本解析成字节数。
     * 允许首尾空白。允许可选的 {@code +} 或 {@code -}。数字是整数，或恰好一位小数。
     * 单位不区分大小写，只接受 B、KB、MB、GB、TB、PB、EB。数字和单位之间可以有一个空格，也可以没有。
     * 不带单位时只有整数按字节解析，{@code 1.5} 非法。
     * 结果必须是整数字节，并且能放进 long。非整数字节、非法格式、空串、null、未知单位或溢出时抛出 {@link IllegalArgumentException}。
     * {@link #format(long)} 产出的文本都能被还原。
     *
     * @param text 大小文本，允许为 null
     * @return 字节数
     */
    public static long parse(String text) {
        if (text == null) {
            throw new IllegalArgumentException("size is empty");
        }
        String trimmed = trim(text);
        if (trimmed.length() == 0) {
            throw new IllegalArgumentException("size is empty");
        }
        int index = 0;
        int sign = 1;
        char first = trimmed.charAt(0);
        if (first == '+' || first == '-') {
            sign = first == '-' ? -1 : 1;
            index = 1;
        }
        if (index >= trimmed.length() || !isDigit(trimmed.charAt(index))) {
            throw new IllegalArgumentException("size is invalid");
        }
        long whole = 0L;
        while (index < trimmed.length() && isDigit(trimmed.charAt(index))) {
            int digit = trimmed.charAt(index) - '0';
            if (whole > (Long.MAX_VALUE - digit) / 10L) {
                throw new IllegalArgumentException("size overflows");
            }
            whole = whole * 10L + digit;
            index++;
        }
        int fractionDigit = -1;
        if (index < trimmed.length() && trimmed.charAt(index) == '.') {
            index++;
            if (index >= trimmed.length() || !isDigit(trimmed.charAt(index))) {
                throw new IllegalArgumentException("size is invalid");
            }
            fractionDigit = trimmed.charAt(index) - '0';
            index++;
            if (index < trimmed.length() && isDigit(trimmed.charAt(index))) {
                throw new IllegalArgumentException("size is invalid");
            }
        }
        if (index < trimmed.length() && trimmed.charAt(index) == ' ') {
            index++;
            if (index < trimmed.length() && trimmed.charAt(index) == ' ') {
                throw new IllegalArgumentException("size is invalid");
            }
        }
        String unit = trimmed.substring(index);
        long unitSize = unitSize(unit);
        if (unitSize < 0L) {
            throw new IllegalArgumentException("size unit is invalid");
        }
        if (unit.length() == 0 && fractionDigit >= 0) {
            throw new IllegalArgumentException("size is invalid");
        }
        if (whole > Long.MAX_VALUE / unitSize) {
            throw new IllegalArgumentException("size overflows");
        }
        long magnitude = whole * unitSize;
        if (fractionDigit > 0) {
            long fraction = fractionBytes(unitSize, fractionDigit);
            if (magnitude > Long.MAX_VALUE - fraction) {
                throw new IllegalArgumentException("size overflows");
            }
            magnitude += fraction;
        }
        if (sign < 0) {
            return -magnitude;
        }
        return magnitude;
    }

    private static long fractionBytes(long unitSize, int fractionDigit) {
        if (fractionDigit == 5 && unitSize > 1L && (unitSize & 1L) == 0L) {
            return unitSize / 2L;
        }
        throw new IllegalArgumentException("size is not a whole number of bytes");
    }

    private static long unitSize(String unit) {
        if (unit.length() == 0) {
            return 1L;
        }
        if (equalsUnit(unit, "B")) {
            return 1L;
        }
        if (equalsUnit(unit, "KB")) {
            return KB;
        }
        if (equalsUnit(unit, "MB")) {
            return MB;
        }
        if (equalsUnit(unit, "GB")) {
            return GB;
        }
        if (equalsUnit(unit, "TB")) {
            return TB;
        }
        if (equalsUnit(unit, "PB")) {
            return PB;
        }
        if (equalsUnit(unit, "EB")) {
            return EB;
        }
        return -1L;
    }

    private static boolean equalsUnit(String unit, String expected) {
        if (unit.length() != expected.length()) {
            return false;
        }
        for (int i = 0; i < unit.length(); i++) {
            char current = unit.charAt(i);
            if (current >= 'a' && current <= 'z') {
                current = (char) (current - ('a' - 'A'));
            }
            if (current != expected.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static String trim(String text) {
        int start = 0;
        int end = text.length();
        while (start < end && Character.isWhitespace(text.charAt(start))) {
            start++;
        }
        while (end > start && Character.isWhitespace(text.charAt(end - 1))) {
            end--;
        }
        return text.substring(start, end);
    }

    private static boolean isDigit(char current) {
        return current >= '0' && current <= '9';
    }
}
