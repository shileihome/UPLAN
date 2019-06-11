package com.uplan.miyao.base.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.util.WebViewUtils;
import com.uplan.miyao.widget.UplanWebView;

import java.io.File;
import java.util.HashMap;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * Author: Created by shilei on 2018/6/8-13:50
 * Description: base webView
 */
public abstract class BaseWebViewActivity extends Activity {
    public UplanWebView uplanWebView;
    public LinearLayout llError;
    public TextView tvReload;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        uplanWebView= (UplanWebView) findViewById(R.id.uplan_web_view);
        llError= (LinearLayout) findViewById(R.id.ll_error);
        tvReload= (TextView) findViewById(R.id.tv_reload);
        clearCookies(this);
        initView();
        setTranslucent();
        uplanWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && uplanWebView.canGoBack()) {  //表示按返回键
                        uplanWebView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }

    /**
     * 设置沉浸式状态栏
     */
    public void setTranslucent(){
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
    protected int getContentLayout() {
        return R.layout.activity_base_web;
    }

    protected  void onReload(){
        llError.setVisibility(View.GONE);
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
            settings.setDomStorageEnabled(true);
            settings.setBlockNetworkImage(false);
//优先使用缓存：
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//不使用缓存：
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            //自适应屏幕
       /*     settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
*/
            settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM) ;
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
    llError.setVisibility(View.VISIBLE);
    tvReload.setOnClickListener(view->{
        onReload();
    });
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
            clearCookies(this);
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


    public void clearCookies(Context context) {
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    public int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

}
