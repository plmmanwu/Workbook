package com.wlwoon.glide.progress;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import androidx.annotation.NonNull;

@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
//        super.registerComponents(context, glide, registry);
        registry.prepend(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getInstance().getOkHttpClient()));
                registry.prepend(
                Registry.BUCKET_GIF,
                InputStream.class,
                pl.droidsonroids.gif.GifDrawable.class,  // <-------------- here
                new MyStreamGifDecoder(registry.getImageHeaderParsers(), glide.getArrayPool()));
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // Apply options to the builder here.
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
    }
}