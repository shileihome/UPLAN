package com.uplan.miyao.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by cfp on 16-10-20.
 */

public class MathUtils {


    /**
     * 安全的String to Double
     *
     * @param str
     * @return
     */

    public static double parseDoubleSafely(String str) {
        double result = 0;
        try {
            result = Double.parseDouble(str);
        } catch (NullPointerException npe) {
        } catch (NumberFormatException nfe) {
        }
        return result;
    }


    /**
     * 安全的String to int
     *
     * @param str
     * @return
     */
    public static int parseIntSafely(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (NullPointerException npe) {
        } catch (NumberFormatException nfe) {
        }
        return result;
    }


    /**
     * 安全的String to float
     *
     * @param str
     * @return
     */

    public static float parseFloatSafely(String str) {
        float result = 0;
        try {
            result = Float.parseFloat(str);
        } catch (NullPointerException npe) {
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    /**
     * 去除后面的0,最多两位小数
     *
     * @param value 数字
     * @return 去除0后
     */
    public static String stripTrailingZeros(double value){
        DecimalFormat format = new DecimalFormat("0.##");
        return format.format(value);
    }

    /**
     * 去除后面的0,最多两位小数
     *
     * @param value 数字
     * @return 去除0后
     */
    public static String stripTrailingZeros(String value){
        return stripTrailingZeros(parseDoubleSafely(value));
    }

    /**
     * 当value > 10000时,返回以万为单位的值, 否则返回原值
     * @param value
     * @return
     */
    public static String transferY2WY(String value){

        Double d = parseDoubleSafely(value);
        if(d > 10000){
            DecimalFormat df = new DecimalFormat("#.####");
            return df.format(d / 10000);
        }
        return value;
    }


    /**
     * 当value > 10000时,返回以万为单位的值(例如:10000, 返回"1万"), 否则返回原值
     * @param value
     * @return
     */
    public static String transferY2WYJoinW(String value){

        Double d = parseDoubleSafely(value);
        if(d > 10000){
            DecimalFormat df = new DecimalFormat("#.####");
            return df.format(d / 10000) + "万";
        }
        return value;
    }

    /**
     * 保留小数
     * @param scale　保留几位小数
     * @param roundingMode　模式
     * @param value　结果
     * @return
     */
    public static double setScale(int scale, int roundingMode, double value){
        BigDecimal bd=new BigDecimal(value);
        return bd.setScale(scale,roundingMode).doubleValue();
    }

    /**
     * 限制输入密码的格式
     *
     * @param str 输入密码
     * @return boolean true:位数通过 false:位数不通过
     */
    public static boolean setInputPwd(String str) {
        return !(str == null || str.length() < 6 || str.length() > 20);
    }

    /**
     * 浮点类型数据为整数时，去掉小数点和其后的0
     *
     * @param resp 后台返回json数据
     * @return 处理后的数据
     */
    public static String respDataFormat(String resp){
        if(TextUtils.isEmpty(resp)){
            return "";
        }

        //不是数字时，原数据返回
        if(!RegexUtils.checkDigit(resp)){
            return resp;
        }

        double d = Double.parseDouble(resp);
        if(Math.round(d)-d==0){
            return String.valueOf((long)d);
        }
        return String.valueOf(d);
    }
}
