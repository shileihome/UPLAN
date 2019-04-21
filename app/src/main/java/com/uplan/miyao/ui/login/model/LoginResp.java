package com.uplan.miyao.ui.login.model;

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
         * is_active : false
         * is_allow_login : true
         * is_fp : 0
         * is_fp_make : 0
         * is_mobile_verified : false
         * is_password_error_locked : false
         * is_pay_password_error_locked : false
         * last_login_time : 1555816166000
         * level_id : 1
         * login_count : 0
         * mobile : 133
         * name : 东方不败
         * open_id : ceshi
         * password : 1
         * password_continuous_errors : 0
         * pay_password_continuous_errors : 0
         * score : 0
         * time : 1555816166000
         * PLAY_SESSION : 70cfff7ba34fedbbfe5921a7f4f9642f3d39a274-___ID=14416570-ccb3-430d-8617-83a1b17d226c& ___TS=1556421860191
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
        public int level_id;
        public int login_count;
        public String mobile;
        public String name;
        public String open_id;
        public String password;
        public int password_continuous_errors;
        public int pay_password_continuous_errors;
        public int score;
        public long time;
        public String PLAY_SESSION;
    }
}
