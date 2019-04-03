package com.uplan.miyao.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Author: Created by shilei on 2018/7/23-16:09
 * Description:
 */
public class Dp2px {
    /**
     * dp转px
     * @param context
     * @param dpVal dp
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
