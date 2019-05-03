package com.uplan.miyao.ui.account.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;

import com.uplan.miyao.base.web.BaseWebViewActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.WebViewUtils;

/**
 * Author: Created by shilei on 2019/5/3-21:03
 * Description:
 */
public class SettingActivity extends BaseWebViewActivity {
    private String homeUrl="http://22ju570648.iok.la/wechat/account/userSetr'x";

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }


    @Override
    public void initView() {
        setWebViewClient();
        WebViewUtils.getCookie(this, uplanWebView, homeUrl,"PLAY_SESSION=" + PreferencesUtils.getString(this, PreferencesUtils.PLAY_SESSION));
        updateWebData();
        uplanWebView.loadUrl(homeUrl);
    }


    private void setWebViewClient() {
        uplanWebView.setWebViewClient(new BaseWebViewActivity.WebAppClient(this, uplanWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

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
       /* if (AppManager.getInstance().hasLogin()) {
            homeUrl = NetWorkConfig.HTTP_HOME_WEB_URL + "?token=" + AppManager.getInstance().getUser().token;
        } else {
            homeUrl = NetWorkConfig.HTTP_HOME_WEB_URL;
        }
        progressWebView.loadUrl(homeUrl);*/
    }

}

