package com.henry.common.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 正则匹配。
 * 已发布的 1.0.5 没有这个类。不使用 Android API。
 */
public class RegexUtil {

    private RegexUtil() {
    }

    /**
     * 判断文本是否完整匹配正则。
     * text 或 regex 为 null 时返回 false，不编译正则。
     * 空正则是合法模式：只匹配空串。
     * 正则语法错误时抛出 {@link PatternSyntaxException}，它是 {@link IllegalArgumentException} 的子类。
     *
     * @param text  待匹配文本，允许为 null
     * @param regex 正则，允许为 null
     * @return 整个文本匹配时返回 true
     */
    public static boolean matches(String text, String regex) {
        if (text == null || regex == null) {
            return false;
        }
        return Pattern.compile(regex).matcher(text).matches();
    }

    /**
     * 返回第一处匹配到的文本。
     * text 或 regex 为 null，或没有匹配时返回 null。
     * 匹配结果本身可以是空串，例如空正则匹配到的空串；这和“没有匹配”不同。
     * 正则语法错误时抛出 {@link PatternSyntaxException}。
     *
     * @param text  待查找文本，允许为 null
     * @param regex 正则，允许为 null
     * @return 第一处匹配；没有匹配时返回 null
     */
    public static String findFirst(String text, String regex) {
        if (text == null || regex == null) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(text);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group();
    }

    /**
     * 替换第一处匹配。
     * text、regex 或 replacement 为 null 时返回 null，不编译正则。
     * 没有匹配时返回原 text。replacement 中的 {@code $} 和 {@code \} 按 {@link Matcher#replaceFirst(String)} 的替换语法处理，不是字面量。
     * 替换语法不合法时抛出 {@link IllegalArgumentException}。正则语法错误时抛出 {@link PatternSyntaxException}。
     * 不修改 {@link #findFirst(String, String)}。
     *
     * @param text        原文，允许为 null
     * @param regex       正则，允许为 null
     * @param replacement 替换文本，允许为 null
     * @return 替换后的文本；任一参数为 null 时返回 null
     */
    public static String replaceFirst(String text, String regex, String replacement) {
        if (text == null || regex == null || replacement == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(text).replaceFirst(replacement);
    }
}
