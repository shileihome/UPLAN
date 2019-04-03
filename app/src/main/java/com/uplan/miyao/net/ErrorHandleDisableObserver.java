package com.uplan.miyao.net;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.uplan.miyao.base.helper.LoginOutHelper;
import com.uplan.miyao.net.constant.ErrorConstant;
import com.uplan.miyao.util.ToastUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * 网络请求，页面回调
 */
public abstract class ErrorHandleDisableObserver<T extends ResponseData> extends DisposableObserver<T> {


    public abstract void onSuccess(T t);

    public abstract void onFailure(int code, String msg);

    private void dealError(Throwable e){

        Timber.tag("Catch-Error").w(e.getMessage());

        //出现异常，保存至文件中

        int code = -1;
        String msg = "未知错误";
        if (e instanceof UnknownHostException) {
            msg = ErrorConstant.ERROR_NETWORK;
        } else if (e instanceof SocketTimeoutException) {
            msg = ErrorConstant.ERROR_NETWORK;
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ResponseData errorResponse = parseHttpException(httpException);
            code = errorResponse.code;
            msg = "网络异常";
        } else if (e instanceof JsonParseException || e instanceof
                ParseException || e instanceof
                JSONException) {
            msg = "数据解析错误";
        }
        onFailure(code, msg);
        ToastUtils.shortShow(msg);
    }


    /**
     * 解析httpException
     * @param httpException 异常
     * @return ResponseData
     */
    private ResponseData parseHttpException(HttpException httpException) {
        ResponseBody responseBody = httpException.response().errorBody();
        MediaType type = responseBody.contentType();
        ResponseData errorResponse = null;
        // 如果是application/json类型数据,则解析返回内容
        if (type.type().equals("application") && type.subtype().equals("json")) {
            try {
                errorResponse = new Gson().fromJson(
                        responseBody.string(), ResponseData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorResponse;
    }


    @Override
    public void onNext(T t) {
        try {
            if (t.code == 0) {
                onSuccess(t);
            } else {
                //session失效，强制退出，重新登录
                if(ErrorConstant.SESSION_INVALID.equals(t.msg)){
                    LoginOutHelper.loginOut();
                }else {
                    onFailure(t.code, t.msg);
                }
            }
        }catch (Exception e){
            onFailure(0, "数据处理异常");
            //出现异常，保存至文件中
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        try {
            dealError(t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }
}
