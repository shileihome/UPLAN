package com.uplan.miyao.base.mvp;

/**
 * BasePresenter
 */
public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter{

    /** 该View由子类初始化 */
    protected V mView;

    /** 该Model由子类初始化 */
    protected M mModel;

    public BasePresenter(V view){
        this.mView = view;
        mModel = getModel();

        onStart();
    }

    /**
     * 获取Model
     *
     * @return 具体业务的Model
     */
    protected abstract M getModel();

    @Override
    public void onStart() {}

    @Override
    public void onDestroy() {
        this.mModel = null;
        this.mView = null;
    }
}
