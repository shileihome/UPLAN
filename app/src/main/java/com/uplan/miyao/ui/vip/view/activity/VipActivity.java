package com.uplan.miyao.ui.vip.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.util.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/4/25-19:44
 * Description:
 */
public class VipActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_vip_logo)
    ImageView ivVipLogo;
    @BindView(R.id.ll_vip)
    CardView llVip;
    @BindView(R.id.ll_common)
    CardView llCommon;
    @BindView(R.id.tv_dredge)
    TextView tvDredge;
    @BindView(R.id.tv_user_tel)
    TextView tvUserTel;
    @BindView(R.id.tv_expire_time)
    TextView tvExpireTime;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    public static void start(Context context) {
        Intent starter = new Intent(context, VipActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        setTranslucent();
        initView();
    }


    public void initView() {
    if(PreferencesUtils.getBoolean(this,PreferencesUtils.IS_ACTIVEA)){
        llVip.setVisibility(View.VISIBLE);
        llCommon.setVisibility(View.GONE);
        tvUserTel.setText(PreferencesUtils.getString(this,PreferencesUtils.USER_TEL));
        tvExpireTime.setText(PreferencesUtils.getString(this,PreferencesUtils.EXPIRE_TIME));
        tvDredge.setText("立即续费 19.9/月");
    }else{
        llVip.setVisibility(View.GONE);
        llCommon.setVisibility(View.VISIBLE);
        tvUserName.setText(PreferencesUtils.getString(this,PreferencesUtils.USER_NAME));
        tvDredge.setText("立即加入 19.9/月");
    }
    }
    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }
    @OnClick(R.id.tv_dredge)
    public void onClick() {
    }
}
