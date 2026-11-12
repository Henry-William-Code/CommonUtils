package com.henry.common.utils.convert;

import com.henry.common.utils.string.StringUtil;

/**
 * 把文本安全地转成整数。失败时返回调用方给出的默认值，不抛出解析异常。
 * 已发布的 1.0.2 没有这个类。不代替会抛异常的 {@code DecimalUtil.checkNumber}。
 */
public class ConvertUtil {

    private ConvertUtil() {
    }

    /**
     * 把十进制文本解析成 int。
     * null、空串、只含空白、非法格式或超出 int 范围时返回 defaultValue，不抛异常。
     * 空白规则与 {@link StringUtil#isBlank(String)} 相同。非空白文本会去掉首尾空白后再解析，中间的空格不接受。
     * 允许 ASCII 正负号，不接受小数点、千分位、十六进制前缀或全角数字。
     * 默认值可以是任意 int。文本无效且默认值恰好等于某个合法数字时，返回值无法区分“解析成功”和“使用了默认值”。
     *
     * @param text         十进制文本，允许为 null
     * @param defaultValue 无法解析时返回的值
     * @return 解析结果或 defaultValue
     */
    public static int parseInt(String text, int defaultValue) {
        Long parsed = parseDecimal(text);
        if (parsed == null || parsed.longValue() < Integer.MIN_VALUE || parsed.longValue() > Integer.MAX_VALUE) {
            return defaultValue;
        }
        return parsed.intValue();
    }

    /**
     * 把十进制文本解析成 long。
     * null、空串、只含空白、非法格式或超出 long 范围时返回 defaultValue，不抛异常。
     * 空白、正负号和拒绝的格式与 {@link #parseInt(String, int)} 相同。
     * 默认值可以是任意 long。文本无效且默认值恰好等于某个合法数字时，返回值无法区分“解析成功”和“使用了默认值”。
     *
     * @param text         十进制文本，允许为 null
     * @param defaultValue 无法解析时返回的值
     * @return 解析结果或 defaultValue
     */
    public static long parseLong(String text, long defaultValue) {
        Long parsed = parseDecimal(text);
        if (parsed == null) {
            return defaultValue;
        }
        return parsed.longValue();
    }

    /**
     * 把文本解析成布尔值。
     * null、空串、只含空白，或不是 true / false 时返回 defaultValue，不抛异常。
     * 去掉首尾空白后，只接受 ASCII 大小写不敏感的 {@code true} 和 {@code false}。
     * 不接受 1、0、yes、no 或全角字母。中间有空白时返回默认值。
     * 默认值可以是 true 或 false。文本无效时无法从返回值区分“解析成功”和“使用了默认值”。
     *
     * @param text         文本，允许为 null
     * @param defaultValue 无法解析时返回的值
     * @return true、false 或 defaultValue
     */
    public static boolean parseBoolean(String text, boolean defaultValue) {
        if (StringUtil.isBlank(text)) {
            return defaultValue;
        }
        String trimmed = trimWhitespace(text);
        if (equalsIgnoreAsciiCase(trimmed, "true")) {
            return true;
        }
        if (equalsIgnoreAsciiCase(trimmed, "false")) {
            return false;
        }
        return defaultValue;
    }

    /**
     * @return 解析成功时返回对应 long；空白、格式错误或溢出时返回 null
     */
    private static Long parseDecimal(String text) {
        if (StringUtil.isBlank(text)) {
            return null;
        }
        String trimmed = trimWhitespace(text);
        if (trimmed.length() == 0) {
            return null;
        }
        int index = 0;
        boolean negative = false;
        char first = trimmed.charAt(0);
        if (first == '+' || first == '-') {
            negative = first == '-';
            index = 1;
        }
        if (index >= trimmed.length()) {
            return null;
        }
        long value = 0L;
        for (int i = index; i < trimmed.length(); i++) {
            char current = trimmed.charAt(i);
            if (current < '0' || current > '9') {
                return null;
            }
            int digit = current - '0';
            if (value < Long.MIN_VALUE / 10L) {
                return null;
            }
            value *= 10L;
            if (value < Long.MIN_VALUE + digit) {
                return null;
            }
            value -= digit;
        }
        if (!negative) {
            if (value == Long.MIN_VALUE) {
                return null;
            }
            return Long.valueOf(-value);
        }
        return Long.valueOf(value);
    }

    private static String trimWhitespace(String text) {
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

    private static boolean equalsIgnoreAsciiCase(String text, String expectedLower) {
        if (text.length() != expectedLower.length()) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current >= 'A' && current <= 'Z') {
                current = (char) (current + ('a' - 'A'));
            }
            if (current != expectedLower.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
