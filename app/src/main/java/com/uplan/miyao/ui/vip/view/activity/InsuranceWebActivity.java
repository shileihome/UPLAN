package com.uplan.miyao.ui.vip.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;

import com.uplan.miyao.base.web.BaseWebViewActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.WebViewUtils;

/**
 * Author: Created by shilei on 2019/5/22-11:09
 * Description:
 */
public class InsuranceWebActivity   extends BaseWebViewActivity {

    private String homeUrl="http://www.51mix.cn/appClient/allInsurance";
    public static void start(Context context) {
        Intent starter = new Intent(context, InsuranceWebActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initView() {
        uplanWebView.setBackgroundColor(Color.TRANSPARENT);
        uplanWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setWebViewClient();
        updateWebData();

    }

    @Override
    protected void onReload() {
        super.onReload();
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" +"\""+PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
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
                    webGoBack(InsuranceWebActivity.this);
                    return true;
                }
                if (url.startsWith("weixin://wap/pay?")){//微信特殊处理
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {

                    }
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
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" +"\""+PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
        uplanWebView.loadUrl(homeUrl);
    }

}

