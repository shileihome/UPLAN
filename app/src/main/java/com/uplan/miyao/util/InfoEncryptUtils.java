package com.uplan.miyao.util;

/**
 * Author: Created by fangmingdong on 2018/4/17-上午11:16
 * Description: 信息脱敏工具类
 */
public class InfoEncryptUtils {

    private InfoEncryptUtils() {
    }

    /**
     * 手机号脱敏
     *
     * @param phone 手机号
     * @return 结果
     */
    public static String encryptPhone(String phone) {
        return setContentText(phone, "****", 7, 11);
    }

    /**
     * 银行卡号脱敏
     *
     * @param bank 银行卡号
     * @return 结果
     */
    public static String encryptBank(String bank) {
        StringBuilder sb=new StringBuilder();
        if(bank!=null&&bank.length()>10){
            for(int i=0;i<bank.length()-10;i++){
                sb.append("*");
            }

            return setContentText(bank, sb.toString(), 6, bank.length()-4);
        }else{
            return bank;
        }

    }

    /**
     * 身份证号脱敏
     * @param idCard 身份证号
     * @return 结果
     */
    public static String encryptIdCard(String idCard) {
        StringBuilder sb = new StringBuilder();
        if(idCard!=null && idCard.length()>10) {
            for (int i = 0; i < idCard.length() - 10; i++) {
                sb.append("*");
            }
            return setContentText(idCard, sb.toString(), 6, idCard.length()-4);
        }else{
            return idCard;
        }
    }

    private static String setContentText(String originalText, String replaceText, int start, int end) {
        if (originalText == null || start > originalText.length() || end > originalText.length()||end<=start) {
            return originalText;
        }
        if (end == originalText.length()) {
            return originalText.substring(0, start) + replaceText;
        } else {
            return originalText.substring(0, start) + replaceText + originalText.substring(end);
        }
    }

}
