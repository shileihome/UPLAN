package com.uplan.miyao.ui.survey.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.ui.survey.contract.SurveyContract;
import com.uplan.miyao.ui.survey.model.SurveyModel;


public class SurveyPresenter extends BasePresenter<SurveyContract.View, SurveyContract.Model> {

    public SurveyPresenter(SurveyContract.View view) {
        super(view);
    }

    @Override
    protected SurveyContract.Model getModel() {
        return new SurveyModel();
    }

}
