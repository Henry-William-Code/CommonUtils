package com.henry.common.utils.naming;

/**
 * 标识符的蛇形、驼峰和 PascalCase 转换。
 * 已发布的 1.0.7 没有这个类。不使用 Android API。
 * 只在 ASCII 小写字母或数字后面紧跟 ASCII 大写字母时分段，连续大写不拆开。
 */
public class NamingUtil {

    private NamingUtil() {
    }

    /**
     * 把标识符转成小写蛇形。
     * {@code HelloWorld} 和 {@code helloWorld} 得到 {@code hello_world}。已经是 {@code hello_world} 的保持小写蛇形。
     * 连字符、下划线和空白是分隔符。只在「ASCII 小写或数字后面紧跟 ASCII 大写」处再插入下划线。
     * 连续大写留在同一段，例如 {@code XMLParser} 得到 {@code xmlparser}。
     * 连续分隔符合并为一个 {@code _}，并去掉首尾分隔符。null，或去掉分隔后没有内容时返回空串。
     * 只把 {@code A-Z} 转成小写，其他字符原样保留。
     *
     * @param text 标识符，允许为 null
     * @return 小写蛇形；没有内容时返回空串
     */
    public static String toSnakeCase(String text) {
        String[] parts = split(text);
        if (parts.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(text.length() + parts.length);
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                out.append('_');
            }
            appendLower(out, parts[i]);
        }
        return out.toString();
    }

    /**
     * 把标识符转成 lowerCamelCase。
     * {@code hello_world}、{@code hello-world} 和 {@code HelloWorld} 都得到 {@code helloWorld}。
     * 分段规则与 {@link #toSnakeCase(String)} 相同。第一段全小写，后续段首字母大写、其余小写。
     * 后续段以数字开头时，该数字保持不变，后面的 ASCII 字母转小写。
     * null，或去掉分隔后没有内容时返回空串。
     *
     * @param text 标识符，允许为 null
     * @return lowerCamelCase；没有内容时返回空串
     */
    public static String toCamelCase(String text) {
        String[] parts = split(text);
        if (parts.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(text.length());
        appendLower(out, parts[0]);
        for (int i = 1; i < parts.length; i++) {
            appendCapitalized(out, parts[i]);
        }
        return out.toString();
    }

    /**
     * 把标识符转成小写连字符形式。
     * 分段规则与 {@link #toSnakeCase(String)} 完全相同，只是用 {@code -} 连接。
     * {@code HelloWorld} 和 {@code helloWorld} 得到 {@code hello-world}。
     * 连字符、下划线和空白都是分隔符。连续分隔符合并为一个 {@code -}，并去掉首尾分隔符。
     * 连续大写留在同一段，例如 {@code XMLParser} 得到 {@code xmlparser}，{@code getHTTPResponse} 得到 {@code get-httpresponse}。
     * null，或去掉分隔后没有内容时返回空串。只把 {@code A-Z} 转成小写。
     * 不修改 {@link #toSnakeCase(String)} 和 {@link #toCamelCase(String)}。
     *
     * @param text 标识符，允许为 null
     * @return 小写连字符形式；没有内容时返回空串
     */
    public static String toKebabCase(String text) {
        String[] parts = split(text);
        if (parts.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(text.length() + parts.length);
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                out.append('-');
            }
            appendLower(out, parts[i]);
        }
        return out.toString();
    }

    /**
     * 把标识符转成 PascalCase。
     * 分段规则与 {@link #toCamelCase(String)}、{@link #toSnakeCase(String)} 完全相同，
     * 但每一段（含第一段）都是首字母大写、其余小写。
     * {@code hello_world}、{@code hello-world} 和 {@code HelloWorld} 都得到 {@code HelloWorld}。
     * 连续大写不拆开，因此 {@code XMLParser} 得到 {@code Xmlparser}，{@code getHTTPResponse} 得到 {@code GetHttpresponse}。
     * null，或去掉分隔后没有内容时返回空串。只处理 ASCII 大小写。
     * 不修改 {@link #toSnakeCase(String)}、{@link #toCamelCase(String)} 和 {@link #toKebabCase(String)}。
     *
     * @param text 标识符，允许为 null
     * @return PascalCase；没有内容时返回空串
     */
    public static String toPascalCase(String text) {
        String[] parts = split(text);
        if (parts.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < parts.length; i++) {
            appendCapitalized(out, parts[i]);
        }
        return out.toString();
    }

    private static String[] split(String text) {
        if (text == null || text.length() == 0) {
            return new String[0];
        }
        String[] parts = new String[text.length()];
        int count = 0;
        int start = -1;
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (isSeparator(current)) {
                if (start >= 0) {
                    parts[count++] = text.substring(start, i);
                    start = -1;
                }
                continue;
            }
            if (start >= 0 && isUpper(current) && isLowerOrDigit(text.charAt(i - 1))) {
                parts[count++] = text.substring(start, i);
                start = i;
                continue;
            }
            if (start < 0) {
                start = i;
            }
        }
        if (start >= 0) {
            parts[count++] = text.substring(start);
        }
        if (count == parts.length) {
            return parts;
        }
        String[] out = new String[count];
        System.arraycopy(parts, 0, out, 0, count);
        return out;
    }

    private static void appendLower(StringBuilder out, String part) {
        for (int i = 0; i < part.length(); i++) {
            out.append(toLower(part.charAt(i)));
        }
    }

    private static void appendCapitalized(StringBuilder out, String part) {
        char first = part.charAt(0);
        out.append(toUpper(first));
        for (int i = 1; i < part.length(); i++) {
            out.append(toLower(part.charAt(i)));
        }
    }

    private static boolean isSeparator(char current) {
        return current == '-' || current == '_' || Character.isWhitespace(current);
    }

    private static boolean isUpper(char current) {
        return current >= 'A' && current <= 'Z';
    }

    private static boolean isLowerOrDigit(char current) {
        return (current >= 'a' && current <= 'z') || (current >= '0' && current <= '9');
    }

    private static char toLower(char current) {
        if (current >= 'A' && current <= 'Z') {
            return (char) (current + ('a' - 'A'));
        }
        return current;
    }

    private static char toUpper(char current) {
        if (current >= 'a' && current <= 'z') {
            return (char) (current - ('a' - 'A'));
        }
        return current;
    }
}
