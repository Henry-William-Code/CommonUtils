package com.henry.common.utils.json;

/**
 * JSON 字符串内容的转义。不加外层引号，也不解析整段 JSON。
 * 已发布的 1.0.7 没有这个类。不使用 JSON 库，也不修改 {@code HtmlEscapeUtil}。
 */
public class JsonEscapeUtil {

    private static final char[] HEX = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private JsonEscapeUtil() {
    }

    /**
     * 按 JSON 字符串内容转义。
     * 处理反斜杠、双引号、退格、换页、换行、回车、制表符，以及 U+0000 到 U+001F 的其他控制字符。
     * 控制字符写成 {@code \\u00XX}，十六进制为小写。不转义 {@code /}。
     * text 为 null 时返回空串。没有需要转义的字符时返回原字符串。不加外层引号。
     *
     * @param text 原文，允许为 null
     * @return 转义后的内容；text 为 null 时返回空串
     */
    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder out = null;
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            String replacement = replacement(current);
            if (replacement == null) {
                if (out != null) {
                    out.append(current);
                }
                continue;
            }
            if (out == null) {
                out = new StringBuilder(text.length() + 8);
                out.append(text, 0, i);
            }
            out.append(replacement);
        }
        return out == null ? text : out.toString();
    }

    /**
     * 还原 {@link #escape(String)} 写出的转义，并接受 {@code \\uXXXX} 与 {@code \\/}。
     * {@code \\u} 的四位十六进制不区分大小写。text 为 null 时返回空串。
     * 尾部孤立的反斜杠、不完整的 {@code \\u}、非法十六进制或不认识的转义抛出 {@link IllegalArgumentException}，不返回截断结果。
     * 不要求外层引号。
     *
     * @param text 转义内容，允许为 null
     * @return 还原后的文本；text 为 null 时返回空串
     */
    public static String unescape(String text) {
        if (text == null) {
            return "";
        }
        int slash = text.indexOf('\\');
        if (slash < 0) {
            return text;
        }
        StringBuilder out = new StringBuilder(text.length());
        out.append(text, 0, slash);
        for (int i = slash; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current != '\\') {
                out.append(current);
                continue;
            }
            if (i + 1 >= text.length()) {
                throw new IllegalArgumentException("trailing escape");
            }
            char next = text.charAt(++i);
            if (next == '\\') {
                out.append('\\');
            } else if (next == '"') {
                out.append('"');
            } else if (next == '/') {
                out.append('/');
            } else if (next == 'b') {
                out.append('\b');
            } else if (next == 'f') {
                out.append('\f');
            } else if (next == 'n') {
                out.append('\n');
            } else if (next == 'r') {
                out.append('\r');
            } else if (next == 't') {
                out.append('\t');
            } else if (next == 'u') {
                if (i + 4 >= text.length()) {
                    throw new IllegalArgumentException("incomplete unicode escape");
                }
                int value = 0;
                for (int shift = 1; shift <= 4; shift++) {
                    int digit = hexDigit(text.charAt(i + shift));
                    if (digit < 0) {
                        throw new IllegalArgumentException("illegal hex escape");
                    }
                    value = (value << 4) | digit;
                }
                out.append((char) value);
                i += 4;
            } else {
                throw new IllegalArgumentException("unknown escape");
            }
        }
        return out.toString();
    }

    private static String replacement(char current) {
        if (current == '\\') {
            return "\\\\";
        }
        if (current == '"') {
            return "\\\"";
        }
        if (current == '\b') {
            return "\\b";
        }
        if (current == '\f') {
            return "\\f";
        }
        if (current == '\n') {
            return "\\n";
        }
        if (current == '\r') {
            return "\\r";
        }
        if (current == '\t') {
            return "\\t";
        }
        if (current < 0x20) {
            return new String(new char[] {
                    '\\', 'u', '0', '0', HEX[current >>> 4], HEX[current & 0x0f]
            });
        }
        return null;
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
