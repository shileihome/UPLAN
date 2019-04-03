/*
package com.uplan.miyao.app.net.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import RxService;

import java.io.InputStream;

import okhttp3.OkHttpClient;

*/
/**
 * Author: Created by fangmingdong on 2018/4/22-下午10:13
 * Description:
 *//*

public class MyGlideModule implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
    }

    @Override public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
        OkHttpClient client = getOkHttpBuilder().build();
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);
        glide.register(GlideUrl.class, InputStream.class, factory);
    }

    private static OkHttpClient.Builder getOkHttpBuilder(){
        return RxService.getOkHttpBuilder();
    }
}*/
