package com.uplan.miyao.base.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.util.WebViewUtils;
import com.uplan.miyao.widget.UplanWebView;

import java.io.File;
import java.util.HashMap;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * Author: Created by shilei on 2018/6/8-13:50
 * Description: base webView
 */
public abstract class BaseWebViewFragment extends Fragment {
    public UplanWebView uplanWebView;
    public LinearLayout llError;
    public TextView tvReload;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), null);
        uplanWebView = (UplanWebView) view.findViewById(R.id.uplan_web_view);
        llError= (LinearLayout) view.findViewById(R.id.ll_error);
        tvReload= (TextView) view.findViewById(R.id.tv_reload);
        clearCookies(getActivity());
        initView();
        return view;
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

//优先使用缓存：
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//不使用缓存：
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settings.setUserAgentString(WebViewUtils.generateCustomUserAgent(uplanWebView));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();
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

    protected HashMap getHeader(HashMap map) {
        return map;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (uplanWebView != null) {
            clearCookies(getActivity());
            uplanWebView.onResume();
            uplanWebView.resumeTimers();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        clearCookies(getActivity());
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
    public void onDestroy() {
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
