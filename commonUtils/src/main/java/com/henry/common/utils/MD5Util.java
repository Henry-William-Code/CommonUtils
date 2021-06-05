package com.henry.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * =============================================
 * 时间：2021-06-05 19:40 </br>
 * 作者：龚鹏</br>
 * 描述：MD5Util操作相关的工具类
 * ============================================
 */
public class MD5Util {

    /**
     * MD5多次加密
     *
     * @param original 原文
     * @param times    加密次数
     * @return 密文
     */
    public static String addMD5Times(String original, int times) {
        if (StringUtil.isEmpty(original)) {
            return "";
        }
        for (int i = 0; i < times; i++) {
            original = addMD5(original);
        }
        return original;
    }

    /**
     * MD5 加密2次
     *
     * @param original 原文
     * @return 密文
     */
    public static String addMD5Twice(String original) {
        return addMD5Times(original, 2);
    }

    /**
     * MD5 加密10次
     *
     * @param original 原文
     * @return 密文
     */
    public static String addMD5Ten(String original) {
        return addMD5Times(original, 10);
    }


    /**
     * MD5加密
     *
     * @param original 原文
     * @return MD5密文
     */
    public static String addMD5(String original) {
        if (StringUtil.isEmpty(original)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(original.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
