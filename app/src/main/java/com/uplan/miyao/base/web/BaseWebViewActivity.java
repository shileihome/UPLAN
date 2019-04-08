package com.uplan.miyao.base.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uplan.miyao.R;
import com.uplan.miyao.util.WebViewUtils;
import com.uplan.miyao.widget.UplanWebView;

import java.util.HashMap;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * Author: Created by shilei on 2018/6/8-13:50
 * Description: base webView
 */
public abstract class BaseWebViewActivity extends Activity {
    public UplanWebView uplanWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        uplanWebView= (UplanWebView) findViewById(R.id.uplan_web_view);
        initView();
    }

    protected int getContentLayout() {
        return R.layout.activity_base_web;
    }

    protected void onReload() {
        String url=uplanWebView.getUrl();
        uplanWebView.loadUrl(url);
    }


    protected class WebAppClient extends WebViewClient {
        private Context context;
        private WebView uplanWebView;
        WebSettings settings;

        @SuppressLint("SetJavaScriptEnabled")
        public WebAppClient(Context context, WebView uplanWebView) {
            this.context = context;
            this.uplanWebView = uplanWebView;
            settings = uplanWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setBuiltInZoomControls(false);
            settings.setDefaultTextEncodingName("utf-8");
            settings.setUserAgentString(WebViewUtils.generateCustomUserAgent(uplanWebView));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title=view.getTitle();
//            if(!TextUtils.isEmpty(title)&&!title.contains(".")){
//                mTitle.setText(title);
//            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            showErrorPage();
        }
    }

    /**
     * 设置错误页面
     */
    private void showErrorPage() {
    }

    /**
     * 子类init
     */
    public abstract void initView();


    /**
     * webView loadUrl
     */
    public abstract void updateWebData();

    /**
     * 为webView添加header.
     *
     * @param map header为LoadUrl参数
     */

    protected HashMap getHeader(HashMap map){
        return map;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(uplanWebView != null){
            uplanWebView.onResume();
            uplanWebView.resumeTimers();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (uplanWebView != null) {
            uplanWebView.onPause();
            uplanWebView.pauseTimers();
        }
    }

    @Override
    public  void onDestroy() {
        super.onDestroy();
        if (uplanWebView != null) {
            uplanWebView.loadUrl("about:blank");
            uplanWebView.stopLoading();
            uplanWebView.setWebChromeClient(null);
            uplanWebView.setWebViewClient(null);
            uplanWebView.destroy();
            uplanWebView = null;
        }
    }
}
