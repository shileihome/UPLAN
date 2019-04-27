package com.uplan.miyao.ui.financial.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;

/**
 * Author: Created by shilei on 2019/4/25-18:57
 * Description:
 */
public class ValidActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ValidActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid);
        setTranslucent();
    }
    public void setTranslucent(){
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }

}
