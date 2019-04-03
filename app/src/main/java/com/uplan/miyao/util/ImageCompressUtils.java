package com.uplan.miyao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.uplan.miyao.base.UiUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Author: Created by zs on 2018/5/3.
 *
 * Description: 图片压缩工具类
 */

public class ImageCompressUtils {

    /** 实例 */
    private static volatile ImageCompressUtils mInstance;

    /** 压缩比例 */
    private static final double COMPRESS_SCALE = 1024 * 1024 * 0.7;

    /** cpu数量 */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /** 核心线程数 */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    /** 最大线程数 */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /** 超时时间 */
    private static final int KEEP_ALIVE = 1;

    /** 线程队列 */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>();

    /** 线程池管理线程 */
    private static final ExecutorService sExecutorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue);

    /** 私有构造 */
    private ImageCompressUtils(){}

    /**
     * 获取实例
     *
     * @return ImageCompressUtils
     */
    public static ImageCompressUtils getInstance(){
        if(mInstance == null){
            synchronized (ImageCompressUtils.class){
                if(mInstance == null){
                    mInstance = new ImageCompressUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取压缩后的文件地址
     *
     * @param filePath 原始文件地址
     */
    public void compressImage(String filePath, CompressCallback callback){
        //文件为空，返回原始文件地址
        if(TextUtils.isEmpty(filePath)){
            callback.callback(filePath);
            return;
        }

        //sd卡不可用，返回原始文件地址
        if(!SystemUtils.getSdcardState()){
            callback.callback(filePath);
            return;
        }

        //文件小于压缩比, 返回原始文件地址
        File originFile = new File(filePath);
        if(originFile.exists() && originFile.length() <= COMPRESS_SCALE){
            callback.callback(filePath);
            return;
        }

        String compressDir = UiUtils.getContext().getExternalFilesDir(null) + "/zrx_compress_image";
        File file = new File(compressDir);
        if(!file.exists()){
            boolean mkdirs = file.mkdirs();
        }

        String outPath = compressDir + "/" + System.currentTimeMillis() + ".jpg";

        sExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                try{
                    saveImageToCache(outPath, scaleBitmap(filePath, 850, 550));
                }catch (Exception e){
                    //压缩异常，返回原始文件地址
                    callback.callback(filePath);
                    return;
                }

                Timber.d("compress_image_end: " + System.currentTimeMillis());
                callback.callback(outPath);
            }
        });

    }

    /**
     * 裁剪压缩图片
     *
     * @param originPath 原始地址
     * @param targetW 目标宽度
     * @param targetH 目标高度
     * @return Bitmap
     */
    private Bitmap scaleBitmap(String originPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(originPath, bmOptions);

        bmOptions.inSampleSize = calculateInSampleSize(bmOptions, targetW, targetH);
        bmOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(originPath, bmOptions);
    }

    /**
     * 计算InSampleSize
     *
     * @param options 配置信息
     * @param reqWidth 目标宽度
     * @param reqHeight 目标高度
     * @return 缩放比
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        int height = options.outHeight;
        int width = options.outWidth;

        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while((halfHeight / inSampleSize) >= reqHeight &&
                    (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 保存图片之缓存目录
     *
     * @param outPath 输出路径
     * @param bitmap Bitmap
     */
    private void saveImageToCache(String outPath, Bitmap bitmap) {
        //进行质量压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;

        //循环判断如果压缩后图片是否大于压缩比,大于继续压缩
        while (baos.toByteArray().length > COMPRESS_SCALE) {
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少6
            options -= 6;
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩回调接口
     */
    public interface CompressCallback{

        /**
         * 回调
         * @param path 压缩完成后的路径
         */
        void callback(String path);
    }
}
