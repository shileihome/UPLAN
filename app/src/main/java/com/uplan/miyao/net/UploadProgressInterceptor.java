package com.uplan.miyao.net;

import android.text.TextUtils;

import java.io.IOException;
import java.util.WeakHashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by fangmingdong on 2018/4/16-下午1:56
 * Description: 文件上传进度拦截
 */
public class UploadProgressInterceptor implements Interceptor {

    public static final WeakHashMap<String, ProgressListener> LISTENER_MAP = new WeakHashMap<>();

    private final String mFilePath;

    /**
     * 注册加载监听
     */
    public static void addListener(String filePath, ProgressListener listener) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        LISTENER_MAP.put(filePath, listener);
    }

    /**
     * 取消注册监听
     */
    public static void removeListener(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        LISTENER_MAP.remove(filePath);
    }


    public UploadProgressInterceptor(String filePath, ProgressListener listener) {
        mFilePath = filePath;
        addListener(filePath, listener);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest);
        }

        Request progressRequest = originalRequest.newBuilder()
                .method(originalRequest.method(),
                        new UploadRequestBody(originalRequest.body(), mFilePath))
                .build();

        return chain.proceed(progressRequest);
    }

    public interface ProgressListener {
        void onProgress(String filePath, int progress);
    }
}
