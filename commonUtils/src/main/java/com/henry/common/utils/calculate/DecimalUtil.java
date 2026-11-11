package com.henry.common.utils.calculate;

import com.henry.common.utils.string.StringUtil;

/**
 * =============================================
 * 时间：2021-06-28 15:32 </br>
 * 作者：龚鹏</br>
 * 描述：进制相关的计算类
 * ============================================
 */
public class DecimalUtil {


    /**
     * 十进制的数以二进制形式进行输出</br>
     * 前面以0的方式补齐</br>
     * 如果不需要补齐的话，就可以调用系统的Integer.toBinaryString(int number)方法
     * @param number
     * @return
     */
    public static String toBinaryString(int number) {
        StringBuffer rst = new StringBuffer(32);
        for (int i = 0; i < 32; i++) {
            rst.append(number < 0 ? 1 : 0);
            number <<= 1;
        }
        return rst.toString();

    }



    /**
     * 二进制转十进制
     * @param number
     * @return
     */
    public static int binary2Decimal(String number) {
        return scale2Decimal(number, 2);
    }



    /**
     * 其他进制转十进制
     * @param number
     * @return
     */
    public static int scale2Decimal(String number, int scale) {
        checkNumber(number);
        if (2 > scale || scale > 32) {
            throw new IllegalArgumentException("scale is not in range");
        }
        // 不同其他进制转十进制,修改这里即可
        int total = 0;
        String[] ch = number.split("");
        int chLength = ch.length;
        for (int i = 0; i < chLength; i++) {
            total += Integer.valueOf(ch[i]) * Math.pow(scale, chLength - 1 - i);
        }
        return total;

    }


    /**
     * 检查数据是否有效
     * @param number
     */
    public static void checkNumber(String number) {
        String regexp = "^\\d+$";
        if (null == number || !number.matches(regexp)) {
            throw new IllegalArgumentException("input is not a number");
        }
    }

    /**
     * 把 int 的 32 个比特写成 8 位小写十六进制，高位用 0 补齐。
     * 按无符号比特解释，不使用十进制正负号。-1 得到 {@code ffffffff}，{@link Integer#MIN_VALUE} 得到 {@code 80000000}。
     * 不改变 {@link #toBinaryString(int)} 等已有方法。
     *
     * @param number 任意 int
     * @return 长度为 8 的小写十六进制文本
     */
    public static String toHexString(int number) {
        char[] out = new char[8];
        for (int i = 7; i >= 0; i--) {
            out[i] = HEX_DIGITS[number & 0xF];
            number >>>= 4;
        }
        return new String(out);
    }

    /**
     * 把十六进制文本按无符号 32 位比特解析成 int。
     * 可以带 {@code 0x} 或 {@code 0X} 前缀。前缀后的十六进制数字必须是 1 到 8 位，字母不区分大小写。
     * 超过 8 位时抛出 {@link IllegalArgumentException}，即使多出来的是前导 0。
     * null、空串或只含空白时抛出 {@link IllegalArgumentException}。不 trim 非空白文本，因此中间或两端夹着非十六进制字符也会抛异常。
     * 不接受正负号。{@code ffffffff} 得到 -1，{@code 80000000} 得到 {@link Integer#MIN_VALUE}。
     * 不改变 {@link #checkNumber(String)} 和 {@link #toHexString(int)}。
     *
     * @param hex 十六进制文本，允许为 null
     * @return 对应的 32 位 int
     */
    public static int parseHex(String hex) {
        if (StringUtil.isBlank(hex)) {
            throw new IllegalArgumentException("hex is empty");
        }
        int start = 0;
        if (hex.length() >= 2
                && hex.charAt(0) == '0'
                && (hex.charAt(1) == 'x' || hex.charAt(1) == 'X')) {
            start = 2;
        }
        int digits = hex.length() - start;
        if (digits < 1 || digits > 8) {
            throw new IllegalArgumentException("hex length is out of range");
        }
        int value = 0;
        for (int i = start; i < hex.length(); i++) {
            int digit = hexDigit(hex.charAt(i));
            if (digit < 0) {
                throw new IllegalArgumentException("hex digit is invalid");
            }
            value = (value << 4) | digit;
        }
        return value;
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

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };


}
