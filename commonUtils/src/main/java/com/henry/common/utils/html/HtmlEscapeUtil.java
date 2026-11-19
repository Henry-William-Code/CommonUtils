package com.henry.common.utils.html;

/**
 * HTML 文本转义。只处理五个字符，不删除标签，也不解析任意 HTML。
 * 与 {@code WebUtil.removeHtmlTag} 不同：本类保留字符，只换成实体。
 * 不使用 Android API。
 */
public class HtmlEscapeUtil {

    private HtmlEscapeUtil() {
    }

    /**
     * 转义 HTML 文本中的 {@code & < > " '}。
     * text 为 null 时返回空串。没有这些字符时返回原字符串。
     * 对应实体是 {@code &amp; &lt; &gt; &quot; &#39;}。先转义 {@code &}，因此不会把已写出的实体再次转成标签。
     * 不转义其他字符，也不识别标签。
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
     * 还原 {@link #escape(String)} 写出的实体。
     * text 为 null 时返回空串。只识别 {@code &amp; &lt; &gt; &quot; &#39; &apos;}。
     * 先还原除 {@code &amp;} 以外的实体，最后还原 {@code &amp;}，因此 {@code &amp;lt;} 还原成 {@code &lt;} 而不是 {@code <}。
     * 不认识的实体、缺少分号的 {@code &}，以及 {@code &#60;} 这类其他数字实体都原样保留。
     * 不删除标签。
     *
     * @param text 转义文本，允许为 null
     * @return 还原后的文本；text 为 null 时返回空串
     */
    public static String unescape(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder out = null;
        int index = 0;
        while (index < text.length()) {
            int amp = text.indexOf('&', index);
            if (amp < 0) {
                break;
            }
            int semi = text.indexOf(';', amp + 1);
            String replacement = semi < 0 ? null : entity(text.substring(amp, semi + 1));
            if (replacement == null) {
                if (out != null) {
                    int end = semi < 0 ? text.length() : semi + 1;
                    out.append(text, index, end);
                }
                if (semi < 0) {
                    index = text.length();
                    break;
                }
                index = semi + 1;
                continue;
            }
            if (out == null) {
                out = new StringBuilder(text.length());
                out.append(text, 0, amp);
            } else {
                out.append(text, index, amp);
            }
            out.append(replacement);
            index = semi + 1;
        }
        if (out == null) {
            return text;
        }
        out.append(text, index, text.length());
        return out.toString();
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
            return "&#39;";
        }
        return null;
    }

    private static String entity(String token) {
        if ("&amp;".equals(token)) {
            return "&";
        }
        if ("&lt;".equals(token)) {
            return "<";
        }
        if ("&gt;".equals(token)) {
            return ">";
        }
        if ("&quot;".equals(token)) {
            return "\"";
        }
        if ("&#39;".equals(token) || "&apos;".equals(token)) {
            return "'";
        }
        return null;
    }
}
