package com.henry.common.utils.calculate;

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


}
