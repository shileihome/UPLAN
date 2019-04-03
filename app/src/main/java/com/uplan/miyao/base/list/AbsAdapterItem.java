package com.uplan.miyao.base.list;

import android.view.View;

/**
 * item基类
 */
public abstract class AbsAdapterItem<T> {

    public abstract int getItemLayout();

    public abstract void init(View contentView);

    public abstract void bindData(T t);

    public void refreshItem(int clickPosition, int currentPosition) {}

}
