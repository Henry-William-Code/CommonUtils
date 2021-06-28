package com.henry.common.utils.calculate;

/**
 * =============================================
 * 时间：2021-06-28 19:21 </br>
 * 作者：龚鹏</br>
 * 描述：数学相关的计算类
 * ============================================
 */
public class MathematicsUtil {



    /**
     * 数学中次幂计算
     *
     * @param number 被计算的数据
     * @param times  次幂的大小
     * @return
     */
    public static int power(int number, int times) {
        if (times < 1) {
            return 0;
        } else if (times == 1) {
            return number;
        } else {
            return number * power(number, times - 1);
        }
    }
}
