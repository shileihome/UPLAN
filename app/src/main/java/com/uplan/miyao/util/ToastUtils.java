package com.uplan.miyao.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uplan.miyao.R;
import com.uplan.miyao.base.UiUtils;

/**
 * Created by yabusai on 15/2/4.
 */
public class ToastUtils {

    private static Toast toast;
    private static Toast sIconToast;

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (context == null) {
            return;
        }

        //解决Toast多次点击弹出多次 update by zs
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }

        toast.show();

    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }

    public static void shortShow(CharSequence text) {
        show(UiUtils.getContext(), text, Toast.LENGTH_SHORT);
    }

    public static void longShow(CharSequence text) {
        show(UiUtils.getContext(), text, Toast.LENGTH_LONG);
    }

    public static void showWithIcon(Context context, @DrawableRes int iconId, String msg) {
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }

        if (sIconToast == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_with_icon, null, false);
            ((TextView) view.findViewById(R.id.tv_toast)).setText(msg);
            ((ImageView) view.findViewById(R.id.iv_toast)).setImageResource(iconId);
            sIconToast = new Toast(context);
            sIconToast.setDuration(Toast.LENGTH_SHORT);
            sIconToast.setGravity(Gravity.CENTER, 0, 0);
            sIconToast.setView(view);

        } else {
            View view = sIconToast.getView();
            ((TextView) view.findViewById(R.id.tv_toast)).setText(msg);
            ((ImageView) view.findViewById(R.id.iv_toast)).setImageResource(iconId);

        }
        sIconToast.show();

    }

    public static  void  showCopyLink(Context context){
        View view=LayoutInflater.from(context).inflate(R.layout.toast_copy_link_reply,null);
        Toast t=new Toast(context);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.setView(view);
        t.show();
    }
}
