package com.uplan.miyao.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.uplan.miyao.BuildConfig;
import com.uplan.miyao.R;


/**
 * Author: Created by shilei on 2018/6/8-14:05
 * Description: 自定义webView
 */
public class ZRXWebView extends WebView{

    public ProgressBar mProgressBar;
    private IWebChromeClient i;

    public ZRXWebView(Context context, AttributeSet attrs) {
        super(context,attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.LOG_DEBUG) {
            setWebContentsDebuggingEnabled(true);
        }
        mProgressBar = new ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
        mProgressBar.setLayoutParams(layoutParams);

        Drawable drawable=context.getResources().getDrawable(R.drawable.web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                mProgressBar.setVisibility(GONE);
            }else{
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if(i != null){
                i.onReceivedTitle(title);
            }
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setIWebChromeClient(IWebChromeClient iWebChromeClient) {
        this.i = iWebChromeClient;
    }

    public interface IWebChromeClient {

        void onReceivedTitle(String title);
    }

}
