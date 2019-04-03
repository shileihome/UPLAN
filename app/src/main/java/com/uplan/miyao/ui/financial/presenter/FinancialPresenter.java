package com.uplan.miyao.ui.financial.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.ui.financial.contract.FinancialContract;
import com.uplan.miyao.ui.financial.model.FinancialModel;


public class FinancialPresenter extends BasePresenter<FinancialContract.View, FinancialContract.Model> {

    public FinancialPresenter(FinancialContract.View view) {
        super(view);
    }

    @Override
    protected FinancialContract.Model getModel() {
        return new FinancialModel();
    }

}
