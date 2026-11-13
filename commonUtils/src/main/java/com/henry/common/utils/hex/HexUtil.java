package com.henry.common.utils.hex;

import com.henry.common.utils.string.StringUtil;

/**
 * 字节数组和十六进制文本的互相转换。
 * 已发布的 1.0.4 没有这个类。不使用 Android API，也不做 int 进制转换。
 * 不改变 {@code DecimalUtil}、{@code Sha256Util} 和 {@code MD5Util}。
 */
public class HexUtil {

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private HexUtil() {
    }

    /**
     * 把字节数组编成小写十六进制。每个字节写成两个字符，不带 {@code 0x} 前缀。
     * data 为 null 或长度为 0 时返回空串。
     *
     * @param data 原始字节，允许为 null
     * @return 小写十六进制；没有字节时返回空串
     */
    public static String encode(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        char[] out = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 0xff;
            out[i * 2] = HEX_DIGITS[value >>> 4];
            out[i * 2 + 1] = HEX_DIGITS[value & 0x0f];
        }
        return new String(out);
    }

    /**
     * 把十六进制文本解析成字节数组。
     * hex 为 null、空串或只含空白时返回长度为 0 的数组。空白规则与 {@link StringUtil#isBlank(String)} 相同。
     * 字母不区分大小写。不接受 {@code 0x} 或 {@code 0X} 前缀，也不去掉非空白文本里的空格或换行。
     * 长度为奇数、含非法字符，或非空白文本中夹着空白时抛出 {@link IllegalArgumentException}，不返回截断结果。
     *
     * @param hex 十六进制文本，允许为 null
     * @return 解析出的字节；空白输入返回空数组
     */
    public static byte[] decode(String hex) {
        if (StringUtil.isBlank(hex)) {
            return new byte[0];
        }
        if ((hex.length() & 1) != 0) {
            throw new IllegalArgumentException("hex length is odd");
        }
        byte[] out = new byte[hex.length() / 2];
        for (int i = 0; i < out.length; i++) {
            int high = hexDigit(hex.charAt(i * 2));
            int low = hexDigit(hex.charAt(i * 2 + 1));
            if (high < 0 || low < 0) {
                throw new IllegalArgumentException("hex digit is invalid");
            }
            out[i] = (byte) ((high << 4) | low);
        }
        return out;
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
}
