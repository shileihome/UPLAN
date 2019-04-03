package com.uplan.miyao.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/**
 * Author: Created by zs on 2018/5/7.
 *
 * Description: 通知相册扫描，更新系统相册
 */
public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mMediaScannerConnection;
    private File mFile;

    public MediaScanner(Context context, File file) {
        mFile = file;
        mMediaScannerConnection = new MediaScannerConnection(context, this);
        mMediaScannerConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMediaScannerConnection.scanFile(mFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMediaScannerConnection.disconnect();
    }
}
