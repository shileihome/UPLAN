package com.uplan.miyao.util;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;

/**
 * Author: Created by fangmingdong on 2018/5/3-下午3:59
 * Description:
 */
public class FileUtils {

    public static final String TAG = "FileUtils";

    /**
     * 扫描文件，更新系统相册
     *
     * @param context 上下文
     * @param file    待扫描文件
     */
    public static void scanFile(Context context, File file) {
        new MediaScanner(context, file);
    }

    /**
     * 图片保存后，更新系统相册
     * 小米手机正常
     * EMUI: Android8.0 中使用 matisse 拍摄的图片无法显示在选择器中
     *
     * @param file    图片文件夹
     * @param context context
     */
    public static void updateSystemImg(Context context, File fileDir, File file) {
        updateSystemImgByBroadcast(file, context);
//        updateSystemImgByMediaScanner(file, context);
        updateSystemImgByMediaScanner(fileDir, context);
        updateSystemImgHuaWei(fileDir, context);
    }

    /**
     * 小米手机中插入后相册中可以马上显示图片
     */
    private static void updateSystemImgByMediaStore(File file, Context context) {
        Log.d(TAG, "updateSystemImgByMediaStore: ");
        try {
            String s = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), FileUtils.getFileName(file), null);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "updateSystemImgForXiaomi: " + e.getMessage());
        }
    }

    /**
     * 小米中有效
     */
    private static void updateSystemImgByMediaScanner(File filePath, Context context) {
        Log.d(TAG, "updateSystemImgByMediaScanner: ");
        String[] paths = {filePath.getAbsolutePath()};
        String[] type = {"image/*"};
        MediaScannerConnection.scanFile(context
                , paths
                , type, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d(TAG, "onScanCompleted: " + path);
                    }
                });
    }

    /**
     * 广播通知系统相册更新
     * 小米中无效
     */
    private static void updateSystemImgByBroadcast(File filePath, Context context) {
        Log.d(TAG, "updateSystemImgByBroadcast: ");
        // 通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", filePath);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    private static void updateSystemImgHuaWei(File filePath, Context context) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FOLDER");
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", filePath);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }


    private static String getFileName(File file) {
        String filePath = file.getAbsolutePath();
        String name;
        try {
            name = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        } catch (Exception e) {
            return "";
        }
        return name;
    }
}
