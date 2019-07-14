package com.uplan.miyao.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uplan.miyao.R;


/**
 * apk下载进度框
 */
public class UpdateDownloadDialog {

    private Context mContext;
    private Display mDisplay;
    private Dialog mDialog;

    private TextView mTvTitle;

    private TextView mTvVersionCurrent;
    private TextView mTvVersionUpdate;

    private TextView mTvProgress;

    private DownLoadLineProgress mLoadLineProgress;

    public UpdateDownloadDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public UpdateDownloadDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_download_progress, null);
        //获取控件
        RelativeLayout rlRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvVersionUpdate = (TextView) view.findViewById(R.id.tv_version_update);
        mTvVersionCurrent = (TextView) view.findViewById(R.id.tv_version_current);
        mTvProgress = (TextView) view.findViewById(R.id.tv_download_progress);
        mTvProgress.setVisibility(View.INVISIBLE);
        mLoadLineProgress = (DownLoadLineProgress) view.findViewById(R.id.download_line_progress);
        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        rlRoot.setLayoutParams(new FrameLayout.LayoutParams((int) (mDisplay
                .getWidth() * 0.8), RelativeLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置当前版本
     *
     * @param versionCurrent 当前版本
     * @return UpdateDownloadDialog
     */
    public UpdateDownloadDialog setVersionCurrent(String versionCurrent) {
     //   mTvVersionCurrent.setText(versionCurrent);
        return this;
    }

    /**
     * 设置更新版本
     *
     * @param versionUpdate 新版本
     * @return UpdateDownloadDialog
     */
    public UpdateDownloadDialog setVersionUpdate(String versionUpdate) {
       // mTvVersionUpdate.setText(versionUpdate);
        return this;
    }

    /**
     * 设置line进度
     *
     * @param progress 进度
     * @return UpdateDownloadDialog
     */
    public UpdateDownloadDialog setProgress(int progress) {
        mLoadLineProgress.setProgress(progress);
    //    mTvProgress.setText("更新进度" + progress + "%");
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
     * 隐藏对话框
     */
    public void dismiss() {
        try{
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }catch (Exception e){
            //ignore this exception
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
