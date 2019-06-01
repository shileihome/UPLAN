package com.uplan.miyao.ui.financial.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.ui.vip.view.activity.VipActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/4/13-15:45
 * Description:
 */
public class FinancialActivity extends AppCompatActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_start_financial)
    TextView tvStartFinancial;

    public static void start(Context context) {
        Intent starter = new Intent(context, FinancialActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        ButterKnife.bind(this);
        setTranslucent();
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        //   QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    @OnClick({R.id.iv_back,  R.id.tv_start_financial})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_start_financial:
                if(PreferencesUtils.getBoolean(this,PreferencesUtils.IS_ACTIVEA)){
                    FinancialWebActivity_1.start(this);
                }else{
                    CommonDialog commonDialog = new CommonDialog(this).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(this);
                            }).show();
                }

                break;
        }
    }
}
