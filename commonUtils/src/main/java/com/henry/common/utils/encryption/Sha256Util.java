package com.henry.common.utils.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 摘要。这是单向散列，不是可逆加密。
 * 已发布的 1.0.2 没有这个类，也不改变 {@code MD5Util} 的行为。
 */
public class Sha256Util {

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private Sha256Util() {
    }

    /**
     * 计算文本的 SHA-256，返回 64 位小写十六进制。
     * 文本按 UTF-8 编码，不使用平台默认字符集。
     * text 为 null 时返回空串，不计算摘要。空串会计算空字节数组的真实摘要，因此和 null 不同。
     * 运行环境缺少 SHA-256 时抛出 {@link IllegalStateException}。Android API 23 和 Java 8 都提供该算法。
     *
     * @param text 原文，允许为 null
     * @return 64 位小写十六进制；text 为 null 时返回空串
     */
    public static String digest(String text) {
        if (text == null) {
            return "";
        }
        return digest(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算字节数组的 SHA-256，返回 64 位小写十六进制。
     * data 为 null 时返回空串。空数组会计算真实摘要，不返回空串。
     * 运行环境缺少 SHA-256 时抛出 {@link IllegalStateException}。
     *
     * @param data 原始字节，允许为 null
     * @return 64 位小写十六进制；data 为 null 时返回空串
     */
    public static String digest(byte[] data) {
        if (data == null) {
            return "";
        }
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
            return toHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is unavailable", ex);
        }
    }

    private static String toHex(byte[] hash) {
        char[] out = new char[hash.length * 2];
        for (int i = 0; i < hash.length; i++) {
            int value = hash[i] & 0xff;
            out[i * 2] = HEX_DIGITS[value >>> 4];
            out[i * 2 + 1] = HEX_DIGITS[value & 0x0f];
        }
        return new String(out);
    }
}
