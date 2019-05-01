package com.uplan.miyao.ui.vip.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
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

import java.security.MessageDigest;

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
        IWXAPI wxApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        wxApi.registerApp("wx05196006651968a1");

        new Thread(new Runnable() {
            @Override
            public void run() {
                PayReq req = new PayReq();
                req.appId = resp.data.get(0).appid;//你的微信appid
                req.partnerId = resp.data.get(0).mch_id;//商户号
                req.prepayId = resp.data.get(0).prepay_id;//预支付交易会话ID
                req.nonceStr = resp.data.get(0).nonce_str;//随机字符串
                long time = System.currentTimeMillis();
                req.timeStamp = time + "";//时间戳
                req.packageValue = "Sign=WXPay";//扩展字段,这里固定填写Sign=WXPay

//              req.extData         = "app data"; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//                wxApi.sendReq(req);
/*                TreeMap map=new TreeMap();
                map.put("appId","appId");
                map.put("partnerId","partnerId");
                map.put("prepayId","prepayId");
                map.put("nonceStr","nonceStr");
                map.put("timeStamp","timeStamp");
                map.put("package","package");

                Iterator iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    //并将获得的值进行拼接
                    String value=(String)map.get(key);
                 Log.e("排序",key+"--"+value);
                }

                Log.e("排序","------------------------------");
                TreeMap map2=new TreeMap();
                map2.put("appid","appid");
                map2.put("package","package");
                map2.put("partnerid","partnerid");
                map2.put("prepayid","prepayid");
                map2.put("noncestr","noncestr");
                map2.put("timestamp","timestamp");

                Iterator iterator2 = map2.keySet().iterator();
                while (iterator2.hasNext()) {
                    Object key = iterator2.next();
                    //并将获得的值进行拼接
                    String value=(String)map2.get(key);
                    Log.e("排序",key+"--"+value);
                }*/

                String stringA = "appid=" + resp.data.get(0).appid +
                        "&noncestr=" + resp.data.get(0).nonce_str +
                        "&package=" + "Sign=WXPay" +
                        "&partnerid=" + resp.data.get(0).mch_id +
                        "&prepayid=" + resp.data.get(0).prepay_id +
                        "&timestamp=" + time;
                String stringSignTemp = stringA + "&key=JRiSzRi0Fyaoo9hOqoQcsR6YaWtX5wxA";
                Log.e("sign",stringSignTemp);
                String sign = "";

                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.reset();
                    md.update(stringSignTemp.getBytes("UTF-8"));
                    StringBuffer buf = new StringBuffer();
                    byte[] bits = md.digest();
                    for (int i = 0; i < bits.length; i++) {
                        int a = bits[i];
                        if (a < 0) a += 256;
                        if (a < 16) buf.append("0");
                        buf.append(Integer.toHexString(a));
                    }
                    sign = buf.toString().toUpperCase();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                req.sign = sign;//签名
                wxApi.sendReq(req);
            }
        }).start();


    }
}
