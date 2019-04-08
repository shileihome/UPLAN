/*
package com.uplan.miyao.base.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.zrx.app.base.UiUtils;
import com.zrx.app.net.RequestInterceptor;
import com.zrx.app.net.constant.NetWorkConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

*/
/**
 * Author: Created by shilei on 2018/6/8-14:54
 * Description: baseWebView实现。
 *//*


public class CommonWebActivity extends BaseWebViewFragment{

    private String CommonUrl;
    private static final String COMMON_URL="common_url";
    private static boolean isStartActivityForResult=false;

    */
/** 是否是和签结果页 *//*

    private static boolean mIsHQResult = false;

    public static void start(Activity context,String url){
        Intent start=new Intent(context,CommonWebActivity.class);
        start.putExtra(COMMON_URL,url);
        context.startActivity(start);
    }
    public static void startActivityForResult(Activity context,String url,int requestCode){
        Intent start=new Intent(context,CommonWebActivity.class);
        start.putExtra(COMMON_URL,url);
        context.startActivityForResult(start,requestCode);
        isStartActivityForResult=true;
    }

    @Override
    public void initView() {
        setPageStatus(PAGE_STATUS_SUCCESS);
        CommonUrl=getIntent().getStringExtra(COMMON_URL);
        updateWebData();
        setWebViewClient();
    }


    private void setWebViewClient() {
        zrxWebView.setWebViewClient(new WebAppClient(this, zrxWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(isHQConfirmButton(url)){
                    sendHQStatus(url);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(isHQResultPage(url)) {
                    mLeftIcon.setVisibility(View.GONE);
                    mIsHQResult = true;
                }else {
                    mLeftIcon.setVisibility(View.VISIBLE);
                    mIsHQResult = false;
                }
            }
        });
    }

    @Override
    public void updateWebData() {
        zrxWebView.loadUrl(CommonUrl);
    }

    @Override
    protected void goBack() {
        if (zrxWebView.canGoBack()) {
            zrxWebView.goBack();
        }else{
            back();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(!mIsHQResult){
                goBack();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    */
/**
     * 是否是和签结果页
     *
     * @param url 结果页地址
     * @return true: 是和签结果页地址
     *//*

    private boolean isHQResultPage(String url){
        try {
            URL resultUrl = new URL(url);
            if(resultUrl.getPath().contains(NetWorkConfig.HQ_RESULT_URL_PATH)){
                Timber.d("hq_confirm_url: onPageFinished" + url);
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }

    */
/**
     * 是否是和签「确认」按钮地址
     *
     * @param url 确认地址
     * @return true: 是和签地址
     *//*

    private boolean isHQConfirmButton(String url){
        try {
            URL resultUrl = new URL(url);
            if(resultUrl.getPath().contains(NetWorkConfig.HQ_CONFIRM_BUTTON_URL_PATH)){
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }

    */
/**
     * 拦截和签「确认」按钮地址，发送地址
     *
     * @param url result_url
     *//*

    private void sendHQStatus(String url){
        Timber.d("hq_confirm_url: " + url);
        showLoadingDialog();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor()).build();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                closePage();
                Timber.d("hq_confirm_url: onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                closePage();
                Timber.d("hq_confirm_url: onResponse");
            }
        });
    }

    */
/**
     * 发送数据完成后，关闭页面
     *//*

    private void closePage(){
        UiUtils.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
                back();
            }
        });
    }

    */
/**
     * 返回
     *//*

    private void back(){
        if(isStartActivityForResult){
            Intent back=new Intent();
            setResult(RESULT_OK,back);
            finish();
        }else{
            finish();
        }
    }

}
*/
