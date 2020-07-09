package com.wlwoon.imageloader;


public interface OnProgressListener {

    void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception);
}