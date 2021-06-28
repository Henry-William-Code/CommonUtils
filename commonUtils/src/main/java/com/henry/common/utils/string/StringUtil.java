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

}
