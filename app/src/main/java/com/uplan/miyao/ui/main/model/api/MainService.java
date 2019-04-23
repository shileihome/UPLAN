package com.uplan.miyao.ui.main.model.api;


import com.uplan.miyao.net.constant.NetWorkConfig;
import com.uplan.miyao.ui.main.model.resp.StatusCountResp;
import com.uplan.miyao.ui.main.model.resp.UserInfoResp;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author：Wbin on 2018/4/9 17:10
 * Email：atwbin@163.com
 * Description：
 */
public interface MainService {

    /**
     * 用户信息
     */
    @GET(NetWorkConfig.USER_INFO)
    Observable<UserInfoResp> getUserInfo();

    @GET(NetWorkConfig.STATUS_COUNT)
    Observable<StatusCountResp> getStatusCount();
}
