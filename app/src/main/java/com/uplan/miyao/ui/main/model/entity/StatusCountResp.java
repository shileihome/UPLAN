package com.uplan.miyao.ui.main.model.entity;

import com.google.gson.annotations.SerializedName;
import com.uplan.miyao.net.ResponseData;

/**
 * Author: Created by zs on 2018/4/16.
 *
 * Description: 各状态数量实体信息
 */

public class StatusCountResp extends ResponseData {


    /**
     * data : {"合同资料":0,"待进件":3,"放款资料":0,"放车资料":0,"资料邮寄":0}
     */

    public DataResp data;

    public static class DataResp {

        /**
         * 合同资料 : 0
         * 待进件 : 3
         * 放款资料 : 0
         * 放车资料 : 0
         * 资料邮寄 : 0
         */
        @SerializedName("合同资料")
        public int contractCount;
        @SerializedName("待进件")
        public int pendingCount;
        @SerializedName("放款资料")
        public int loanCount;
        @SerializedName("放车资料")
        public int carCount;
        @SerializedName("资料邮寄")
        public int sendCount;
        @SerializedName("大数据退回")
        public int bigDataCount;
    }
}
