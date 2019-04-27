package com.uplan.miyao.ui.main.model.resp;

import com.uplan.miyao.net.ResponseData;

/**
 * Author：Wbin on 2018/4/9 16:55
 * Email：atwbin@163.com
 * Description：用户信息
 */
public class UserInfoResp extends ResponseData {

    public DataResp data;

    public static class DataResp {
        public boolean authenticated;
        public PrincipalData principal;

        public static class PrincipalData {
            public String name;
            public String email;
            public String tel;
        }
    }

}
