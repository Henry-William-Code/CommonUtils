package com.henry.common.utils.ip;

/**
 * 点分十进制 IPv4。
 * 已发布的 1.0.7 没有这个类。不使用 Android API，也不修改 {@code WebUtil}。
 */
public class IpUtil {

    private IpUtil() {
    }

    /**
     * 判断文本是不是点分十进制 IPv4。
     * 必须恰好 4 段，每段是 0 到 255 的十进制整数。除单个 {@code 0} 外不接受前导零。
     * null、空白、空段、前后空格、多余点号、IPv6、CIDR、端口或非数字都返回 false，不抛异常。
     * 不 trim。
     *
     * @param text 允许为 null
     * @return 合法 IPv4 时返回 true
     */
    public static boolean isIpv4(String text) {
        return parse(text) != null;
    }

    /**
     * 把合法 IPv4 转成无符号 32 位整数，用 long 返回。
     * 结果范围是 0 到 4294967295。{@code 0.0.0.0} 为 0，{@code 255.255.255.255} 为 4294967295。
     * {@link #isIpv4(String)} 为 false 时抛出 {@link IllegalArgumentException}。
     *
     * @param ipv4 点分十进制 IPv4，不能为非法文本
     * @return 无符号 32 位值
     */
    public static long toLong(String ipv4) {
        int[] parts = parse(ipv4);
        if (parts == null) {
            throw new IllegalArgumentException("ipv4 is invalid");
        }
        return ((long) parts[0] << 24)
                | ((long) parts[1] << 16)
                | ((long) parts[2] << 8)
                | parts[3];
    }

    private static int[] parse(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        int[] parts = new int[4];
        int partIndex = 0;
        int value = 0;
        int digits = 0;
        boolean leadingZero = false;
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == '.') {
                if (digits == 0 || partIndex == 3) {
                    return null;
                }
                parts[partIndex] = value;
                partIndex++;
                value = 0;
                digits = 0;
                leadingZero = false;
                continue;
            }
            if (current < '0' || current > '9') {
                return null;
            }
            if (digits == 0 && current == '0') {
                leadingZero = true;
            } else if (leadingZero) {
                return null;
            }
            digits++;
            if (digits > 3) {
                return null;
            }
            value = value * 10 + (current - '0');
            if (value > 255) {
                return null;
            }
        }
        if (partIndex != 3 || digits == 0) {
            return null;
        }
        parts[3] = value;
        return parts;
    }
}
