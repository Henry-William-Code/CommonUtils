package com.henry.common.utils.wildcard;

/**
 * 通用字符串通配。{@code *} 匹配任意长度（含空串，也含 {@code /}），{@code ?} 恰好匹配一个字符（含 {@code /}）。
 * 这不是文件系统路径 glob，也不是正则。大小写敏感，不 trim。
 * 不调用 {@code java.util.regex.Pattern}。已发布的 1.0.9 没有这个类。
 */
public class WildcardUtil {

    private static final int STAR = -1;
    private static final int QUESTION = -2;

    private WildcardUtil() {
    }

    /**
     * 判断 text 是否匹配 pattern。
     * text 或 pattern 为 null 时返回 false，不抛异常。
     * 空模式只匹配空串。{@code *} 匹配任意非 null 文本，包括空串。
     * 反斜杠只转义 {@code *}、{@code ?}、{@code \}。其他转义（如 {@code \a}）或末尾孤立 {@code \}
     * 抛出 {@link IllegalArgumentException}。这是模式非法，不是不匹配。
     * 连续 {@code *} 合法。字面量按精确字符匹配，包括空白。大小写敏感，不 trim。
     * {@code ?} 匹配一个 Java {@code char}，代理项的一半也算一个字符。
     *
     * @param text    原文，允许为 null
     * @param pattern 通配模式，允许为 null
     * @return 匹配时返回 true
     */
    public static boolean matches(String text, String pattern) {
        if (text == null || pattern == null) {
            return false;
        }
        int[] tokens = compile(pattern);
        return matchTokens(text, tokens);
    }

    /**
     * 把 {@code *}、{@code ?}、{@code \} 转成 {@code \*}、{@code \?}、{@code \\}，
     * 使结果作为 pattern 时只匹配该字面量。
     * literal 为 null 时返回空串。{@code escape("")} 也是空串。
     * 对任意非 null 的 text，{@code matches(text, escape(text))} 为 true。
     * other 与 text 不等时，{@code matches(other, escape(text))} 为 false。
     *
     * @param literal 要按字面匹配的文本，允许为 null
     * @return 转义后的模式；null 时返回空串
     */
    public static String escape(String literal) {
        if (literal == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(literal.length());
        for (int i = 0; i < literal.length(); i++) {
            char current = literal.charAt(i);
            if (current == '*' || current == '?' || current == '\\') {
                out.append('\\');
            }
            out.append(current);
        }
        return out.toString();
    }

    private static int[] compile(String pattern) {
        int[] tokens = new int[pattern.length()];
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            char current = pattern.charAt(i);
            if (current == '\\') {
                if (i + 1 >= pattern.length()) {
                    throw new IllegalArgumentException("wildcard escape is invalid");
                }
                char escaped = pattern.charAt(++i);
                if (escaped != '*' && escaped != '?' && escaped != '\\') {
                    throw new IllegalArgumentException("wildcard escape is invalid");
                }
                tokens[count++] = escaped;
                continue;
            }
            if (current == '*') {
                tokens[count++] = STAR;
                continue;
            }
            if (current == '?') {
                tokens[count++] = QUESTION;
                continue;
            }
            tokens[count++] = current;
        }
        if (count == tokens.length) {
            return tokens;
        }
        int[] out = new int[count];
        System.arraycopy(tokens, 0, out, 0, count);
        return out;
    }

    private static boolean matchTokens(String text, int[] tokens) {
        int textIndex = 0;
        int tokenIndex = 0;
        int starToken = -1;
        int starText = -1;
        while (textIndex < text.length()) {
            if (tokenIndex < tokens.length && matchesOne(text.charAt(textIndex), tokens[tokenIndex])) {
                textIndex++;
                tokenIndex++;
                continue;
            }
            if (tokenIndex < tokens.length && tokens[tokenIndex] == STAR) {
                starToken = tokenIndex;
                starText = textIndex;
                tokenIndex++;
                continue;
            }
            if (starToken >= 0) {
                starText++;
                textIndex = starText;
                tokenIndex = starToken + 1;
                continue;
            }
            return false;
        }
        while (tokenIndex < tokens.length && tokens[tokenIndex] == STAR) {
            tokenIndex++;
        }
        return tokenIndex == tokens.length;
    }

    private static boolean matchesOne(char current, int token) {
        return token == QUESTION || token == current;
    }
}
