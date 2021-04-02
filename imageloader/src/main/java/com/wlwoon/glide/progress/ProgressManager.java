package com.wlwoon.glide.progress;

import android.util.Log;

import com.wlwoon.imageloader.OnProgressListener;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressManager {

    private static OkHttpClient okHttpClient;
    static volatile ProgressManager instance;

    public synchronized static ProgressManager getInstance() {
        if (instance == null) {
            synchronized (ProgressManager.class) {
                if (instance == null) {
                    instance = new ProgressManager();
                }
            }
        }
        return instance;
    }

    private OnProgressListener LISTENER = new OnProgressListener() {
        @Override
        public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
            Log.d("wxy  r_PM===", "bytesRead:" + bytesRead + ",totalBytesRead:" + totalBytes);
            if (mOnProgressListener != null) {
                mOnProgressListener.onProgress(imageUrl, bytesRead, totalBytes, isDone, exception);
            }
        }
    };

    OnProgressListener mOnProgressListener;

    public void addProgressListener(OnProgressListener progressListener) {
        mOnProgressListener = progressListener;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            Log.d("wxy OkHttpClient===", "url:" + request.url().toString());
                            return response.newBuilder()
                                    .body(new ProgressResponseBody(request.url().toString(), response.body(), LISTENER))
                                    .build();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }


}