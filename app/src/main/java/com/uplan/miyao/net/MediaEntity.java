package com.uplan.miyao.net;

/**
 * Author: Created by zs on 2018/4/18.
 *
 * Description: 媒体类信息
 */

public class MediaEntity {

    public String fieldName;//字段名称
    public String url;//资源地址

    public MediaEntity(String fieldName, String url){
        this.fieldName = fieldName;
        this.url = url;
    }

}
