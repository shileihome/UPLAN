/*
package com.uplan.miyao.app.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import UiUtils;
import LoginOutHelper;
import ErrorConstant;
import NetWorkConfig;
import RegexUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

*/
/**
 * Author: Created by zs on 2018/4/18.
 *
 * Description: 获取图片、视频信息详情
 *//*


public class RequestMediaDetail {

    */
/** 错误码：file字段为空，未上传过文件 *//*

    public static final int FILE_NULL = 0x1;

    */
/** 错误码：真正失败 *//*

    public static final int ERROR_CODE = 0x2;

    public RequestMediaDetail() {}

    */
/**
     * 获取图片、视频集合
     *
     * @param id 工单id
     * @param fieldName 字段名称
     * @param callBack 回调监听
     *//*

    public static void getMediaList(String id, String fieldName, MediaCallBack callBack) {
        String url = NetWorkConfig.GET_MEDIA_DETAIL + id;
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor()).build();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Timber.d("media_data : failure");
                dealMediaListFailure(callBack, ERROR_CODE, ErrorConstant.ERROR_NETWORK);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws
                    IOException {
                Timber.d("media_data : response- " + response.toString());
                ResponseBody body = response.body();

                //网络异常
                if (response.code() != 200 || body == null) {
                    dealMediaListFailure(callBack, ERROR_CODE, ErrorConstant.ERROR_NETWORK);
                    return;
                }

                String json = body.string();
                Timber.d("media_data : bodyJson- " + json);
                try {
                    JSONObject jsonObject =JSONObject.parseObject(json);
                    int code = jsonObject.getInteger("code");
                    Timber.d("media_data : code- " + code);

                    //接口返回失败
                    if (code != 0) {
                        String message = jsonObject.getString("msg");
                        if (TextUtils.isEmpty(message)) {
                            dealMediaListFailure(callBack, ERROR_CODE, "数据解析异常");
                            return;
                        }

                        Timber.d("media_data : message- " + message);
                        if(ErrorConstant.SESSION_INVALID.equals(message)){
                            logout();
                            return;
                        }
                    }

                    //接口返回成功
                    JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                    if(dataJsonObject == null){
                        dealMediaListFailure(callBack, ERROR_CODE, "数据解析异常");
                        return;
                    }
                    Timber.d("media_data : dataJson- " + dataJsonObject);

                    //当前工单状态
                    String status = dataJsonObject.getString("state");

                    JSONObject fileJsonObject = dataJsonObject.getJSONObject("file");
                    if(fileJsonObject == null){
                        dealMediaListFailure(callBack, FILE_NULL, "未上传过文件");
                        return;
                    }
                    Timber.d("media_data : fileJson- " + fileJsonObject);
                    //媒体信息数组
                    ArrayList<MediaEntity> mediaList = new ArrayList<>();
                    //索引值数组
                    ArrayList<Integer> indexList = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : fileJsonObject.entrySet()) {
                        if(entry.getKey().startsWith(fieldName)){
                            if(fieldName.equals(entry.getKey())){
                                mediaList.add(new MediaEntity(entry.getKey(), NetWorkConfig.GET_FILE_URL+entry.getValue().toString()));
                            }

                            if(entry.getKey().length() > fieldName.length()){
                                String s = entry.getKey().substring(fieldName.length(), entry.getKey().length());
                                if(!TextUtils.isEmpty(s) && RegexUtils.checkDigit(s)){
                                    mediaList.add(new MediaEntity(entry.getKey(), NetWorkConfig.GET_FILE_URL+entry.getValue().toString()));
                                    indexList.add(Integer.parseInt(s));
                                }
                            }
                        }
                    }

                    //升序排序
                    Collections.sort(indexList);
                    Collections.sort(mediaList, new MediaComparator());
                    int maxIndex = indexList.size() > 0 ? indexList.get(indexList.size()-1) : -1;
                    dealMediaListSuccess(callBack, mediaList, maxIndex, status);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    */
