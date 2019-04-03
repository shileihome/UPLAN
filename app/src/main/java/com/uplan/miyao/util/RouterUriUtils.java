package com.uplan.miyao.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.URISyntaxException;

/**
 * Author: Created by zs on 2018/4/11.
 *
 * Description: 根据Uri跳转不同页面
 */

public class RouterUriUtils {

    private RouterUriUtils() {}

    /**
     *
     * 根据Uri跳转不同页面
     *
     * @param context 上下文
     * @param uri uri
     */
    public static void startWithUri(Context context, String uri) {
        try {
            if (uri == null || uri.trim().length() <= 0) {
                return;
            }

            String scheme = Uri.parse(uri.trim()).getScheme();
            if (scheme == null) {
                throw new URISyntaxException(uri, "scheme is null");
            }

            if(scheme.startsWith("intent")){
                Intent intent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME);
                context.startActivity(intent);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
