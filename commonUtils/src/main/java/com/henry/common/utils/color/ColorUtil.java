package com.henry.common.utils.color;

/**
 * 十六进制颜色。只接受带 {@code #} 的 RGB、ARGB、RRGGBB、AARRGGBB。
 * 不识别 {@code red}、{@code black} 等颜色名，也不解析 {@code rgb()} 或 {@code argb()}。
 * 不加新依赖，也不使用 Android API。已发布的 1.0.9 没有这个类。
 */
public class ColorUtil {

    private static final char[] HEX = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private ColorUtil() {
    }

    /**
     * 把十六进制颜色解析成 ARGB 的 32 位 int，alpha 在最高字节。
     * 接受 {@code #RGB}、{@code #ARGB}、{@code #RRGGBB}、{@code #AARRGGBB}。{@code #} 必须有，十六进制不区分大小写。
     * {@code #RGB} 扩展为不透明的 {@code #FFRRGGBB}，每个半字节重复一次，例如 {@code #0F8} 是 {@code 0xFF00FF88}。
     * {@code #ARGB} 扩展为 {@code #AARRGGBB}，每个半字节重复一次，例如 {@code #80F8} 是 {@code 0x8800FF88}。
     * {@code #RRGGBB} 的 alpha 是 {@code FF}。{@code #000} 是 {@code 0xFF000000}，不是 0。
     * 不 trim。前后空格、缺 {@code #}、{@code 0x} 前缀、长度不是 3/4/6/8、非十六进制字符，以及 null 或空串，
     * 都抛出 {@link IllegalArgumentException}，不返回 0，也不截断。
     *
     * @param text 带 {@code #} 的十六进制颜色，不能为 null
     * @return ARGB int
     */
    public static int parse(String text) {
        if (text == null || text.length() == 0 || text.charAt(0) != '#') {
            throw new IllegalArgumentException("color is invalid");
        }
        int hexLength = text.length() - 1;
        if (hexLength != 3 && hexLength != 4 && hexLength != 6 && hexLength != 8) {
            throw new IllegalArgumentException("color is invalid");
        }
        for (int i = 1; i < text.length(); i++) {
            if (hexValue(text.charAt(i)) < 0) {
                throw new IllegalArgumentException("color is invalid");
            }
        }
        String expanded = expand(text);
        int value = 0;
        for (int i = 0; i < expanded.length(); i++) {
            value = (value << 4) | hexValue(expanded.charAt(i));
        }
        return value;
    }

    /**
     * 把 ARGB int 写成大写 {@code #AARRGGBB}，共 9 个字符，alpha 不省略。
     * {@code toHex(0)} 是 {@code #00000000}，不是 {@code #FF000000}。
     * 对任意 int {@code n}，{@code parse(toHex(n))} 等于 n。
     *
     * @param color ARGB int
     * @return 9 个字符的大写十六进制颜色
     */
    public static String toHex(int color) {
        char[] out = new char[9];
        out[0] = '#';
        for (int i = 0; i < 8; i++) {
            int shift = (7 - i) * 4;
            out[i + 1] = HEX[(color >>> shift) & 0xf];
        }
        return new String(out);
    }

    private static String expand(String text) {
        int hexLength = text.length() - 1;
        if (hexLength == 8) {
            return text.substring(1);
        }
        StringBuilder out = new StringBuilder(8);
        if (hexLength == 6) {
            out.append("FF");
            out.append(text, 1, text.length());
            return out.toString();
        }
        if (hexLength == 3) {
            out.append("FF");
        }
        for (int i = 1; i < text.length(); i++) {
            char current = text.charAt(i);
            out.append(current);
            out.append(current);
        }
        return out.toString();
    }

    private static int hexValue(char current) {
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
