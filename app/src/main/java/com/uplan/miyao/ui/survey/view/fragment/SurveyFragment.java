package com.uplan.miyao.ui.survey.view.fragment;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.uplan.miyao.base.web.BaseWebViewFragment;


public class SurveyFragment extends BaseWebViewFragment {
    private String homeUrl="http://key.51mix.cn/wechat/family/InfoInputAction/publicize";



    @Override
    public void initView() {
        setWebViewClient();
        updateWebData();
        uplanWebView.loadUrl(homeUrl);
    }


    private void setWebViewClient() {
        uplanWebView.setWebViewClient(new WebAppClient(getActivity(), uplanWebView) {
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
