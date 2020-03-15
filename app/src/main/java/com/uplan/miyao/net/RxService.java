package com.uplan.miyao.net;


import android.util.Log;

import com.uplan.miyao.BuildConfig;
import com.uplan.miyao.app.UPLANApplication;
import com.uplan.miyao.util.PreferencesUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 构建OkHttpClient
 */

public class RxService {
    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 10;
    //    private static RequestInterceptor requestInterceptor = new RequestInterceptor();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();

    /** retrofit service缓存 */
    private static Map<String, Object> retrofitServices = new HashMap<>();

    /**
     * 获取OkHttp
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        return getOkHttpBuilder().build(); // TODO: 2018/4/4 添加证书验证 by zs
    }

    public static OkHttpClient.Builder getOkHttpBuilder() {
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new OkHttpClient.Builder()
//                .addNetworkInterceptor(cacheInterceptor)//缓存拦截器
//                .addInterce
// ptor(requestInterceptor)//请求拦截器
                .addInterceptor(new Interceptor() {
                    @Override

                    public Response intercept(Chain chain) throws IOException {
                        Log.e("最终",""+PreferencesUtils.getString(UPLANApplication.getContext(), PreferencesUtils.PLAY_SESSION));
                        Request request = chain.request()
                                .newBuilder()
                                .cacheControl(new CacheControl.Builder().noCache().build())
                                .addHeader("cookie", "PLAY_SESSION="+"\""+ PreferencesUtils.getString(UPLANApplication.getContext(), PreferencesUtils.PLAY_SESSION)
                                                +"\""
                                   //     +";HttpOnly=false;"
                                    //    +";path=/;domain=www.51mix.cn;"
                                )
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(loggingInterceptor)//日志拦截器
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)//time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//读超时
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//写超时
                .retryOnConnectionFailure(false);//失败重连
    }


    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, Api.BASE_URL);
    }

    public synchronized static <T> T createApi(Class<T> clazz, String url) {

        T retrofitService;
        Object serviceObj = retrofitServices.get(clazz.getName() + url);
        if (serviceObj != null) {
            retrofitService = (T) serviceObj;
            return retrofitService;
        }
        retrofitService = getRetrofit(url).create(clazz);
        retrofitServices.put(clazz.getName() + url, retrofitService);
        return retrofitService;
    }

    /**
     * 获取Retrofit单例
     *
     * @param url
     * @return
     */
    public static Retrofit getRetrofit(String url) {
        Retrofit retrofit;
        return retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}