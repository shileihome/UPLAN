package com.uplan.miyao.ui.financial.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/4/13-15:45
 * Description:
 */
public class FinancialActivity extends AppCompatActivity {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_login2)
    TextView tvLogin2;

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

    @OnClick({R.id.tv_login, R.id.tv_login2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                FinancialWebActivity.start(this);
                break;
            case R.id.tv_login2:
                break;
        }
    }
    public void setTranslucent(){
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

}
