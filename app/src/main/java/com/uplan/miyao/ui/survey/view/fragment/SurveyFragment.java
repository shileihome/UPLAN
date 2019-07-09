package com.uplan.miyao.ui.survey.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.survey.contract.SurveyContract;
import com.uplan.miyao.ui.survey.presenter.SurveyPresenter;
import com.uplan.miyao.ui.survey.view.SurveyWebActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SurveyFragment extends BaseFragment<SurveyPresenter> implements SurveyContract.View {

    @BindView(R.id.tv_start_survey)
    TextView tvStartSurvey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey, null);
        ButterKnife.bind(this, view);
        setTranslucent();
        return view;
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        QMUIStatusBarHelper.translucent(getActivity());
//        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
    }

    @Override
    protected SurveyPresenter getPresenter() {
        return null;
    }

    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick(R.id.tv_start_survey)
    public void onClick() {
        if(!isLogined()){
            CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
            commonDialog.setSubMessage("请先登录!").
                    setLeftButton(getString(R.string.common_dialog_cancel), v -> {

                    }).
                    setRightButton(getString(R.string.commit_change), v -> {

                        LoginActivity.start(getActivity());
                    }).show();
            return;
        }

        SurveyWebActivity.start(getActivity());
    }
    public boolean isLogined(){
        return PreferencesUtils.getBoolean(getActivity(),PreferencesUtils.LOGIN_STATE);
    }
}


/*extends BaseWebViewFragment {

    private String homeUrl = "http://www.51mix.cn/wechat/account/planInstructions?isFocus=1";

    @Override
    public void initView() {

        setWebViewClient();
        String cookie = "PLAY_SESSION=" + "\""+PreferencesUtils.getString(getActivity(), PreferencesUtils.PLAY_SESSION)+"\"";
        WebViewUtils.getCookie(getActivity(), uplanWebView, homeUrl, cookie);
        uplanWebView.loadUrl(homeUrl);
    }

    @Override
    protected void onReload() {
        super.onReload();
        String cookie = "PLAY_SESSION=" + "\""+PreferencesUtils.getString(getActivity(), PreferencesUtils.PLAY_SESSION)+"\"";
        WebViewUtils.getCookie(getActivity(), uplanWebView, homeUrl, cookie);
        uplanWebView.loadUrl(homeUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWebData();
    }

    private void setWebViewClient() {
        uplanWebView.setWebViewClient(new WebAppClient(getActivity(), uplanWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                SurveyWebActivity.start(getActivity(),url);
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
            String cookie = "PLAY_SESSION=" + "\""+PreferencesUtils.getString(getActivity(), PreferencesUtils.PLAY_SESSION)+"\"";
            WebViewUtils.getCookie(getActivity(), uplanWebView, homeUrl, cookie);
           uplanWebView.loadUrl(homeUrl);
        }else{
            LoginActivity.start(getActivity());
        }

    }
    public boolean isLogined(){
        return PreferencesUtils.getBoolean(getActivity(),PreferencesUtils.LOGIN_STATE);
    }

}
*/