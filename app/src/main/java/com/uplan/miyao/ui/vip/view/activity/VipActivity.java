package com.uplan.miyao.ui.vip.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseActivity;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;
import com.uplan.miyao.ui.vip.presenter.DiscoverPresenter;
import com.uplan.miyao.util.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/4/25-19:44
 * Description:
 */
public class VipActivity extends BaseActivity<DiscoverPresenter> implements DiscoverContract.View {

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
    protected void init() {
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        setTranslucent();
        initView();
    }

    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(this);
    }


    public void initView() {
        if (PreferencesUtils.getBoolean(this, PreferencesUtils.IS_ACTIVEA)) {
            llVip.setVisibility(View.VISIBLE);
            llCommon.setVisibility(View.GONE);
            tvUserTel.setText(PreferencesUtils.getString(this, PreferencesUtils.USER_TEL));
            tvExpireTime.setText(PreferencesUtils.getString(this, PreferencesUtils.EXPIRE_TIME));
            tvDredge.setText("立即续费 19.9/月");
        } else {
            llVip.setVisibility(View.GONE);
            llCommon.setVisibility(View.VISIBLE);
            tvUserName.setText(PreferencesUtils.getString(this, PreferencesUtils.USER_NAME));
            tvDredge.setText("立即加入 19.9/月");
        }
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }

    @OnClick(R.id.tv_dredge)
    public void onClick() {
        mPresenter.pay();
        ;
    }

    @Override
    public void dealFailure(int code, String message) {

    }

    @Override
    public void loading() {
        showLoadingDialog();
    }

    @Override
    public void unLoad() {
        hideLoadingDialog();
    }

    @Override
    public void dealPaySuccess(VipDetailResp resp) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wx05196006651968a1");
        PayReq req = new PayReq();
        req.appId           = resp.data.get(0).appid;//你的微信appid
        req.partnerId       = resp.data.get(0).mch_id;//商户号
        req.prepayId        = resp.data.get(0).prepay_id;//预支付交易会话ID
        req.nonceStr        = resp.data.get(0).nonce_str;//随机字符串
    //    req.timeStamp       = "1412000000";//时间戳
        req.packageValue    = "Sign=WXPay";//扩展字段,这里固定填写Sign=WXPay
        req.sign            = resp.data.get(0).sign;//签名
//              req.extData         = "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }
}
