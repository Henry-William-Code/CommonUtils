package com.henry.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * 时间：2019-09-02 16:44 <br>
 * 作者：龚鹏<br>
 * 描述：系统工具类
 */
public class SystemUtil {



    /**
     * 获取当前应用的版本号
     *
     * @param context 内容上下文
     * @return
     */
    public static int getPackageVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        int versionCode = 1;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * 获取当前应用的版本名</br>
     *  默认版本名为  1.0.0
     * @param context 内容上下文
     * @return
     */
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = "1.0.0";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * 获取当前手机的系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机型号
     *
     * @return
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param context  内容上下文
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }



    /**
     * 设置控制宽度（占屏幕几分之几）
     *
     * @param context 内容上下文
     * @param view    控件
     * @param number  分子
     * @param count   分母
     */
    public static void settingsWidgetWidth(Context context, View view, int number, int count) {
        //1、通过Resources获取   最好用这个方法获取屏幕宽高，用其它的方法，又不是在activity中的话，会获取值为0
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.width = width * number / count;
        view.setLayoutParams(params);

    }

    /**
     * 设置控制宽度（占屏幕几分之几）
     *
     * @param context 内容上下文
     * @param view    控件
     * @param number  分子
     * @param count   分母
     */
    public static void settingsWidgetWidth2(Context context, View view, int number, int count) {
        //1、通过Resources获取   最好用这个方法获取屏幕宽高，用其它的方法，又不是在activity中的话，会获取值为0
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.width = width * number / count;
        view.setLayoutParams(params);

    }

    /**
     * 设置控制宽度（占屏幕几分之几）
     *
     * @param context 内容上下文
     * @param view    控件
     * @param number  分子
     * @param count   分母
     */
    public static void settingsWidgetHeight(Context context, View view, int number, int count) {
        //1、通过Resources获取   最好用这个方法获取屏幕宽高，用其它的方法，又不是在activity中的话，会获取值为0
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = height * number / count;
        view.setLayoutParams(params);

    }
    /**
     * 设置控制宽度（占屏幕几分之几）
     *
     * @param context 内容上下文
     * @param view    控件
     * @param number  分子
     * @param count   分母
     */
    public static void settingsWidgetHeight2(Context context, View view, int number, int count) {
        //1、通过Resources获取   最好用这个方法获取屏幕宽高，用其它的方法，又不是在activity中的话，会获取值为0
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = height * number / count;
        view.setLayoutParams(params);

    }

    /**
     * 省份证的正则表达式^(\d{15}|\d{17}[\dx])$
     *
     * @param id 省份证号
     * @return 生日（yyyy-MM-dd）
     */
    public static String extractYearMonthDayOfIdCard(String id) {
        String year = null;
        String month = null;
        String day = null;
        //正则匹配身份证号是否是正确的，15位或者17位数字+数字/x/X
        if (id.matches("^\\d{15}|\\d{17}[\\dxX]$")) {
            year = id.substring(6, 10);
            month = id.substring(10, 12);
            day = id.substring(12, 14);
        } else {
            System.out.println("身份证号码不匹配！");
            return "";
        }
        return year + "-" + month + "-" + day;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取随机数
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max)%(max-min+1) + min;
    }



    /**
     * 判断是否是平板 * 谷歌源码（尺寸，分辨率，能否打电话都是浮云）
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}