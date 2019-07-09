package com.uplan.miyao.ui.survey.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.vip.view.activity.SharedActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/7/9-19:09
 * Description:
 */
public class SurveyActivity extends AppCompatActivity {

    @BindView(R.id.tv_start_survey)
    TextView tvStartSurvey;

    public static void start(Context context) {
        Intent starter = new Intent(context, SurveyActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_survey);
        ButterKnife.bind(this);
        setTranslucent();
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
//        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
    }


    @OnClick(R.id.tv_start_survey)
    public void onClick() {
        if(!isLogined()){
            CommonDialog commonDialog = new CommonDialog(this).builder();
            commonDialog.setSubMessage("请先登录!").
                    setLeftButton(getString(R.string.common_dialog_cancel), v -> {

                    }).
                    setRightButton(getString(R.string.commit_change), v -> {

                        LoginActivity.start(this);
                    }).show();
            return;
        }

        SurveyWebActivity.start(this);
    }
    public boolean isLogined(){
        return PreferencesUtils.getBoolean(this,PreferencesUtils.LOGIN_STATE);
    }

}
