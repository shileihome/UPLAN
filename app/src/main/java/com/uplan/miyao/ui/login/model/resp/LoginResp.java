package com.uplan.miyao.ui.login.model.resp;

import com.uplan.miyao.net.ResponseData;

import java.util.List;

/**
 * Author: Created by shilei on 2019/4/11-22:03
 * Description:
 */
public class LoginResp extends ResponseData {


    public List<DataBean> data;

    public static class DataBean {
        /**
         * account_id : 8ha6l8jbxe8w
         * age : 0
         * balance : 2002.56
         * client : 0
         * fp_level : 0
         * freeze : 0
         * id : 986
         * is_active : true
         * is_adequacy : 1
         * is_allow_login : true
         * is_fp : 0
         * is_fp_make : 0
         * is_mobile_verified : false
         * is_password_error_locked : false
         * is_pay_password_error_locked : false
         * last_login_ip : 111.196.145.229
         * last_login_time : 1557136484000
         * level_end_time : 1557315413000
         * level_id : 2
         * login_count : 2
         * mobile : 18612790610
         * name : 大路
         * open_id : oyZ1m0dhPmQUYtGrCd7DLVJG-hl4
         * password : 123456
         * password_continuous_errors : 0
         * pay_password : 8eb9b637c0be935f8d4dac7103cc4a70
         * pay_password_continuous_errors : 0
         * photo : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIoMZ0nMlGrcxCS2gx0sL6lpuZQRI0Nv0CZq1gnvMM39XDk8oAiaibpjqShN1mdU0Z4fP3tL2jTh7Lw/132
         * score : 20.9
         * sex : 0
         * time : 1554223521000
         * PLAY_SESSION : 9bde5359b7e3c8d23d742234a2acc16538668a13-___ID=0ff2f1c7-dca0-4894-8cd6-57f2d780dc33
         */

        public boolean is_active;
        public long level_end_time;
        public String mobile;
        public String name;
        public String password;
        public String PLAY_SESSION;
    }
}
