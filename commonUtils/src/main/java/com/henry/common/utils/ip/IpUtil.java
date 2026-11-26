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
     * 判断文本是不是 127.0.0.0/8 里的 IPv4。
     * 必须先通过 {@link #isIpv4(String)} 的规则：无前导零，不 trim，非法文本返回 false，不抛异常。
     * 第一段必须是 127，后三段仍是 0 到 255。
     * 不修改 {@link #isIpv4(String)}、{@link #toLong(String)} 和 {@link #fromLong(long)}。
     *
     * @param text 允许为 null
     * @return 回环地址时返回 true
     */
    public static boolean isLoopback(String text) {
        int[] parts = parse(text);
        return parts != null && parts[0] == 127;
    }

    /**
     * 判断文本是不是 RFC 1918 私网 IPv4。
     * 只认 {@code 10.0.0.0/8}、{@code 172.16.0.0/12}（第二段 16 到 31）和 {@code 192.168.0.0/16}。
     * 必须先通过 {@link #isIpv4(String)} 的规则：无前导零，不 trim，非法文本返回 false，不抛异常。
     * {@code 127.0.0.1}、{@code 169.254.0.1}、{@code 0.0.0.0}、{@code 192.0.2.1}、IPv6 和前后空格都返回 false。
     * 不修改 {@link #isIpv4(String)}、{@link #isLoopback(String)}、{@link #toLong(String)} 和 {@link #fromLong(long)}。
     *
     * @param text 允许为 null
     * @return RFC 1918 地址时返回 true
     */
    public static boolean isPrivate(String text) {
        int[] parts = parse(text);
        if (parts == null) {
            return false;
        }
        if (parts[0] == 10) {
            return true;
        }
        if (parts[0] == 172 && parts[1] >= 16 && parts[1] <= 31) {
            return true;
        }
        return parts[0] == 192 && parts[1] == 168;
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

    /**
     * 把无符号 32 位整数还原成点分十进制 IPv4。
     * 这是 {@link #toLong(String)} 的逆运算。每段不带前导零，段值本身是 0 时写成 {@code 0}。
     * value 小于 0 或大于 4294967295 时抛出 {@link IllegalArgumentException}。
     * 不修改 {@link #isIpv4(String)} 和 {@link #toLong(String)}。
     *
     * @param value 无符号 32 位值
     * @return 点分十进制 IPv4
     */
    public static String fromLong(long value) {
        if (value < 0L || value > 4294967295L) {
            throw new IllegalArgumentException("ipv4 value is out of range");
        }
        return ((value >>> 24) & 0xffL) + "."
                + ((value >>> 16) & 0xffL) + "."
                + ((value >>> 8) & 0xffL) + "."
                + (value & 0xffL);
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
