package com.henry.common.utils.version;

import com.henry.common.utils.string.StringUtil;

/**
 * 点号数字版本比较。
 * 已发布的 1.0.6 没有这个类。不使用 Android API。
 */
public class VersionUtil {

    private VersionUtil() {
    }

    /**
     * 按点号分段比较两个版本号。
     * {@code 1.0.10} 大于 {@code 1.0.2}。缺少的段按 0 处理，因此 {@code 1.0} 等于 {@code 1.0.0}。
     * 前导零不改变数值。不 trim 非空白文本。
     * null、空串、只含空白、空段、非数字或某一段超出 long 范围时抛出 {@link IllegalArgumentException}。
     * 不接受正负号、字母前缀或空格。
     *
     * @param left  左侧版本，不能为 null
     * @param right 右侧版本，不能为 null
     * @return 左侧较小返回负数，相等返回 0，左侧较大返回正数
     */
    public static int compare(String left, String right) {
        long[] leftParts = parse(left);
        long[] rightParts = parse(right);
        int count = Math.max(leftParts.length, rightParts.length);
        for (int i = 0; i < count; i++) {
            long leftValue = i < leftParts.length ? leftParts[i] : 0L;
            long rightValue = i < rightParts.length ? rightParts[i] : 0L;
            if (leftValue != rightValue) {
                return leftValue < rightValue ? -1 : 1;
            }
        }
        return 0;
    }

    private static long[] parse(String version) {
        if (StringUtil.isBlank(version)) {
            throw new IllegalArgumentException("version is empty");
        }
        String[] parts = version.split("\\.", -1);
        long[] values = new long[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.length() == 0) {
                throw new IllegalArgumentException("version segment is empty");
            }
            long value = 0L;
            for (int j = 0; j < part.length(); j++) {
                char current = part.charAt(j);
                if (current < '0' || current > '9') {
                    throw new IllegalArgumentException("version segment is not a number");
                }
                int digit = current - '0';
                if (value > (Long.MAX_VALUE - digit) / 10L) {
                    throw new IllegalArgumentException("version segment overflows");
                }
                value = value * 10L + digit;
            }
            values[i] = value;
        }
        return values;
    }
}
