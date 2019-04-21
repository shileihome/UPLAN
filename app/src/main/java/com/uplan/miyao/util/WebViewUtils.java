package com.uplan.miyao.util;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : cfp
 *     e-mail : chengfangpeng@foxmail.com
 *     time   : 2017/12/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WebViewUtils {

    /**
     * 清除cookie
     */
    public static void cleanCookie(){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    /**
     * 自定义UA
     */
    public static String generateCustomUserAgent(WebView webView){
        return webView.getSettings().getUserAgentString();


    }

    /**
     * 为webView添加cookie
     *
     * @param context Base子类Activity
     * @param url url
     * @param cookie  cookie
     */
    public static void getCookie(Context context, WebView webView,String url, String cookie){
        List<String> cookies=new ArrayList<>();
        cookies.add(cookie);
        syncCookieToWebView(webView,context,url,cookies);
    }

    /**
     * 将这个Cookie存入webviewCookiesChromium.db数据库的cookies表中
     *
     * @param context context
     * @param url url
     * @param cookies List<cookie>
     */
    public static void syncCookieToWebView(WebView webView,Context context,String url, List<String> cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cm=CookieManager.getInstance();
        cm.setAcceptCookie(true);
        if(cookies !=null){
            for(String cookie : cookies){
                cm.setCookie(url,cookie);//可以同步所有cookie，包括sessionid
            }
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP_MR1){
            cm.setAcceptThirdPartyCookies(webView, true);
            CookieManager.getInstance().flush();
        }else{
            CookieSyncManager.getInstance().sync();
        }
    }

}
