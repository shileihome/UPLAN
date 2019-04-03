/*
package com.uplan.miyao.app.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import UiUtils;

import java.io.File;
import java.math.BigDecimal;

*/
/**
 * Author: Created by zs on 2018/4/26.
 *
 * Description: 清除缓存工具类
 *//*


public class ClearCacheUtils {

    private static ClearCacheUtils mInstance;

    public static ClearCacheUtils getInstance() {
        if (mInstance == null) {
            mInstance = new ClearCacheUtils();
        }
        return mInstance;
    }

    */
/**
     * 清除图片所有缓存
     *//*

    public void clearAllCache(Context context, ClearCacheCallback callback) {

        try{
            //清除磁盘中的文件缓存
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Glide磁盘缓存
                    //压缩图片缓存
                    String compressDir = UiUtils.getContext().getExternalFilesDir(null) + "/zrx_compress_image";
                    File file = new File(compressDir);
                    if(file.exists()){
                        deleteDir(file);
                    }

                    UiUtils.getMainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            //清除完毕切回至主线程,清除内存中缓存
                            callback.clearFinish();
                        }
                    });
                }
            }).start();
        }catch (Exception e){
            callback.clearFinish();
        }
    }

    */
/**
     * 递归删除文件夹
     *
     * @param dir 文件夹
     * @return true: 删除成功 false: 删除失败
     *//*

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    */
/**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     *//*

    public String getCacheSize(Context context) {
        try {
            String compressCache = UiUtils.getContext().getExternalFilesDir(null) + "/zrx_compress_image";
            return totalSize <= 0 ? "" : getFormatSize(totalSize);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    */
/**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     *//*

    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    */
/**
     * 格式化单位
     *
     * @param size size
     * @return size
     *//*

    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    */
/**
     * 清除缓存回调
     *//*

    public interface ClearCacheCallback{

        */
/**
         * 清除结束
         *//*

        void clearFinish();
    }
}
*/