/**
     * 分组获取图片、视频
     *
     * @param id 工单id
     * @param callBack 回调监听
     *//*

    public static void getMediaGroupMap(String id, GroupCallBack callBack){
        String url = NetWorkConfig.GET_MEDIA_DETAIL + id;
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor()).build();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Timber.d("media_data : failure");
                dealMediaGroupMapFailure(callBack, ERROR_CODE, ErrorConstant.ERROR_NETWORK);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws
                    IOException {
                Timber.d("media_data : response- " + response.toString());
                ResponseBody body = response.body();

                //网络异常
                if (response.code() != 200 || body == null) {
                    dealMediaGroupMapFailure(callBack,ERROR_CODE, ErrorConstant.ERROR_NETWORK);
                    return;
                }

                String json = body.string();
                Timber.d("media_data : bodyJson- " + json);
                try {
                    JSONObject jsonObject =JSONObject.parseObject(json);
                    int code = jsonObject.getInteger("code");
                    Timber.d("media_data : code- " + code);

                    //接口返回失败
                    if (code != 0) {
                        String message = jsonObject.getString("msg");
                        if (TextUtils.isEmpty(message)) {
                            dealMediaGroupMapFailure(callBack, ERROR_CODE, "数据解析异常");
                            return;
                        }

                        Timber.d("media_data : message- " + message);
                        if(ErrorConstant.SESSION_INVALID.equals(message)){
                            logout();
                            return;
                        }
                    }

                    //接口返回成功
                    JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                    if(dataJsonObject == null){
                        dealMediaGroupMapFailure(callBack, ERROR_CODE, "数据解析异常");
                        return;
                    }
                    Timber.d("media_data : dataJson- " + dataJsonObject);

                    JSONObject fileJsonObject = dataJsonObject.getJSONObject("file");
                    if(fileJsonObject == null){
                        dealMediaGroupMapFailure(callBack, FILE_NULL, "未上传过文件");
                        return;
                    }
                    Timber.d("media_data : fileJson- " + fileJsonObject);

                    Map<String, Integer> groupMap = new HashMap<>();
                    for (Map.Entry<String, Object> entry : fileJsonObject.entrySet()) {

                        if(!RegexUtils.hasDigit(entry.getKey())){
                            //key中不含有数字
                            Integer integer = groupMap.get(entry.getKey());
                            if(integer == null || integer == 0){
                                //当前map未录入该条数据
                                groupMap.put(entry.getKey(), 1);
                            }else {
                                //当前map有该条数据，对应value+1
                                groupMap.put(entry.getKey(),integer+1);
                            }
                        }else {
                            //key中含有数字
                            //截取数字之前的文字
                            String key = RegexUtils.splitNotNumber(entry.getKey());
                            Integer integer = groupMap.get(key);
                            if(integer == null || integer == 0){
                                //当前map未录入该条数据
                                groupMap.put(key, 1);
                            }else {
                                //当前map有该条数据，对应value+1
                                groupMap.put(key,integer+1);
                            }
                        }
                    }

                    dealMediaGroupMapSuccess(callBack, groupMap);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    */
/**
     * 获取图片、视频集合成功处理
     *
     * @param callBack 回调
     * @param list 集合信息
     *//*

    private static void dealMediaListSuccess(MediaCallBack callBack, List<MediaEntity> list, int maxIndex, String status){
        if(callBack != null){
            UiUtils.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    callBack.onSuccess(list, maxIndex, status);
                }
            });
        }
    }

    */
/**
     * 获取图片、视频集合错误处理
     *
     * @param callBack 回调
     * @param message 错误信息
     *//*

    private static void dealMediaListFailure(MediaCallBack callBack, int code, String message){
        if(callBack != null){
            UiUtils.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFailure(code, message);
                }
            });
        }
    }

    */
/**
     * 分组获取图片、视频成功处理
     *
     * @param callBack 回调
     * @param map 分组信息
     *//*

    private static void dealMediaGroupMapSuccess(GroupCallBack callBack, Map<String, Integer> map){
        if(callBack != null){
            UiUtils.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    callBack.onSuccess(map);
                }
            });
        }
    }

    */
/**
     * 分组获取获取图片、视频错误处理
     *
     * @param callBack 回调
     * @param message 错误信息
     *//*

    private static void dealMediaGroupMapFailure(GroupCallBack callBack, int code, String message){
        if(callBack != null){
            UiUtils.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFailure(code, message);
                }
            });
        }
    }

    */
/**
     * session过期退出登录
     *//*

    private static void logout(){
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginOutHelper.loginOut();
            }
        });
    }


    */
/**
     * 获取图片、视频集合回调
     *//*

    public interface MediaCallBack {

        */
/**
         * 获取图片、视频集合成功回调
         *
         * @param list 图片、视频集合
         * @param maxIndex 最大索引值
         * @param status 当前工单状态
         *//*

        void onSuccess(List<MediaEntity> list, int maxIndex, String status);

        */
/**
         * 获取图片、视频集合失败回调
         *
         * @param message 错误信息
         *//*

        void onFailure(int code, String message);
    }

    */
/**
     * 分组获取图片、视频集合
     *//*

    public interface GroupCallBack{

        */
/**
         * 分组获取图片、视频成功回调
         *
         * @param map 数据集合
         *//*

        void onSuccess(Map<String, Integer> map);

        */
/**
         * 分组获取图片、视频失败回调
         *
         * @param message 错误信息
         *//*

        void onFailure(int code, String message);
    }
}
*/
