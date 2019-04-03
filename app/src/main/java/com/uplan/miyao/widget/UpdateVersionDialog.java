package com.uplan.miyao.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.UiUtils;

/**
 * Author: Created by zs
 *
 * Description: 版本更新弹框
 */

public class UpdateVersionDialog {

    private Context mContext;
    private Display mDisplay;
    private Dialog mDialog;

    /** 立即更新 */
    private ImageView mIvStartUpdate;

    /** 版本名称 */
    private TextView mTvVersionName;

    /** 关闭按钮 */
    private ImageView mIvClose;

    /** 版本更新提示内容 */
    private TextView mTvVersionContent;

    public UpdateVersionDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public UpdateVersionDialog builder() {
        View view = UiUtils.inflateView(R.layout.dialog_update_version);

        //获取控件
        LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
        mTvVersionName = (TextView) llRoot.findViewById(R.id.tv_version_name);
        mTvVersionContent = (TextView) llRoot.findViewById(R.id.tv_version_content);
        mIvStartUpdate = (ImageView) llRoot.findViewById(R.id.iv_start_update);
        mIvClose = (ImageView) llRoot.findViewById(R.id.iv_close);
        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        llRoot.setLayoutParams(new FrameLayout.LayoutParams((int) (mDisplay.getWidth() * 0.8),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 立即更新监听
     *
     * @param listener 监听回调
     */
    public UpdateVersionDialog setImmediateUpdate(View.OnClickListener listener) {
        mIvStartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    /**
     * 弹出对话框
     */
    public UpdateVersionDialog show() {
        if (mDialog != null) {
            mDialog.show();
        }

        return this;
    }

    /**
     * 关闭弹框
     */
    private void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
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


    /**
     * 版本名称
     *
     * @param versionName 版本名称
     */
    public UpdateVersionDialog setVersionName(String versionName) {
        mTvVersionName.setText(versionName);
        return this;
    }

    /**
     * 设置更新内容
     *
     * @param versionContent 更新内容
     */
    public UpdateVersionDialog setVersionContent(String versionContent){
        mTvVersionContent.setText(versionContent);
        return this;
    }

    /**
     * 设置关闭按钮
     */
    public UpdateVersionDialog setCloseClickListener(boolean hideClose) {
        if(hideClose){
            return this;
        }

        mIvClose.setVisibility(View.VISIBLE);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }
}
