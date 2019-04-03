package com.uplan.miyao.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uplan.miyao.app.R;
import com.uplan.miyao.base.UiUtils;


/**
 * Created by zs on 2017/9/20.
 * <p>
 * 通用Dialog
 */

public class CommonDialog {

    private Context mContext;
    private Display mDisplay;
    private Dialog mDialog;

    /** 页面控件 */
    private TextView mTvMessage;
    private TextView mTvLeft;
    private TextView mTvRight;
    private TextView mTvSubMessage;

    public CommonDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public CommonDialog builder() {
        View view = UiUtils.inflateView(R.layout.dialog_common);

        //获取控件
        LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mTvSubMessage = (TextView) view.findViewById(R.id.tv_sub_message);
        mTvLeft = (TextView) view.findViewById(R.id.tv_left);
        mTvRight = (TextView) view.findViewById(R.id.tv_right);


        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
        mDialog.setContentView(view);

        llRoot.setLayoutParams(new FrameLayout.LayoutParams((int) (mDisplay.getWidth() * 0.8),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param message 提示信息
     * @return CommonDialog
     */
    public CommonDialog setMessage(CharSequence message) {
        mTvMessage.setText(message);
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param message 提示信息
     * @param gravity message显示方式
     * @return CommonDialog
     */
    public CommonDialog setMessage(CharSequence message, int gravity) {
        mTvMessage.setGravity(gravity);
        mTvMessage.setText(message);
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param message 提示信息
     * @return CommonDialog
     */
    public CommonDialog setSubMessage(CharSequence message) {
        mTvSubMessage.setVisibility(View.VISIBLE);
        mTvSubMessage.setText(message);
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param message 提示信息
     * @param gravity message显示方式
     * @return CommonDialog
     */
    public CommonDialog setSubMessage(CharSequence message, int gravity) {
        mTvSubMessage.setVisibility(View.VISIBLE);
        mTvSubMessage.setGravity(gravity);
        mTvSubMessage.setText(message);
        return this;
    }

    /**
     * 左侧按钮
     *
     * @param text     按钮文案
     * @param listener 监听
     * @return CommonDialog
     */
    public CommonDialog setLeftButton(String text, final View.OnClickListener listener) {
        mTvLeft.setText(text);
        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        return this;
    }

    /**
     * 左侧按钮
     *
     * @param text     按钮文案
     * @param listener 监听
     * @return CommonDialog
     */
    public CommonDialog setRightButton(String text, final View.OnClickListener listener) {
        mTvRight.setText(text);
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        return this;
    }

    /**
     * 左侧按钮
     *
     * @param text     按钮文案
     * @param listener 监听
     * @return CommonDialog
     */
    public CommonDialog setOnlyButton(String text, final View.OnClickListener listener) {
        mTvLeft.setVisibility(View.GONE);
        mTvRight.setText(text);
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        return this;
    }

    /**
     * 设置是否可取消
     *
     * @param cancelable boolean
     * @return CommonDialog
     */
    public CommonDialog setCancelable(boolean cancelable) {
        if (mDialog != null) {
            mDialog.setCancelable(cancelable);
        }
        return this;
    }

    /**
     * 弹出对话框
     */
    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    /**
     * 判断dialog是否显示
     *
     * @return boolean
     */
    public boolean isShow() {
        return mDialog != null && mDialog.isShowing();
    }

}
