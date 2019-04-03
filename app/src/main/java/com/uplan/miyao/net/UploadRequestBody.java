package com.uplan.miyao.net;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Author: Created by fangmingdong on 2018/4/10-下午1:57
 * Description: 网络请求体
 */
public class UploadRequestBody extends RequestBody {

    private final String mFilePath;
    protected RequestBody delegate;
    protected UploadProgressInterceptor.ProgressListener listener;

    protected CountingSink countingSink;
    private int currentProgress;

    public UploadRequestBody(RequestBody delegate, String filePath) {
        this.delegate = delegate;
        mFilePath = filePath;
        this.listener = UploadProgressInterceptor.LISTENER_MAP.get(filePath);
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;

        countingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(countingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            long fullLength = contentLength();
            bytesWritten += byteCount;

            int progress = (int) (100f * bytesWritten / fullLength);

            if (listener == null) {
                listener = UploadProgressInterceptor.LISTENER_MAP.get(mFilePath);
            }

            if (listener != null && progress != currentProgress) {
                listener.onProgress(mFilePath, progress);
            }
            if (listener != null && bytesWritten == fullLength) {
                listener = null;
            }

            currentProgress = progress;

            if (currentProgress>=100) {
                UploadProgressInterceptor.removeListener(mFilePath);
            }

        }
    }
}
