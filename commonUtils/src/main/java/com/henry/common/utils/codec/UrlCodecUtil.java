package com.henry.common.utils.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * UTF-8 的 {@code application/x-www-form-urlencoded} 编解码。
 * 已发布的 1.0.5 没有这个类。不修改 {@code WebUtil}，也不使用 Android API。
 */
public class UrlCodecUtil {

    private UrlCodecUtil() {
    }

    /**
     * 按表单规则编码文本。
     * text 为 null 或空串时返回空串。空格编成 {@code +}。
     * 使用 {@link URLEncoder}，字符集固定为 UTF-8。
     * UTF-8 不可用时抛出 {@link IllegalStateException}。
     *
     * @param text 原文，允许为 null
     * @return 表单编码；没有内容时返回空串
     */
    public static String encode(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("UTF-8 is unavailable", ex);
        }
    }

    /**
     * 按表单规则解码文本。
     * text 为 null 或空串时返回空串。{@code +} 解成空格。
     * 非法百分号序列，例如末尾单独的 {@code %}、不完整的 {@code %A} 或非十六进制，
     * 抛出 {@link IllegalArgumentException}，不返回截断结果。
     * 对原文而言，{@link #encode(String)} 与本方法互为逆运算。
     * UTF-8 不可用时抛出 {@link IllegalStateException}。
     *
     * @param text 已编码文本，允许为 null
     * @return 解码后的原文；没有内容时返回空串
     */
    public static String decode(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("UTF-8 is unavailable", ex);
        }
    }
}
