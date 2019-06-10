package com.uplan.miyao.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.util.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/4/28-17:56
 * Description:
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    IWXAPI wxApi;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_pay_image)
    TextView tvPayImage;
    @BindView(R.id.tv_pay_text)
    TextView tvPayText;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
        setTranslucent();
        wxApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        wxApi.registerApp("wx05196006651968a1");

        wxApi.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
//        Log.d(TAG, "onPayFinish, errCode = " + baseReq.errCode);

        if (baseReq.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果");
            builder.setMessage("微信支付，不知道成功没，没成功钱就打水漂了！");
            builder.show();
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
         /*   AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果");
            builder.setMessage( String.valueOf(resp.errCode));
            builder.show();*/
            if (resp.errCode == 0) {
                Log.d(TAG, "onPayFinish" + "支付成功！");
                if (PreferencesUtils.getBoolean(this, PreferencesUtils.IS_ACTIVEA)) {
                    PreferencesUtils.putBoolean(this, PreferencesUtils.IS_ACTIVEA, true);
                    String lastDate=PreferencesUtils.getString(this,PreferencesUtils.EXPIRE_TIME);
                    long time=Long.parseLong(lastDate);
                    time=time+30*24*60*60*1000;
                    String expireTime=fomatDate(time);
                    tvPayText.setText("续费成功！");
                    tvPayTime.setVisibility(View.VISIBLE);
                    tvPayTime.setText("您的会员将于"+expireTime+"日到期");

                } else {
                    PreferencesUtils.putBoolean(this, PreferencesUtils.IS_ACTIVEA, true);
                    tvPayText.setText("恭喜您成为会员！");
                    tvPayTime.setVisibility(View.GONE);
                }

                tvTitle.setText("开通成功");
                tvPayImage.setBackground(getResources().getDrawable(R.drawable.pay_sucess));
                tvSure.setText("我知道了");
            } else {
                Log.d(TAG, "onPayFinish" + resp.errCode);
                if (PreferencesUtils.getBoolean(this, PreferencesUtils.IS_ACTIVEA)) {
                    PreferencesUtils.putBoolean(this, PreferencesUtils.IS_ACTIVEA, true);
                } else {
                    PreferencesUtils.putBoolean(this, PreferencesUtils.IS_ACTIVEA, false);
                }
                tvTitle.setText("支付失败");
                tvPayImage.setBackground(getResources().getDrawable(R.drawable.pay_faile));
                tvPayText.setText("抱歉，支付失败了！");
                tvPayTime.setVisibility(View.GONE);
                tvSure.setText("我知道了");
            }
        }
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }


    @OnClick({R.id.iv_back, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sure:
                finish();
                break;
        }
    }

    private String fomatDate(long time){
        Date date=new Date();
        date.setTime(time);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String expire=sdf.format(date);
        return expire;
    }
}

