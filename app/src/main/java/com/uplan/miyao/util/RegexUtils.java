package com.uplan.miyao.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : cfp
 *     e-mail : chengfangpeng@foxmail.com
 *     time   : 2017/10/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RegexUtils {

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     * @author ：shijing
     * 2016年12月5日下午4:34:46
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /**
     * 验证手机号码
     * @param phone 手机号
     * @param prompt 提示
     */

    public static boolean isMobile(String phone,String prompt)
    {
        if(phone.startsWith("1")&&phone.length()==11){
            return true;
        }else{
            ToastUtils.shortShow(prompt);
            return false;
        }
    }

    /**
     * 验证身份证号码
     * @param prompt 提示
     * @param idCard 身份证
     *
     * 18位的身份证正则：
    [1-9]\d{5}                 前六位地区，非0打头
    (18|19|([23]\d))\d{2}      出身年份，覆盖范围为 1800-3999 年
    ((0[1-9])|(10|11|12))      月份，01-12月
    (([0-2][1-9])|10|20|30|31) 日期，01-31天
    \d{3}[0-9Xx]：              顺序码三位 + 一位校验码

    15位的身份证：
    [1-9]\d{5}                  前六位地区，非0打头
    \d{2}                       出生年份后两位00-99
    ((0[1-9])|(10|11|12))       月份，01-12月
    (([0-2][1-9])|10|20|30|31)  日期，01-31天
    \d{3}                       顺序码三位，没有校验码

     *
     */

    public static boolean isIdNumber(String idCard,String prompt)
    {

        /*
        十五位身份证验证
        * else if(idCard.length()==15){

            String regex = "[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}";

            if(Pattern.matches(regex,idCard)){
                return  true;
            }else{
                ToastUtils.shortShow(prompt);
                return false;
            }

        }
        * */

        if(idCard.length()==18){
            String regex = "[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]";

            if(Pattern.matches(regex,idCard)){
                return  true;
            }else{
                ToastUtils.shortShow(prompt);
                return false;
            }
        }else{
            ToastUtils.shortShow(prompt);
            return false;
        }

    }
    /**
     * 验证银行卡号
     * @param bank 银行卡号
     * @param prompt 提示
     */

    public static boolean isBankNum(String bank,String prompt)
    {
        if(bank.length()>=16&&bank.length()<=19){
            return true;
        }else{
            ToastUtils.shortShow(prompt);
            return false;
        }
    }
    /**
     * 验证是否是数字
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

    /**
     * 验证当前字符串是否含有数字
     *
     * @param str 字符串
     * @return true: 含有数字 false: 不含有数字
     */
    public static boolean hasDigit(String str) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 截取非数字的字符串
     *
     * @param str 字符串
     * @return 去除数字后的字符串
     */
    public static String splitNotNumber(String str) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 验证银行卡号
     *
     * @param bank 银行卡号
     */
    public static boolean checkBank(String bank) {
        if (bank.length() >= 16 && bank.length() <= 19) {
            return true;
        } else {
            return false;
        }
    }
}
