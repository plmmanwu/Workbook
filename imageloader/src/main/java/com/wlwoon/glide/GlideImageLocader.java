package com.wlwoon.glide;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wlwoon.glide.progress.GlideApp;
import com.wlwoon.glide.progress.ProgressManager;
import com.wlwoon.imageloader.IImageLoaderstrategy;
import com.wlwoon.imageloader.ImageLoaderConfig;
import com.wlwoon.imageloader.ImageLoaderOptions;
import com.wlwoon.imageloader.OnProgressListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by wuzhao on 2018/1/28.
 */

public class GlideImageLocader implements IImageLoaderstrategy {
    private static final String HTTP = "http";
    private Handler mainHandler = new Handler();
    private Handler mMainThreadHandler = new Handler();
    private long mLastBytesRead;
    private long mTotalBytes;
    private OnProgressListener internalProgressListener;
    private OnProgressListener onProgressListener;
    private boolean mLastStatus;
    private String mImageUrlObj;

    @Override
    public void showImage(@NonNull final ImageLoaderOptions options) {
        RequestOptions requestOptions = new RequestOptions();
        mImageUrlObj = options.getUrl();
        if (options.getHolderDrawable() != -1) {
            requestOptions.placeholder(options.getHolderDrawable());
        }
        if (options.getHolderDrawable2() != null) {
            requestOptions.placeholder(options.getHolderDrawable2());
        }
        if (options.getErrorDrawable() != -1) {
            requestOptions.fallback(options.getErrorDrawable());
        }
        if (options.getErrorDrawable2() != null) {
            requestOptions.fallback(options.getErrorDrawable2());
        }

        if (options.getDiskCacheStrategy() != ImageLoaderOptions.DiskCacheStrategy.DEFAULT) {
            if (ImageLoaderOptions.DiskCacheStrategy.NONE == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            } else if (ImageLoaderOptions.DiskCacheStrategy.All == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            } else if (ImageLoaderOptions.DiskCacheStrategy.SOURCE == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            } else if (ImageLoaderOptions.DiskCacheStrategy.RESULT == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
            }

        }

        if (options.isDontAnimate()) {
            requestOptions.dontAnimate();
        }

        if (options.isSkipMemoryCache()) {
            requestOptions.skipMemoryCache(true);
        }
        if (options.getImageSize() != null) {
            requestOptions.override(options.getImageSize().getWidth(), options.getImageSize().getHeight());
        }



        List<Transformation> list = new ArrayList<Transformation>();
        if (options.isBlurImage()) {
            list.add(new BlurTransformation(options.getBlurValue()));
//            requestOptions.transforms(new BlurTransformation(options.getBlurValue()));
        }
        if (options.needImageRadius()) {
            list.add(new RoundedCorners(options.getImageRadius()));
//            requestOptions.transforms(new RoundedCorners(options.getImageRadius()));
        }

        if (options.getCircleWithBorder() != null) {
            ImageLoaderOptions.CircleWithBorder circleWithBorder = options.getCircleWithBorder();
            list.add(new GlideCircleTransform(circleWithBorder.getContext(),circleWithBorder.getPx(),circleWithBorder.getColor()));
        }
        if (options.isCircle()) {
            list.add(new CircleTransformation());

//            requestOptions.transforms(new CircleTransformation());
        }
        if (list.size() > 0) {
            Transformation[] transformations = list.toArray(new Transformation[list.size()]);
            requestOptions.transforms(transformations);

        }

        //添加进度监听
        onProgressListener = options.getOnProgressListener();
        if ( onProgressListener != null) {
            addProgressListener();
//            ProgressManager.getInstance().addProgressListener(options.getOnProgressListener());
        } else {
            ProgressManager.getInstance().addProgressListener(null);
        }

        RequestBuilder builder = getRequestBuilder(options);
        builder.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, e);
//                ProgressManager.removeProgressListener(internalProgressListener);
                if (options.getLoaderResultCallBack() != null) {
                    options.getLoaderResultCallBack().onFail();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, null);
//                ProgressManager.removeProgressListener(internalProgressListener);
                if (options.getLoaderResultCallBack() != null) {
                    options.getLoaderResultCallBack().onSucc();
                }
                return false;
            }
        });

        builder.apply(requestOptions).into((ImageView) options.getViewContainer());
    }

    private RequestManager getRequestManager(View view) {
        return GlideApp.with(view);

    }

    private RequestBuilder getRequestBuilder(ImageLoaderOptions options) {
        RequestBuilder builder = null;

        if (options.isAsGif()) {
            builder = getRequestManager(options.getViewContainer()).asGif();
        } else if (options.isAsBitmap()) {
            builder = getRequestManager(options.getViewContainer()).asBitmap();
        } else {
            builder = getRequestManager(options.getViewContainer()).asBitmap();
        }

        if (!TextUtils.isEmpty(options.getUrl())) {
            builder.load(options.getUrl());
        } else if (options.getResource()!= -1){
            builder.load(options.getResource());
        } else if (options.getByte()!=null&&options.getByte().length>0) {
            builder.load(options.getByte());
        } else if (options.getFile() != null) {
            builder.load(options.getFile());
        }

        return builder;

    }

    @Override
    public void hideImage(@NonNull View view, int visiable) {
        if (view != null) {
            view.setVisibility(visiable);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideApp.get(context).clearMemory();

        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            GlideApp.get(context).clearDiskCache();

        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideApp.get(context).trimMemory(level);
        }
    }

    @Override
    public void pause(Context context) {
        GlideApp.with(context).pauseRequests();
    }

    @Override
    public void resume(Context context) {
        GlideApp.with(context).resumeRequests();

    }

    @Override
    public void init(Context context, ImageLoaderConfig config) {
        // 暂时不做配置

    }

    //进度监听
    private void addProgressListener() {
        if (TextUtils.isEmpty(mImageUrlObj) || !mImageUrlObj.startsWith(HTTP)) return;
        System.out.printf("wxy %s","addProgressListener 添加进度条监听");
        internalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
                System.out.printf("wxy %s","addProgressListener 收到进度回调");
                if (totalBytes == 0 || !TextUtils.equals(imageUrl, mImageUrlObj)) return;
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;

                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(bytesRead, totalBytes, isDone, exception);
//                if (isDone) {
//                    ProgressManager.removeProgressListener(this);
//                }
            }
        };
        ProgressManager.getInstance().addProgressListener(internalProgressListener);
    }

    private void mainThreadCallback(final long bytesRead, final long totalBytes, final boolean isDone, final Exception exception) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
                if (onProgressListener != null) {
                    onProgressListener.onProgress(mImageUrlObj, bytesRead, totalBytes, isDone, exception);
                }

//                if (onGlideImageViewListener != null) {
//                    onGlideImageViewListener.onProgress(percent, isDone, exception);
//                }
            }
        });
    }
}