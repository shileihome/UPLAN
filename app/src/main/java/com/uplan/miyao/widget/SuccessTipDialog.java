package com.uplan.miyao.widget;

import android.app.Activity;

import com.uplan.miyao.base.UiUtils;

/**
 * Author: Created by zs on 2018/4/23.
 *
 * Description: 成功提示对话框
 */

public class SuccessTipDialog {

    /**
     * 弹框提示
     *
     * @param context 上下文
     * @param callback 回调
     */
    public static void show(Activity context, SuccessTipCallback callback){
        try{
            show(context, "提交成功", callback);
        }catch (Exception ex){
            //ignore this exception
        }
    }

    /**
     * 弹框提示
     *
     * @param context 上下文
     * @param tips 提示信息
     * @param callback 回调
     */
    public static void show(Activity context, String tips, SuccessTipCallback callback){
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(tips)
                .create();
        tipDialog.show();

        UiUtils.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(context == null || context.isFinishing()){
                    return;
                }

                tipDialog.dismiss();
                callback.dismiss();
            }
        }, 1000);
    }

    public interface SuccessTipCallback{
        void dismiss();
    }

}
