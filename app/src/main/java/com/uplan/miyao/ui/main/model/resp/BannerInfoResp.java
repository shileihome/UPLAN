package com.uplan.miyao.ui.main.model.resp;

import com.uplan.miyao.net.ResponseData;

import java.util.List;

/**
 * Author: Created by shilei on 2019/6/3-19:21
 * Description:
 */
public class BannerInfoResp extends ResponseData {

    /**
     * data : [{"faurl1":"1","faurl2":"1","faurl3":"1","faxian1":"1","faxian2":"1","faxian3":"1","shouurl1":"1","shouurl2":"1","shouurl3":"1","shouye1":"1","shouye2":"1","shouye3":"1"}]
     * msg : 0
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * faurl1 : 1
         * faurl2 : 1
         * faurl3 : 1
         * faxian1 : 1
         * faxian2 : 1
         * faxian3 : 1
         * shouurl1 : 1
         * shouurl2 : 1
         * shouurl3 : 1
         * shouye1 : 1
         * shouye2 : 1
         * shouye3 : 1
         */

        public String faurl1;
        public String faurl2;
        public String faurl3;
        public String faxian1;
        public String faxian2;
        public String faxian3;
        public String shouurl1;
        public String shouurl2;
        public String shouurl3;
        public String shouye1;
        public String shouye2;
        public String shouye3;
    }
}
