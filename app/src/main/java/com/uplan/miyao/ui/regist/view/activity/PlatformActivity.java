package com.uplan.miyao.ui.regist.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;

import com.uplan.miyao.base.web.BaseWebViewActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Created by shilei on 2019/6/1-16:33
 * Description:
 */
public class PlatformActivity  extends BaseWebViewActivity {
 /*   @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_end)
    TextView tvEnd;

    public static void start(Context context){
        Intent intent=new Intent(context,PlatformActivity.class);
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
    }*/


    private String homeUrl="http://www.51mix.cn/appClient/service";
    public static void start(Context context) {
        Intent starter = new Intent(context, PlatformActivity.class);
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
    public void updateWebData() {
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" + "\""+ PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
        uplanWebView.loadUrl(homeUrl);
    }

}
