package com.uplan.miyao.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zs on 2017/3/27.
 *
 * 常用Text工具类
 */

public class TextViewUtils {

    /**
     * 修改字体大小
     * 坐标规则：左闭右开
     *
     * @param view TextView
     * @param text 文案
     * @param start 开始位置
     * @param end 结束位置
     * @param textSize 字体大小 单位sp
     */
    public static void updateTextView(TextView view, String text, int start, int end, int textSize) {
        try {
            SpannableStringBuilder sSpan = new SpannableStringBuilder(text);
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(textSize, true);
            sSpan.setSpan(sizeSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            SpannableStringBuilder span = new SpannableStringBuilder(sSpan);
            view.setText(span);
        } catch (Exception e) {
            // ignore this exception
        }
    }

    /**
     * textview设置颜色
     * @param view
     * @param text　文案
     * @param start　开始位置
     * @param end　结束位置
     * @param color　字体大小 单位sp
     */
    public static void setTextSpanColor(TextView view, String text, int start, int end, int color){
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        view.setText(ss);
    }



    /**
     * 设置金额
     *
     * @param textView TextView
     * @param text 内容
     * @param textSize 字体大小 单位sp
     */
    public static void setMoney(TextView textView, String text, int textSize){
        if(!TextUtils.isEmpty(text)){
            updateTextView(textView, String.format("%s元", text),text.length(), text.length() + 1, textSize);
        }
    }

    /**
     * 设置金额
     *
     * @param textView TextView
     * @param text 内容
     */
    public static void setMoney(TextView textView, String text){
        setMoney(textView, text, 14);
    }

    /**
     * 格式化金额
     * @param amount 金额
     * */
    public static String formattedAmount(String amount){
        if(amount!=null){

            Pattern patternInt = Pattern.compile("^\\d+$$");
            Matcher isInt = patternInt.matcher(amount);

            Pattern patternDouble= Pattern.compile("\\d+\\.\\d+$");
            Matcher isDouble=patternDouble.matcher(amount);
            if( isInt.matches() ){
                NumberFormat nf = new DecimalFormat("#,###.00");
                Double d = Double.valueOf(amount);
                return nf.format(d);
            }else if(isDouble.matches()){
                NumberFormat nf = new DecimalFormat("#,###.##");
                Double d = Double.valueOf(amount);
                return nf.format(d);
            }else{
                return amount;
            }
        }else{
            return amount;
        }

    }
}
