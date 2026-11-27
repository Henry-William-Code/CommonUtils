package com.henry.common.utils.luhn;

/**
 * 标准 Luhn 校验。从右往左，偶数位（从右数第 2、4、… 位）乘 2，乘积大于 9 则减 9，再把所有位相加。
 * 和能被 10 整除时校验通过。只处理十进制数字串，不是 CRC，也不放进 {@code Crc32Util}。
 * 不使用 Android API。
 */
public class LuhnUtil {

    private LuhnUtil() {
    }

    /**
     * 判断数字串是否通过 Luhn 校验。
     * 只接受十进制数字字符。null、空串、长度小于 2，或含任何非数字（空格、连字符、字母等）都返回 false。
     * 不抛异常，也不 trim。长度至少为 2 才可能返回 true。
     *
     * @param digits 待校验的数字串，允许为 null
     * @return 通过 Luhn 校验时返回 true
     */
    public static boolean isValid(String digits) {
        if (digits == null || digits.length() < 2) {
            return false;
        }
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = digits.length() - 1; i >= 0; i--) {
            int value = digit(digits.charAt(i));
            if (value < 0) {
                return false;
            }
            if (doubleDigit) {
                value = doubleAndFold(value);
            }
            sum += value;
            doubleDigit = !doubleDigit;
        }
        return sum % 10 == 0;
    }

    /**
     * 计算使 {@code isValid(payload + 该数字)} 为 true 的校验位。
     * 对任意仅含数字且非空的 payload，{@code isValid(payload + String.valueOf(checkDigit(payload)))} 为 true。
     * payload 为 null、空串，或含非数字时抛出 {@link IllegalArgumentException}。不 trim。
     *
     * @param payload 不含校验位的数字串，必须非空且只含十进制数字
     * @return 0 到 9 的校验位
     */
    public static int checkDigit(String payload) {
        if (payload == null || payload.length() == 0) {
            throw new IllegalArgumentException("payload is invalid");
        }
        int sum = 0;
        boolean doubleDigit = true;
        for (int i = payload.length() - 1; i >= 0; i--) {
            int value = digit(payload.charAt(i));
            if (value < 0) {
                throw new IllegalArgumentException("payload is invalid");
            }
            if (doubleDigit) {
                value = doubleAndFold(value);
            }
            sum += value;
            doubleDigit = !doubleDigit;
        }
        return (10 - (sum % 10)) % 10;
    }

    private static int digit(char current) {
        if (current < '0' || current > '9') {
            return -1;
        }
        return current - '0';
    }

    private static int doubleAndFold(int value) {
        int doubled = value * 2;
        if (doubled > 9) {
            return doubled - 9;
        }
        return doubled;
    }
}
