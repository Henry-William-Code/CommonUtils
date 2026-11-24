package com.henry.common.utils.xml;

/**
 * XML 1.0 文本转义。只处理 5 个预定义实体和数字字符引用，不加外层标签或引号。
 * 规则与 {@code HtmlEscapeUtil}、{@code JsonEscapeUtil} 不同，不复用它们。
 * 已发布的 1.0.8 没有这个类。不能实例化，也不使用 Android API。
 */
public class XmlEscapeUtil {

    private XmlEscapeUtil() {
    }

    /**
     * 转义 XML 文本中的 {@code & < > " '}。
     * text 为 null 时返回空串。先替换 {@code &}，再替换 {@code <}、{@code >}、{@code "}、{@code '}，
     * 对应实体是 {@code &amp;}、{@code &lt;}、{@code &gt;}、{@code &quot;}、{@code &apos;}。
     * 替换只发生在原文字符上，写出的实体不会被再次转义。
     * 其余字符（包括非 ASCII）原样保留。没有这些字符时返回原字符串。不修改入参，也不加外层标签或引号。
     *
     * @param text 原文，允许为 null
     * @return 转义后的文本；text 为 null 时返回空串
     */
    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder out = null;
        for (int i = 0; i < text.length(); i++) {
            String replacement = replacement(text.charAt(i));
            if (replacement == null) {
                if (out != null) {
                    out.append(text.charAt(i));
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
     * 还原 5 个 XML 预定义实体，以及十进制 {@code &#...;} 和十六进制 {@code &#x...;} 数字字符引用。
     * text 为 null 时返回空串。没有 {@code &} 时返回原字符串。
     * 命名实体只接受 {@code amp}、{@code lt}、{@code gt}、{@code quot}、{@code apos}，区分大小写。
     * 十六进制引用的 {@code x} 必须是小写；十六进制数字本身不区分大小写。允许前导零。
     * 数字引用接受 Unicode 标量值，范围是 U+0000 到 U+10FFFF，不包括代理项 U+D800 到 U+DFFF。
     * 超出该范围的值、代理项、非数字、空数字、未知命名实体、缺少分号或其他不完整引用抛出 {@link IllegalArgumentException}，不返回截断结果。
     * 只还原一层，因此 {@code &amp;lt;} 得到 {@code &lt;}。
     * 对 {@link #escape(String)} 的结果，{@code unescape(escape(text))} 回到原文；text 为 null 时两边都是空串。
     *
     * @param text 转义文本，允许为 null
     * @return 还原后的文本；text 为 null 时返回空串
     */
    public static String unescape(String text) {
        if (text == null) {
            return "";
        }
        int amp = text.indexOf('&');
        if (amp < 0) {
            return text;
        }
        StringBuilder out = new StringBuilder(text.length());
        out.append(text, 0, amp);
        for (int i = amp; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current != '&') {
                out.append(current);
                continue;
            }
            int semi = text.indexOf(';', i + 1);
            if (semi < 0) {
                throw new IllegalArgumentException("incomplete entity");
            }
            appendEntity(out, text, i, semi);
            i = semi;
        }
        return out.toString();
    }

    private static void appendEntity(StringBuilder out, String text, int amp, int semi) {
        if (semi == amp + 1) {
            throw new IllegalArgumentException("unknown entity");
        }
        if (text.charAt(amp + 1) == '#') {
            appendNumeric(out, text, amp + 2, semi);
            return;
        }
        String name = text.substring(amp + 1, semi);
        if ("amp".equals(name)) {
            out.append('&');
        } else if ("lt".equals(name)) {
            out.append('<');
        } else if ("gt".equals(name)) {
            out.append('>');
        } else if ("quot".equals(name)) {
            out.append('"');
        } else if ("apos".equals(name)) {
            out.append('\'');
        } else {
            throw new IllegalArgumentException("unknown entity");
        }
    }

    private static void appendNumeric(StringBuilder out, String text, int start, int semi) {
        if (start >= semi) {
            throw new IllegalArgumentException("illegal numeric entity");
        }
        int base = 10;
        if (text.charAt(start) == 'x') {
            base = 16;
            start++;
        }
        if (start >= semi) {
            throw new IllegalArgumentException("illegal numeric entity");
        }
        int value = 0;
        for (int i = start; i < semi; i++) {
            int digit = base == 16 ? hexDigit(text.charAt(i)) : decimalDigit(text.charAt(i));
            if (digit < 0) {
                throw new IllegalArgumentException("illegal numeric entity");
            }
            if (value > (0x10FFFF - digit) / base) {
                throw new IllegalArgumentException("illegal code point");
            }
            value = value * base + digit;
        }
        if (value >= 0xD800 && value <= 0xDFFF) {
            throw new IllegalArgumentException("illegal code point");
        }
        out.appendCodePoint(value);
    }

    private static String replacement(char current) {
        if (current == '&') {
            return "&amp;";
        }
        if (current == '<') {
            return "&lt;";
        }
        if (current == '>') {
            return "&gt;";
        }
        if (current == '"') {
            return "&quot;";
        }
        if (current == '\'') {
            return "&apos;";
        }
        return null;
    }

    private static int decimalDigit(char current) {
        if (current >= '0' && current <= '9') {
            return current - '0';
        }
        return -1;
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
