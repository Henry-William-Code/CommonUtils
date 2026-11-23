package com.henry.common.utils.mac;

/**
 * MAC 地址文本。
 * 已发布的 1.0.7 没有这个类。不使用 Android API。
 */
public class MacUtil {

    private MacUtil() {
    }

    /**
     * 判断文本是不是 MAC 地址。
     * 接受 6 组各 2 位十六进制，分隔符全程是 {@code :} 或全程是 {@code -}，也接受连续 12 位十六进制。
     * 十六进制不区分大小写。不混用两种分隔符。
     * text 为 null、空白、长度不对、非法字符、空段、多余分隔符或前后有空格时返回 false，不抛异常，也不 trim。
     *
     * @param text 允许为 null
     * @return 合法 MAC 地址时返回 true
     */
    public static boolean isValid(String text) {
        return parse(text) != null;
    }

    /**
     * 把合法 MAC 地址规范成小写冒号形式。
     * {@code AA-BB-CC-DD-EE-FF} 和 {@code aabbccddeeff} 都得到 {@code aa:bb:cc:dd:ee:ff}。
     * {@link #isValid(String)} 为 false 时抛出 {@link IllegalArgumentException}，不返回残缺结果。
     *
     * @param text MAC 地址，不能为非法文本
     * @return 小写冒号形式
     */
    public static String normalize(String text) {
        int[] octets = parse(text);
        if (octets == null) {
            throw new IllegalArgumentException("mac address is invalid");
        }
        char[] out = new char[17];
        for (int i = 0; i < octets.length; i++) {
            int value = octets[i];
            int offset = i * 3;
            out[offset] = hex(value >>> 4);
            out[offset + 1] = hex(value & 0x0f);
            if (i < octets.length - 1) {
                out[offset + 2] = ':';
            }
        }
        return new String(out);
    }

    private static int[] parse(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        if (text.length() == 12) {
            return parsePlain(text);
        }
        if (text.length() == 17) {
            return parseSeparated(text);
        }
        return null;
    }

    private static int[] parsePlain(String text) {
        int[] octets = new int[6];
        for (int i = 0; i < octets.length; i++) {
            int high = hexDigit(text.charAt(i * 2));
            int low = hexDigit(text.charAt(i * 2 + 1));
            if (high < 0 || low < 0) {
                return null;
            }
            octets[i] = (high << 4) | low;
        }
        return octets;
    }

    private static int[] parseSeparated(String text) {
        char separator = text.charAt(2);
        if (separator != ':' && separator != '-') {
            return null;
        }
        int[] octets = new int[6];
        for (int i = 0; i < octets.length; i++) {
            int offset = i * 3;
            int high = hexDigit(text.charAt(offset));
            int low = hexDigit(text.charAt(offset + 1));
            if (high < 0 || low < 0) {
                return null;
            }
            if (i < octets.length - 1 && text.charAt(offset + 2) != separator) {
                return null;
            }
            octets[i] = (high << 4) | low;
        }
        return octets;
    }

    private static int hexDigit(char current) {
        if (current >= '0' && current <= '9') {
            return current - '0';
        }
        if (current >= 'a' && current <= 'f') {
            return current - 'a' + 10;
        }
        if (current >= 'A' && current <= 'F') {
            return current - 'A' + 10;
        }
        return -1;
    }

    private static char hex(int value) {
        return (char) (value < 10 ? '0' + value : 'a' + (value - 10));
    }
}
