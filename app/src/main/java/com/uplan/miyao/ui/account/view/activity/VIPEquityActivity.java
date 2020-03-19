package com.uplan.miyao.ui.account.view.activity;

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
 * Author: Created by shilei on 2019/4/5-14:12
 * Description:
 */
public class VIPEquityActivity extends BaseWebViewActivity {
private String homeUrl="http://key.51mix.cn/wechat/family/InfoInputAction/publicize";
    public static void start(Context context) {
        Intent starter = new Intent(context, VIPEquityActivity.class);
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
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" +"\""+ PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
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
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" +"\""+ PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION)+"\"");
        uplanWebView.loadUrl(homeUrl);
    }

}
