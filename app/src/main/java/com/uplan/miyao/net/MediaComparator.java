package com.uplan.miyao.net;


import com.uplan.miyao.util.MathUtils;
import com.uplan.miyao.util.RegexUtils;

import java.util.Comparator;

/**
 * Author: Created by zs on 2018/5/31.
 *
 * Description: 照片/视频按照上传顺序排序
 */

public class MediaComparator implements Comparator<MediaEntity> {

    @Override
    public int compare(MediaEntity o1, MediaEntity o2) {
        try{
            if(!RegexUtils.hasDigit(o1.fieldName)){
                return -1;
            }

            if(!RegexUtils.hasDigit(o2.fieldName)){
                return 1;
            }

            String key = RegexUtils.splitNotNumber(o1.fieldName);
            Integer s1 = MathUtils.parseIntSafely(o1.fieldName.substring(key.length(),o1.fieldName.length()));
            Integer s2 = MathUtils.parseIntSafely(o2.fieldName.substring(key.length(),o2.fieldName.length()));
            if(s1 > s2){
                return 1;
            }else {
                return -1;
            }
        }catch (Exception ex){
            return 1;
        }
    }
}
