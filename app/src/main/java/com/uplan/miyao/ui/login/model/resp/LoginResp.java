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
         * age : 0
         * balance : 0
         * client : 0
         * fp_level : 0
         * freeze : 0
         * id : 130
         * is_active : true
         * is_allow_login : true
         * is_fp : 0
         * is_fp_make : 0
         * is_mobile_verified : false
         * is_password_error_locked : false
         * is_pay_password_error_locked : false
         * last_login_time : 1555816166000
         * last_logout_time : 1556599067000
         * level_end_time : 1559201249000
         * level_id : 2
         * login_count : 0
         * mobile : 133
         * name : 东方不败
         * open_id : ceshi
         * password : 4
         * password_continuous_errors : 0
         * pay_password_continuous_errors : 0
         * score : 0.01
         * time : 1555816166000
         * PLAY_SESSION : ae210547d0dd7aa8e8eae8fa74db107b0cd878d6-___ID=f5fdeab2-64b4-4a1c-8c29-30fb6a46b4be&___TS=1557290979545
         */

        public int age;
        public int balance;
        public int client;
        public int fp_level;
        public int freeze;
        public int id;
        public boolean is_active;
        public boolean is_allow_login;
        public int is_fp;
        public int is_fp_make;
        public boolean is_mobile_verified;
        public boolean is_password_error_locked;
        public boolean is_pay_password_error_locked;
        public long last_login_time;
        public long last_logout_time;
        public long level_end_time;
        public int level_id;
        public int login_count;
        public String mobile;
        public String name;
        public String open_id;
        public String password;
        public int password_continuous_errors;
        public int pay_password_continuous_errors;
        public double score;
        public long time;
        public String PLAY_SESSION;
    }
}
