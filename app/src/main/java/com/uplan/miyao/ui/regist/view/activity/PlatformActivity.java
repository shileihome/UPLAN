package com.uplan.miyao.ui.regist.view.activity;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/6/1-16:33
 * Description:
 */
public class PlatformActivity  extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_end)
    TextView tvEnd;

    public static void start(Context context){
        Intent intent=new Intent(context,PrivacyActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);
        ButterKnife.bind(this);
        setTranslucent();
    }

    @OnClick({R.id.iv_back, R.id.tv_end})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_end:
                finish();
                break;
        }
    }
    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
}
