package com.uplan.miyao.ui.main.model.resp;

import com.uplan.miyao.net.ResponseData;

import java.util.List;

/**
 * Author: Created by shilei on 2019/6/3-19:21
 * Description:
 */
public class BannerInfoResp extends ResponseData {


    public List<DataBean> data;

    public static class DataBean {
        /**
         * faurl1 : http://22ju570648.iok.la/appClient/getCarouselurl?pic=f1
         * faurl2 : http://22ju570648.iok.la/appClient/getCarouselurl?pic=f2
         * faurl3 : http://22ju570648.iok.la/appClient/getCarouselurl?pic=f3
         * faxian1 : 1
         * faxian2 : 1
         * faxian3 : 1
         * shouurl1 : http://22ju570648.iok.la/appClient/getCarouselurl?pic=s1
         * shouurl2 : http://22ju570648.iok.la/appClient/getCarouselurl?pic=s2
         * shouurl3 : http://22ju570648.iok.la/appClient/getCarouselurl?pic=s3
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
        public String chanpin1;
        public String chanpin2;
        public String chanpin3;
        public String chanpin4;
        public String shouurl1;
        public String shouurl2;
        public String shouurl3;
        public String shouye1;
        public String shouye2;
        public String shouye3;
        public String churl1;
        public String churl2;
        public String churl3;
        public String churl4;
        public String ch1_isvip;
        public String ch2_isvip;
        public String ch3_isvip;
        public String ch4_isvip;
    }
}
