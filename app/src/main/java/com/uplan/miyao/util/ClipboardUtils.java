package com.uplan.miyao.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Author: Created by shilei on 2018/6/8-11:09
 * Description:文字剪切到剪切板
 */
public class ClipboardUtils {
    public static void setClipboardUtils(Context context,String lable,String msg){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText(lable, msg);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
