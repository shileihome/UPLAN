package com.uplan.miyao.ui.survey.view.fragment;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.uplan.miyao.base.web.BaseWebViewFragment;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.survey.view.SurveyActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.WebViewUtils;


public class SurveyFragment extends BaseWebViewFragment {

    private String homeUrl = "http://22ju570648.iok.la/wechat/account/planInstructions?isFocus=1";

    @Override
    public void initView() {

        setWebViewClient();
        String cookie = "PLAY_SESSION=" + PreferencesUtils.getString(getActivity(), PreferencesUtils.PLAY_SESSION);
        WebViewUtils.getCookie(getActivity(), uplanWebView, homeUrl, cookie);
        updateWebData();
        uplanWebView.loadUrl(homeUrl);
    }


    private void setWebViewClient() {
        uplanWebView.setWebViewClient(new WebAppClient(getActivity(), uplanWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                SurveyActivity.start(getActivity(),url);
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
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

        if(isLogined()){
           uplanWebView.loadUrl(homeUrl);
        }else{
            LoginActivity.start(getActivity());
        }

    }
    public boolean isLogined(){
        return PreferencesUtils.getBoolean(getActivity(),PreferencesUtils.LOGIN_STATE);
    }

}
