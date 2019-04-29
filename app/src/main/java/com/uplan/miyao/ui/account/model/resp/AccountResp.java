package com.uplan.miyao.ui.account.model.resp;

import com.uplan.miyao.net.ResponseData;

import java.util.List;

/**
 * Author: Created by shilei on 2019/4/29-16:35
 * Description:
 */
public class AccountResp extends ResponseData {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * porperty : 0.0
         * previousProfit : 0.0
         * accumulatedProfit : 0.0
         */

        public double porperty;
        public double previousProfit;
        public double accumulatedProfit;
    }
}
