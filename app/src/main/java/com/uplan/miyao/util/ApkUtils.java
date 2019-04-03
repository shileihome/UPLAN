package com.uplan.miyao.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * <pre>
 *     author : cfp
 *     e-mail : chengfangpeng@foxmail.com
 *     time   : 2017/08/11
 *     desc   : Apk工具
 *     version: 1.0
 * </pre>
 */
public class ApkUtils {

    /**
     * 安装一个apk文件
     * @param context
     * @param uriFile
     */
    public static void install(Context context, File uriFile) {

        // TODO: 17-8-11 SDK >= 24时需要做兼容
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载一个app
     * @param context
     * @param packageName
     */
    public static void uninstall(Context context, String packageName) {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE,packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 检测包名
     *
     * @param context 上下文
     * @param path apk路径
     * @return true: 验证包通过
     */
    public static boolean checkPackageName(Context context, String path) {
        PackageInfo packageInfo = context.getPackageManager()
                .getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            Timber.d("version_update--> packageInfo: " + packageInfo.packageName);
        }
        return packageInfo != null && TextUtils.equals(context.getPackageName(), packageInfo.packageName);

    }

    /**
     * 检测是否需要更新
     *
     * @param context     上下文
     * @param versionCode 版本号
     * @return true: 需要更新  false: 不需要更新
     */
    public static boolean checkNeedUpdate(Context context, int versionCode) {
        int localVersionCode = SystemUtils.getVersionCode(context);

        Timber.d("version_code: localVersionCode" + localVersionCode);
        Timber.d("version_code: remoteVersionCode" + versionCode);
        return versionCode > localVersionCode;
    }

    /**
     * 获取versionCode
     *
     * @return versionCode
     */
    public static int convertVersionCode(Context context, String versionName) {
        if (versionName.contains(".")) {
            String v = versionName.replace(".", "");
            if (RegexUtils.checkDigit(v)) {
                return MathUtils.parseIntSafely(v);
            }
        }
        return SystemUtils.getVersionCode(context) + 1;
    }



}
