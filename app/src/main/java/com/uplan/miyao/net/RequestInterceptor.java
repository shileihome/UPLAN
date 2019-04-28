/*
package com.uplan.miyao.app.net;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.uplan.miyao.app.BuildConfig;
import com.uplan.miyao.app.app.AccountManager;
import UiUtils;
import com.uplan.miyao.app.constant.PreferenceConstant;
import CharactorHandler;
import PreferencesUtils;
import SystemUtils;
import ZipHelper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

*/
/**
 * 网络请求拦截器
 *//*


public class RequestInterceptor implements Interceptor {

*/
/*
* token
*//*


    private static final String ACCESS_TOKEN = "X-CSRF-TOKEN";
*/
/*

* cookie
*//*


    private static final String COOKIE = "Cookie";

*/
/*
* versionCode
*//*


    private static final String VERSION_CODE = "versionCode";

*/
/*
* 签名参数名
*//*


    private static final String PARAM_SIGN = "sign";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = onHttpRequestBefore(request);
        Response originalResponse;
        try {
//            printRequest(request, BuildConfig.DEBUG);
            originalResponse = chain.proceed(request);
            saveCookie(originalResponse);
            printResponse(request, originalResponse.newBuilder().build(), BuildConfig.LOG_DEBUG);
        } catch (Exception e) {
            printRequest(request, BuildConfig.LOG_DEBUG);
            throw e;
        }

        return originalResponse;
    }

    private void saveCookie(Response originalResponse) {
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> cookies = originalResponse.headers("Set-Cookie");
            for (String cookie : cookies) {
                if (cookie.contains("SESSION")) {
                    String sessionId = cookie.split(";")[0];
                    PreferencesUtils.putString(UiUtils.getContext(), PreferenceConstant.COOKIE_SESSION_ID, sessionId);
                    Timber.d("Cookie_SessionId: " + sessionId);

                }
            }
        }
    }


*
     * 发起请求之前
     * 在这里做一些请求服务器前的一些操作, 比如添加统一的参数和header,或者对参数进行签名
     *
     * @param request
     * @return


    private Request onHttpRequestBefore(Request request) {
        return processParams(request);
    }

*
     * 处理请求参数 比如对请求参数进行签名，添加公用的参数
     *
     * @param originRequest 请求


    private Request processParams(Request originRequest) {

        Request.Builder newRequest = originRequest.newBuilder();

        // Header
        Headers.Builder newHeaderBuilder = originRequest.headers().newBuilder();
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                newHeaderBuilder.add(entry.getKey(), entry.getValue());
            }
            newRequest.headers(newHeaderBuilder.build());
        }

        // Query Param
        if ("GET".equals(originRequest.method())) {
            HttpUrl.Builder newUrlBuilder = originRequest.url().newBuilder();
            Map<String, String> queryParamMap = getQueryParamMap();
            if (queryParamMap != null && !queryParamMap.isEmpty()) {
                for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
                    newUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                newRequest.url(newUrlBuilder.build());
            }
        } else if ("POST".equals(originRequest.method())) {
            RequestBody body = originRequest.body();
            if (body != null && body instanceof FormBody) {
                // POST Param x-www-form-urlencoded
                FormBody formBody = (FormBody) body;
                Map<String, String> formBodyParamMap = new HashMap<>();
                int bodySize = formBody.size();
                for (int i = 0; i < bodySize; i++) {
                    formBodyParamMap.put(formBody.name(i), formBody.value(i));
                }

                Map<String, String> newFormBodyParamMap = getFormBodyParamMap();
                if (newFormBodyParamMap != null) {
                    formBodyParamMap.putAll(newFormBodyParamMap);
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : formBodyParamMap.entrySet()) {
                        bodyBuilder.add(entry.getKey(), entry.getValue());
                    }
                    newRequest.method(originRequest.method(), bodyBuilder.build());
                }
            } else if (body != null && body instanceof MultipartBody) {
                // POST Param form-data
                MultipartBody multipartBody = (MultipartBody) body;
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Map<String, String> extraFormBodyParamMap = getFormBodyParamMap();
                for (Map.Entry<String, String> entry : extraFormBodyParamMap.entrySet()) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                List<MultipartBody.Part> parts = multipartBody.parts();
                for (MultipartBody.Part part : parts) {
                    builder.addPart(part);
                }
                newRequest.post(builder.build());
            }
        }
        return newRequest.build();
    }


*
     * 添加公共header
     *
     * @return  header


    private Map<String, String> getHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put(ACCESS_TOKEN, accessToken());
        headers.put(VERSION_CODE, String.valueOf(SystemUtils.getVersionCode(UiUtils.getContext())));

        //cookie
        String sessionId = PreferencesUtils.getString(UiUtils.getContext(), PreferenceConstant.COOKIE_SESSION_ID);
        if(!TextUtils.isEmpty(sessionId)){
            headers.put(COOKIE, sessionId);
        }
        return headers;
    }


*
     * get请求添加通过参数
     *
     * @return


    public Map<String, String> getQueryParamMap() {

        Map<String, String> params = new HashMap<>();
        params.put("client_type", "Android");
        return params;
    }

*
     * post请求添加公共参数
     *
     * @return


    public Map<String, String> getFormBodyParamMap() {
        Map<String, String> postParams = new HashMap<>();
        //postParams.put("client_type", "Android");
        return postParams;
    }


*
     * 获取登录token
     *
     * @return token


    private String accessToken() {
        return AccountManager.getInstance().getToken();
    }


*
     * 响应日志
     *
     * @param request
     * @param response
     * @param logResponse
     * @return
     * @throws IOException


    @Nullable
    private String printResponse(Request request, Response response, boolean logResponse) throws IOException {

        //读取服务器返回的结果
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return "";
        }
        String bodyString = null;
        if (isParseable(responseBody.contentType())) {
            try {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                //获取content的压缩类型
                String encoding = response
                        .headers()
                        .get("Content-Encoding");

                Buffer clone = buffer.clone();

                //解析response content
                bodyString = parseContent(responseBody, encoding, clone);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (logResponse) {

                StringBuilder sb = new StringBuilder();
                sb.append("Method: ").append(request.method()).append("\n");
                sb.append("url: ").append(request.url().toString()).append("\n");
                sb.append("Headers: ").append(getHeaders(request)).append("\n");
                sb.append("Response_Result: ").append("\n").append((isJson(responseBody.contentType()) ?
                        CharactorHandler.jsonFormat(bodyString) : isXml(responseBody.contentType()) ?
                        CharactorHandler.xmlFormat(bodyString) : bodyString)).append("\n");
                Timber.tag("Api_Data").w(sb.toString());
            }

            //收集接口异常
            try{
                if(isJson(responseBody.contentType())){
                    JSONObject jsonObject =JSONObject.parseObject(bodyString);
                    if(jsonObject.containsKey("code")){
                        int code = jsonObject.getInteger("code");
                        if(code != 0){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Method: ").append(request.method()).append("\n");
                            sb.append("url: ").append(request.url().toString()).append("\n");
                            sb.append("Headers: ").append(getHeaders(request)).append("\n");
                            sb.append("Response_Result: ").append("\n").append((isJson(responseBody.contentType()) ?
                                    CharactorHandler.jsonFormat(bodyString) : isXml(responseBody.contentType()) ?
                                    CharactorHandler.xmlFormat(bodyString) : bodyString)).append("\n");

                        }
                    }
                }
            }catch (Exception ex){
                //ignore this exception
            }
        } else {
            if (logResponse) {
                Timber.tag(getTag(request, "Response_Result")).w("This result isn't parsed");
            }
        }
        return bodyString;
    }

*
     * 请求日志
     *
     * @param request
     * @param debug


    private void printRequest(Request request, boolean debug) {
        if (!debug) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Method: ").append(request.method()).append("\n");
        sb.append("url: ").append(request.url().toString()).append("\n");
        sb.append("Headers: ").append(getHeaders(request)).append("\n");
        Timber.tag("Api_Data").w(sb.toString());
    }


    private String getTag(Request request, String tag) {
//        return String.format(" [%s] 「 %s 」 [ %s ]>>> %s", request.method(), request.url().toString(), getPostParamsBody(request), tag);
        return String.format(" [%s] 「 %s 」 >>> %s", request.method(), request.url().toString(), tag);
    }


*
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return


    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//解压
        } else {//content没有被压缩
            return clone.readString(charset);
        }
    }


    private static boolean isParseable(MediaType mediaType) {
        if (mediaType == null) return false;
        return mediaType.toString().toLowerCase().contains("text")
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    private static boolean isJson(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("json");
    }

    private static boolean isXml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("xml");
    }

    private static boolean isHtml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("html");
    }

    private static boolean isForm(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    private static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1)
            return s;
        return s.substring(i + 1, s.length() - 1);
    }


*
     * 获取请求参数
     *
     * @param request
     * @return


    private String getPostParamsBody(Request request) {
        String paramsStr = "";
        try {
            RequestBody requestBody = request.body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            paramsStr = buffer.readString(charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return paramsStr;
        }

    }

*
     * 获取请求header
     *
     * @param request
     * @return


    private String getHeaders(Request request) {

        StringBuilder headerSb = new StringBuilder();
        Headers headers = request.headers();
        for (String name : headers.names()) {
            headerSb.append(name);
            headerSb.append("=");
            headerSb.append(headers.get(name));
        }
        return headerSb.toString();
    }
}
*/
