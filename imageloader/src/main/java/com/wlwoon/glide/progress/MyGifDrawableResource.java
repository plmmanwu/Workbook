package com.wlwoon.glide.progress;


import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.resource.drawable.DrawableResource;

import androidx.annotation.NonNull;
import pl.droidsonroids.gif.GifDrawable;

/**
 * @author Administrator
 * @date 2018/1/12 14:49
 */

public class MyGifDrawableResource extends DrawableResource<GifDrawable>
        implements Initializable {

    // Public API.
    @SuppressWarnings("WeakerAccess")
    public MyGifDrawableResource(GifDrawable drawable) {
        super(drawable);
    }

    @NonNull
    @Override
    public Class<GifDrawable> getResourceClass() {
        return GifDrawable.class;
    }

    @Override
    public int getSize() {
        return drawable.getFrameByteCount() * drawable.getNumberOfFrames();
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.recycle();
    }

    @Override
    public void initialize() {
        drawable.seekToFrameAndGet(0).prepareToDraw();
    }
}