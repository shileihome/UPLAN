package com.uplan.miyao.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by zs on 2017/10/11.
 *
 * Bitmap工具类，有效避免OOM
 */

public class BitmapUtils {

    /**
     * 解析图片
     *
     * @param resources 资源文件
     * @param resId 资源Id
     * @param reqWidth 目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight){
        //只获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //将inJustDecodeBound设置为true
        options.inJustDecodeBounds = true;
        //此时返回的Bitmap为null, 携带来图片的相关信息
        BitmapFactory.decodeResource(resources, resId, options);

        //计算inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //使用获取的inSampleSize再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 计算InSampleSize
     *
     * @param options 配置信息
     * @param reqWidth 目标宽度
     * @param reqHeight 目标高度
     * @return 缩放比
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        //源图片的高度和宽度
        int height = options.outHeight;
        int width = options.outWidth;

        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            //计算实际宽高和目标宽高的比率
            int heightRatio =  Math.round((float) height / (float)reqHeight);
            int widthRatio =  Math.round((float) width / (float)reqWidth);
            //选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap stringToBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(string, Base64.DEFAULT);
            bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 解析图片
     * @param string
     * @return
     */
    public static Bitmap stringToBitmapIgnoreBase64(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            bitmap=BitmapFactory.decodeByteArray(string.getBytes(), 0, string.getBytes().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
