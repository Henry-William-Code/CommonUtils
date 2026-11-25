package com.henry.common.utils.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * 单行 CSV 记录。分隔符是逗号，引号是 {@code "}。
 * 已发布的 1.0.8 没有这个类。不读写文件，也不加 BOM。
 */
public class CsvUtil {

    private CsvUtil() {
    }

    /**
     * 把字段转义后用逗号连接成一条记录。
     * fields 为 null 或长度为 0 时返回空串。单个字段为 null 时按空字段处理。
     * 字段里出现逗号、双引号、{@code \r} 或 {@code \n} 时用双引号包裹，内部的 {@code "} 写成 {@code ""}。
     * 其他字段原样输出，不 trim。不加前后换行，也不加 BOM。
     * {@code joinLine()} 和 {@code joinLine("")} 都返回空串，而 {@link #splitLine(String)} 把空串解析成一个空字段。
     *
     * @param fields 字段，允许为 null
     * @return 一条 CSV 记录；没有字段时返回空串
     */
    public static String joinLine(String... fields) {
        if (fields == null || fields.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                out.append(',');
            }
            appendField(out, fields[i] == null ? "" : fields[i]);
        }
        return out.toString();
    }

    /**
     * 解析一条 CSV 记录。
     * line 为 null 时返回长度为 0 的新数组。
     * 末尾恰好是 {@code \r\n}、{@code \n} 或 {@code \r} 时，只去掉这一个记录结束符，不再 trim 其他空白。
     * 空串，以及去掉结束符后变成空串的文本，解析为只有一个空字段的数组。
     * 因此 {@code splitLine("")} 是 {@code [""]}，不是长度为 0 的数组。
     * 引号字段内的逗号、换行，以及 {@code ""} 表示的字面引号，都属于字段内容。
     * 未闭合引号、未加引号字段中出现单独 {@code "}、关闭引号之后在逗号或结尾之前还有内容时，
     * 抛出 {@link IllegalArgumentException}，不返回截断结果。
     * 不删除未加引号字段两侧的空格。返回新数组。
     *
     * @param line 一条记录，允许为 null
     * @return 字段数组；line 为 null 时长度为 0
     */
    public static String[] splitLine(String line) {
        if (line == null) {
            return new String[0];
        }
        String record = stripRecordSeparator(line);
        List<String> fields = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < record.length(); i++) {
            char currentChar = record.charAt(i);
            if (quoted) {
                if (currentChar != '"') {
                    current.append(currentChar);
                    continue;
                }
                if (i + 1 < record.length() && record.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                    continue;
                }
                quoted = false;
                if (i + 1 < record.length() && record.charAt(i + 1) != ',') {
                    throw new IllegalArgumentException("csv quote is not followed by a comma");
                }
                continue;
            }
            if (currentChar == ',') {
                fields.add(current.toString());
                current.setLength(0);
                continue;
            }
            if (currentChar == '"') {
                if (current.length() != 0) {
                    throw new IllegalArgumentException("csv quote is inside an unquoted field");
                }
                quoted = true;
                continue;
            }
            current.append(currentChar);
        }
        if (quoted) {
            throw new IllegalArgumentException("csv quote is not closed");
        }
        fields.add(current.toString());
        return fields.toArray(new String[fields.size()]);
    }

    private static void appendField(StringBuilder out, String field) {
        if (!needsQuotes(field)) {
            out.append(field);
            return;
        }
        out.append('"');
        for (int i = 0; i < field.length(); i++) {
            char current = field.charAt(i);
            if (current == '"') {
                out.append('"');
            }
            out.append(current);
        }
        out.append('"');
    }

    private static boolean needsQuotes(String field) {
        for (int i = 0; i < field.length(); i++) {
            char current = field.charAt(i);
            if (current == ',' || current == '"' || current == '\r' || current == '\n') {
                return true;
            }
        }
        return false;
    }

    private static String stripRecordSeparator(String line) {
        int length = line.length();
        if (length >= 2 && line.charAt(length - 2) == '\r' && line.charAt(length - 1) == '\n') {
            return line.substring(0, length - 2);
        }
        if (length >= 1) {
            char last = line.charAt(length - 1);
            if (last == '\n' || last == '\r') {
                return line.substring(0, length - 1);
            }
        }
        return line;
    }
}
