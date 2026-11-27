package com.henry.common.utils.base32;

import com.henry.common.utils.string.StringUtil;

/**
 * RFC 4648 Base32。字母表是 {@code A-Z} 和 {@code 2-7}，填充字符是 {@code =}。
 * 不是 Base32hex，也不是 Base64。不修改 {@code Base64Util}、{@code HexUtil} 和 {@code UrlCodecUtil}。
 * 不使用 Android API。
 */
public class Base32Util {

    private static final char[] ALPHABET = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '2', '3', '4', '5', '6', '7'
    };

    private static final int[] DECODE = new int[128];

    static {
        for (int i = 0; i < DECODE.length; i++) {
            DECODE[i] = -1;
        }
        for (int i = 0; i < ALPHABET.length; i++) {
            DECODE[ALPHABET[i]] = i;
            if (ALPHABET[i] >= 'A' && ALPHABET[i] <= 'Z') {
                DECODE[ALPHABET[i] + ('a' - 'A')] = i;
            }
        }
    }

    private Base32Util() {
    }

    /**
     * 按 RFC 4648 把字节编成大写 Base32，并补齐 {@code =}。
     * data 为 null 或长度为 0 时返回空串。
     * 对任意非 null 的 data，{@code decode(encode(data))} 的内容与 data 相同。
     *
     * @param data 原始字节，允许为 null
     * @return 大写 Base32；没有内容时返回空串
     */
    public static String encode(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        char[] out = new char[((data.length + 4) / 5) * 8];
        int buffer = 0;
        int bits = 0;
        int index = 0;
        for (int i = 0; i < data.length; i++) {
            buffer = (buffer << 8) | (data[i] & 0xff);
            bits += 8;
            while (bits >= 5) {
                bits -= 5;
                out[index++] = ALPHABET[(buffer >> bits) & 31];
            }
            buffer &= (1 << bits) - 1;
        }
        if (bits > 0) {
            out[index++] = ALPHABET[(buffer << (5 - bits)) & 31];
        }
        while (index < out.length) {
            out[index++] = '=';
        }
        return new String(out);
    }

    /**
     * 把 RFC 4648 Base32 解回字节。字母不区分大小写。
     * text 为 null、空串，或只含空白时返回长度为 0 的新数组。空白规则与 {@link StringUtil#isBlank(String)} 相同。
     * 不接受空白夹在非空白文本中间，不接受字母表外字符，不接受错误或缺失的填充，也不接受末组里非 0 的多余位。
     * 这些情况以及长度不是 8 的倍数时抛出 {@link IllegalArgumentException}，不返回截断结果。
     *
     * @param text Base32 文本，允许为 null
     * @return 解码后的新数组
     */
    public static byte[] decode(String text) {
        if (StringUtil.isBlank(text)) {
            return new byte[0];
        }
        if (text.length() % 8 != 0) {
            throw new IllegalArgumentException("base32 is invalid");
        }
        int padding = 0;
        for (int i = text.length() - 1; i >= 0 && text.charAt(i) == '='; i--) {
            padding++;
        }
        if (padding != 0 && padding != 1 && padding != 3 && padding != 4 && padding != 6) {
            throw new IllegalArgumentException("base32 is invalid");
        }
        int dataChars = text.length() - padding;
        int byteLength = (dataChars / 8) * 5 + tailBytes(dataChars % 8);
        byte[] out = new byte[byteLength];
        int buffer = 0;
        int bits = 0;
        int outIndex = 0;
        for (int i = 0; i < dataChars; i++) {
            int value = decodeChar(text.charAt(i));
            if (value < 0) {
                throw new IllegalArgumentException("base32 is invalid");
            }
            buffer = (buffer << 5) | value;
            bits += 5;
            if (bits >= 8) {
                bits -= 8;
                out[outIndex++] = (byte) ((buffer >> bits) & 0xff);
                buffer &= (1 << bits) - 1;
            }
        }
        if (bits > 0 && buffer != 0) {
            throw new IllegalArgumentException("base32 is invalid");
        }
        if (outIndex != out.length) {
            throw new IllegalArgumentException("base32 is invalid");
        }
        return out;
    }

    private static int tailBytes(int remainder) {
        if (remainder == 0) {
            return 0;
        }
        if (remainder == 2) {
            return 1;
        }
        if (remainder == 4) {
            return 2;
        }
        if (remainder == 5) {
            return 3;
        }
        if (remainder == 7) {
            return 4;
        }
        throw new IllegalArgumentException("base32 is invalid");
    }

    private static int decodeChar(char current) {
        if (current >= DECODE.length) {
            return -1;
        }
        return DECODE[current];
    }
}
