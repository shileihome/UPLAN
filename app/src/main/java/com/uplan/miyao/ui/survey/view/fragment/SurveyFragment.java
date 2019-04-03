package com.uplan.miyao.ui.survey.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.survey.contract.SurveyContract;
import com.uplan.miyao.ui.survey.presenter.SurveyPresenter;


public class SurveyFragment extends BaseFragment<SurveyPresenter> implements SurveyContract.View {

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_survey;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {

    }

    @Override
    protected SurveyPresenter getPresenter() {
        return new SurveyPresenter(this);
    }
/*

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void killMyself() {
        finish();
    }
*/

    @Override
    public void dealFailure(int code, String message) {

    }
}
