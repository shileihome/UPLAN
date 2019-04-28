package com.uplan.miyao.ui.vip.model.resp;

import com.uplan.miyao.net.ResponseData;

import java.util.List;

/**
 * Author: Created by shilei on 2019/4/28-18:20
 * Description:
 */
public class VipDetailResp extends ResponseData {


    public List<DataBean> data;

    public static class DataBean {
        /**
         * return_code : SUCCESS
         * return_msg : OK
         * appid : wx51f14963092d74d5
         * mch_id : 1487192012
         * nonce_str : O8ltKApSUGdNCwlz
         * sign : 66969F6406D3561458C59C4D0D4A5A99
         * result_code : SUCCESS
         * prepay_id : wx28180450721162e7ac42d0191696202246
         * trade_type : APP
         */

        public String return_code;
        public String return_msg;
        public String appid;
        public String mch_id;
        public String nonce_str;
        public String sign;
        public String result_code;
        public String prepay_id;
        public String trade_type;
    }
}
