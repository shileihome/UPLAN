package com.uplan.miyao.base.mvp;

/**
 * IView view层统一的一些方法
 */
public interface IView {

    /**
     * 错误处理
     *
     * @param code 错误码
     * @param message 错误提示信息
     */
    void dealFailure(int code, String message);
}
