package com.henry.common.utils.string;

/**
 * ============================================
 * 时间：2021-06-05 16:44 <br>
 * 作者：龚鹏<br>
 * 描述： Android中文字处理工具类
 * ============================================
 */
public class StringUtil {


    /**
     * 判断字符串是否为空
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return null == string || string.length() < 1;
    }


    /**
     * 字符串输出空字符，也就是""</br>
     * 如果不是空的话，就原样输出</br>
     * 是空的话，就输出""</br>
     * 因为本人遇到过很多次，需要判断是不是null，，很麻烦
     * @param string
     * @return
     */
    public static String outPrintEmptyString(String string) {
        if (isEmpty(string)) {
            return "";
        }
        return string;
    }

    /**
     * 判断字符串是否为 null、空串，或只含空白字符。
     * 空白按 {@link Character#isWhitespace(char)} 判断，因此空格、制表符、换行属于空白，
     * 不间断空格 {@code \u00A0} 不属于空白。
     * 与 {@link #isEmpty(String)} 不同：仅空白时本方法返回 true。
     *
     * @param string 允许为 null
     * @return null、空串或仅空白时返回 true
     */
    public static boolean isBlank(String string) {
        if (string == null || string.length() < 1) {
            return true;
        }
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 去掉首尾空白；null 时返回空串。
     * 空白规则与 {@link #isBlank(String)} 相同，使用 {@link Character#isWhitespace(char)}。
     * 不间断空格 {@code \u00A0} 会保留。中间的空白不删除。
     * 不修改 {@link #outPrintEmptyString(String)} 和 {@link #defaultIfBlank(String, String)}。
     *
     * @param text 允许为 null
     * @return 去掉首尾空白后的文本；null 时返回空串
     */
    public static String trimToEmpty(String text) {
        if (text == null) {
            return "";
        }
        int start = 0;
        int end = text.length();
        while (start < end && Character.isWhitespace(text.charAt(start))) {
            start++;
        }
        while (end > start && Character.isWhitespace(text.charAt(end - 1))) {
            end--;
        }
        if (start == 0 && end == text.length()) {
            return text;
        }
        return text.substring(start, end);
    }

    /**
     * 判断 text 是否以 prefix 开头。
     * text 或 prefix 为 null 时返回 false。prefix 为空串且 text 不是 null 时返回 true，包括 text 也是空串。
     * 区分大小写，不去掉空白。不修改 {@link #equals(String, String)}。
     *
     * @param text   原文，允许为 null
     * @param prefix 前缀，允许为 null
     * @return 以前缀开头时返回 true
     */
    public static boolean startsWith(String text, String prefix) {
        if (text == null || prefix == null) {
            return false;
        }
        return text.startsWith(prefix);
    }

    /**
     * 判断 text 是否以 prefix 开头，只忽略 ASCII 大小写。
     * text 或 prefix 为 null 时返回 false。prefix 为空串且 text 不是 null 时返回 true，包括 text 也是空串。
     * 只把 {@code A-Z} 看成与 {@code a-z} 相同，不处理 Unicode 大小写，也不去掉空白。
     * 不修改 {@link #startsWith(String, String)}。
     *
     * @param text   原文，允许为 null
     * @param prefix 前缀，允许为 null
     * @return 忽略 ASCII 大小写后以前缀开头时返回 true
     */
    public static boolean startsWithIgnoreCase(String text, String prefix) {
        if (text == null || prefix == null) {
            return false;
        }
        if (prefix.length() > text.length()) {
            return false;
        }
        return regionEqualsAsciiIgnoreCase(text, 0, prefix);
    }

    /**
     * 判断 text 是否以 suffix 结尾。
     * 规则与 {@link #startsWith(String, String)} 对称。
     * text 或 suffix 为 null 时返回 false。suffix 为空串且 text 不是 null 时返回 true，包括 text 也是空串。
     * 区分大小写，不去掉空白。
     *
     * @param text   原文，允许为 null
     * @param suffix 后缀，允许为 null
     * @return 以后缀结尾时返回 true
     */
    public static boolean endsWith(String text, String suffix) {
        if (text == null || suffix == null) {
            return false;
        }
        return text.endsWith(suffix);
    }

    /**
     * 判断 text 是否以 suffix 结尾，只忽略 ASCII 大小写。
     * 规则与 {@link #startsWithIgnoreCase(String, String)} 对称。
     * text 或 suffix 为 null 时返回 false。suffix 为空串且 text 不是 null 时返回 true，包括 text 也是空串。
     * 只把 {@code A-Z} 看成与 {@code a-z} 相同，不处理 Unicode 大小写，也不去掉空白。
     * 不修改 {@link #endsWith(String, String)} 和 {@link #startsWithIgnoreCase(String, String)}。
     *
     * @param text   原文，允许为 null
     * @param suffix 后缀，允许为 null
     * @return 忽略 ASCII 大小写后以后缀结尾时返回 true
     */
    public static boolean endsWithIgnoreCase(String text, String suffix) {
        if (text == null || suffix == null) {
            return false;
        }
        if (suffix.length() > text.length()) {
            return false;
        }
        return regionEqualsAsciiIgnoreCase(text, text.length() - suffix.length(), suffix);
    }

    /**
     * 判断 text 是否包含 search。
     * text 或 search 为 null 时返回 false。search 为空串且 text 不是 null 时返回 true，包括 text 也是空串。
     * 区分大小写，不去掉空白。不修改 {@link #startsWith(String, String)} 和 {@link #endsWith(String, String)}。
     *
     * @param text   原文，允许为 null
     * @param search 要查找的文本，允许为 null
     * @return 包含时返回 true
     */
    public static boolean contains(String text, String search) {
        if (text == null || search == null) {
            return false;
        }
        return text.indexOf(search) >= 0;
    }

    /**
     * 判断 text 是否包含 search，只忽略 ASCII 大小写。
     * text 或 search 为 null 时返回 false。search 为空串且 text 不是 null 时返回 true，包括 text 也是空串。
     * 只把 {@code A-Z} 看成与 {@code a-z} 相同，不处理 Unicode 大小写，也不去掉空白。
     * 已有的 {@link #contains(String, String)} 仍然区分大小写。
     *
     * @param text   原文，允许为 null
     * @param search 要查找的文本，允许为 null
     * @return 忽略 ASCII 大小写后包含时返回 true
     */
    public static boolean containsIgnoreCase(String text, String search) {
        if (text == null || search == null) {
            return false;
        }
        int window = search.length();
        if (window == 0) {
            return true;
        }
        int limit = text.length() - window;
        for (int i = 0; i <= limit; i++) {
            if (regionEqualsAsciiIgnoreCase(text, i, search)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较两个字符串的内容是否相同。
     * 两者都为 null 时返回 true，只有一方为 null 时返回 false。
     * 区分大小写，不去掉首尾空白。
     *
     * @param left  允许为 null
     * @param right 允许为 null
     * @return 内容相同或同为 null 时返回 true
     */
    public static boolean equals(String left, String right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }

    /**
     * 按 ASCII 大小写不敏感的方式比较两个字符串。
     * 两者都为 null 时返回 true，只有一方为 null 时返回 false。
     * 不去掉首尾空白。只把 {@code A-Z} 看成与 {@code a-z} 相同，不处理 Unicode 大小写。
     * 已有的 {@link #equals(String, String)} 仍然区分大小写。
     *
     * @param left  允许为 null
     * @param right 允许为 null
     * @return ASCII 忽略大小写后相同，或同为 null 时返回 true
     */
    public static boolean equalsIgnoreCase(String left, String right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null || left.length() != right.length()) {
            return false;
        }
        for (int i = 0; i < left.length(); i++) {
            char first = left.charAt(i);
            char second = right.charAt(i);
            if (first >= 'A' && first <= 'Z') {
                first = (char) (first + ('a' - 'A'));
            }
            if (second >= 'A' && second <= 'Z') {
                second = (char) (second + ('a' - 'A'));
            }
            if (first != second) {
                return false;
            }
        }
        return true;
    }

    /**
     * text 为空白时返回默认值，否则原样返回 text。
     * 空白规则与 {@link #isBlank(String)} 相同。defaultValue 允许为 null。
     * 非空白文本不去掉首尾空白。
     *
     * @param text         原文，允许为 null
     * @param defaultValue text 为空白时返回的值，允许为 null
     * @return 原文或默认值
     */
    public static String defaultIfBlank(String text, String defaultValue) {
        if (isBlank(text)) {
            return defaultValue;
        }
        return text;
    }

    /**
     * 把 text 重复 count 次后返回。
     * text 为 null、空串，或 count 小于 1 时返回空串。
     * 结果字符数大于 {@link Integer#MAX_VALUE} 时抛出 {@link IllegalArgumentException}，不返回截断字符串。
     *
     * @param text  被重复的文本，允许为 null
     * @param count 重复次数
     * @return 重复结果；无法重复时返回空串
     */
    public static String repeat(String text, int count) {
        if (text == null || text.length() < 1 || count < 1) {
            return "";
        }
        if (count == 1) {
            return text;
        }
        long total = (long) text.length() * (long) count;
        if (total > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("repeat length overflow");
        }
        StringBuilder builder = new StringBuilder((int) total);
        for (int i = 0; i < count; i++) {
            builder.append(text);
        }
        return builder.toString();
    }

    /**
     * 用 delimiter 连接 parts。
     * delimiter 为 null 时按空串处理。
     * parts 为 null 或没有元素时返回空串。
     * 某个元素为 null 时按空串参与拼接，保留这个位置，不写成 "null"。
     * 分隔符只放在元素之间。
     *
     * @param delimiter 分隔符，允许为 null
     * @param parts     待连接的文本，允许为 null
     * @return 连接后的字符串
     */
    public static String join(CharSequence delimiter, String... parts) {
        if (parts == null || parts.length == 0) {
            return "";
        }
        String separator = delimiter == null ? "" : delimiter.toString();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                builder.append(separator);
            }
            if (parts[i] != null) {
                builder.append(parts[i]);
            }
        }
        return builder.toString();
    }

    private static boolean regionEqualsAsciiIgnoreCase(String text, int offset, String search) {
        for (int i = 0; i < search.length(); i++) {
            char first = text.charAt(offset + i);
            char second = search.charAt(i);
            if (first >= 'A' && first <= 'Z') {
                first = (char) (first + ('a' - 'A'));
            }
            if (second >= 'A' && second <= 'Z') {
                second = (char) (second + ('a' - 'A'));
            }
            if (first != second) {
                return false;
            }
        }
        return true;
    }

}
