package com.uplan.miyao.ui.vip.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.uplan.miyao.base.web.BaseWebViewActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Created by shilei on 2019/4/25-19:44
 * Description:
 */
public class VipActivity extends BaseWebViewActivity {
/*
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

    }

    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    public void initView() {
        if (PreferencesUtils.getBoolean(this, PreferencesUtils.IS_ACTIVEA)) {
            llVip.setVisibility(View.VISIBLE);
            llCommon.setVisibility(View.GONE);
            tvUserTel.setText(StringUtils.formatTel(PreferencesUtils.getString(this, PreferencesUtils.USER_TEL)));
            String date=fomatDate(PreferencesUtils.getLong(this, PreferencesUtils.EXPIRE_TIME));
            tvExpireTime.setText("您的会员将于"+date+"到期");
            tvDredge.setText("立即续费 19.9/月");
        } else {
            llVip.setVisibility(View.GONE);
            llCommon.setVisibility(View.VISIBLE);
            tvUserName.setText(StringUtils.formatTel(PreferencesUtils.getString(this, PreferencesUtils.USER_TEL)));
            tvDredge.setText("立即加入 19.9/月");
        }
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_dredge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_dredge:
                mPresenter.pay();
                break;
        }
    }

    @Override
    public void dealFailure(int code, String message) {
        ToastUtils.shortShow(message);
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
                TreeMap map=new TreeMap();
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
                }

                String stringA = "appid=" + resp.data.get(0).appid +
                        "&noncestr=" + resp.data.get(0).nonce_str +
                        "&package=" + "Sign=WXPay" +
                        "&partnerid=" + resp.data.get(0).mch_id +
                        "&prepayid=" + resp.data.get(0).prepay_id +
                        "&timestamp=" + time;
                String stringSignTemp = stringA + "&key=JRiSzRi0Fyaoo9hOqoQcsR6YaWtX5wxA";
                Log.e("sign", stringSignTemp);
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

    private String fomatDate(long time){
        Date date=new Date();
        date.setTime(time);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String expire=sdf.format(date);
        return expire;
    }*/


    private String homeUrl="http://www.51mix.cn/appClient/wechatPay/wechatPayPage";
    public static void start(Context context) {
        Intent starter = new Intent(context, VipActivity.class);
        context.startActivity(starter);
    }


    @Override
    public void initView() {
        setWebViewClient();
        updateWebData();
    }

    @Override
    protected void onReload() {
        super.onReload();
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" + "\""+PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
        uplanWebView.loadUrl(homeUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setWebViewClient() {
        uplanWebView.setWebViewClient(new BaseWebViewActivity.WebAppClient(this, uplanWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.equals(WEB_BACK)){
                    finish();
                    return true;
                }
                if (url.startsWith("weixin://wap/pay?")) {
//微信特殊处理
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if ( url.startsWith("https://wx.tenpay.com")) {
                    Map<String, String> extraHeaders = new HashMap<>();
                    extraHeaders.put("Referer", "http://www.51mix.cn");
                    view.loadUrl(url, extraHeaders);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void updateWebData() {
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" + "\""+ PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
        uplanWebView.loadUrl(homeUrl);
    }
}
