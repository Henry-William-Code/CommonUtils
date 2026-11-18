package com.henry.common.utils.uuid;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 标准 UUID 文本。
 * 已发布的 1.0.6 没有这个类。不使用 Android API。
 */
public class UuidUtil {

    private static final Pattern UUID_TEXT = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private UuidUtil() {
    }

    /**
     * 生成随机 UUID 的标准文本。
     * 固定为 36 个字符，小写十六进制，连字符位置是 8-4-4-4-12。
     *
     * @return 标准 UUID 文本
     */
    public static String random() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断文本是不是标准 UUID。
     * 必须带连字符，分段长度是 8-4-4-4-12，十六进制不区分大小写。
     * text 为 null、空串、缺少连字符或长度不对时返回 false，不抛异常。
     *
     * @param text 允许为 null
     * @return 符合标准格式时返回 true
     */
    public static boolean isValid(String text) {
        return text != null && UUID_TEXT.matcher(text).matches();
    }
}
