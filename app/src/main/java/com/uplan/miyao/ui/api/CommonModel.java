package com.uplan.miyao.ui.api;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.net.UploadProgressInterceptor;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by zs on 2018/4/8.
 * <p>
 * 常用接口请求：登录、获取令牌
 */

public class CommonModel {



    /**
     * 退出登录
     *
     * @return 退出结果
     */
    public static Observable<ResponseData> loginOut() {
        return RxService.createApi(CommonService.class).loginOut();
    }

    /**
     * 提交进件录入信息
     *
     * @param id       工单id
     * @param jsonBody 提交的数据
     * @return 结果
     */
    public static Observable<ResponseData> uploadPendingData(String id, String jsonBody) {
        return RxService.createApi(CommonService.class).uploadPendingData(id, jsonBody);
    }

    /**
     * 上传文件
     *
     * @param orderId  工单 id
     * @param filePath 文件 path
     * @param fileName 文件名
     * @param listener 进度监听
     * @return 结果
     */
    public static Observable<ResponseData> uploadFile(String orderId, String filePath, String fileName, UploadProgressInterceptor.ProgressListener listener) {
        File file = new File(filePath);

        String type = "multipart/form-data";

        MediaType mediaType = MediaType.parse(type);
        RequestBody requestFile = RequestBody.create(
                mediaType,
                file
        );

        String fileExt = MimeTypeMap.getFileExtensionFromUrl(file.toString());
        if (TextUtils.isEmpty(fileExt)) {
            String[] split = filePath.split("\\.");
            if (split.length > 0) {
                fileExt = split[split.length - 1];
            }
        }
        String newFileName = System.currentTimeMillis() + "";
        MultipartBody.Part fileBodybody = MultipartBody.Part.createFormData(fileName,
                newFileName + "." + fileExt, requestFile);

        Timber.d("upload file, fileName:" + fileName + ", fileExt:" + fileExt + ", type:" + type + "newFileName:" + newFileName + ", filePath" + filePath);

        OkHttpClient.Builder builder = RxService.getOkHttpBuilderWithListener(filePath, listener);
        Retrofit retrofit = RxService.getRetrofit(builder.build());
        CommonService commonService = retrofit.create(CommonService.class);

        return commonService.uploadFile(orderId, fileBodybody);
    }

    /**
     * 删除文件
     *
     * @param orderId 工单id
     * @param key     字段名称
     * @return 删除结果
     */
    public static Observable<ResponseData> deleteFile(String orderId, String key) {
        return RxService.createApi(CommonService.class).deleteFile(orderId, key);
    }
}
