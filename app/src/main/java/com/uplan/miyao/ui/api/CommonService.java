package com.uplan.miyao.ui.api;

import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.net.constant.NetWorkConfig;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by zs on 2018/4/8.
 * <p>
 * 常用接口请求：登录、获取令牌
 */

public interface CommonService {


    /**
     * 登录
     *
     * @param userName 用户名称
     * @param password 密码
     * @return 登录结果
     */
    @FormUrlEncoded
    @POST(NetWorkConfig.LOGIN)
    Observable<ResponseData> login(@Field("email") String userName, @Field("password") String password);

    /**
     * 退出登录
     *
     * @return 退出结果
     */
    @GET(NetWorkConfig.LOGIN_OUT)
    Observable<ResponseData> loginOut();

    /**
     * 文件上传：视频，图片
     *
     * @param orderId 工单id
     * @param file    文件
     * @return 上传结果
     */
    @Multipart
    @POST(NetWorkConfig.FILE_UPLOAD)
    Observable<ResponseData> uploadFile(@Path("id") String orderId, @Part MultipartBody.Part file);


    /**
     * 进件信息录入
     *
     * @param id       id
     * @param jsonBody json
     * @return 提交结果
     */
    @FormUrlEncoded
    @POST(NetWorkConfig.PENDING_DATA_UPLOAD)
    Observable<ResponseData> uploadPendingData(@Path("id") String id, @Field("word") String jsonBody);

    /**
     * 更改提交状态
     *
     * @param id 工单id
     * @return 提交结果
     */
    @POST(NetWorkConfig.CHANGE_ENTER_STATE)
    Observable<ResponseData> changeEnterStatus(@Path("id") String id,@Path("state") String state);

    /**
     * 删除文件
     *
     * @param id 工单id
     * @param key 字段key
     * @return 删除结果
     */
    @GET(NetWorkConfig.DELETE_FILE)
    Observable<ResponseData> deleteFile(@Path("id") String id,@Path("key") String key);

}
