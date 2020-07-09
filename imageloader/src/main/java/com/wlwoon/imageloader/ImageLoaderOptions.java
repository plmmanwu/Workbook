package com.wlwoon.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

import java.io.File;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;


/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class ImageLoaderOptions {
    private View viewContainer;  // 图片容器
    private String url;  // 图片地址
    private int resource;  // 图片地址
    private byte[] mByte;//图片地址
    private File mFile;//图片这地址
    private @DrawableRes
    int holderDrawable;  // 设置展位图
    private Drawable holderDrawable2;  // 设置展位图
    private ImageSize imageSize;  //设置图片的大小
    private int errorDrawable;  //是否展示加载错误的图片
    private Drawable errorDrawable2;  //是否展示加载错误的图片
    private boolean asGif=false;   //是否作为gif展示
    private boolean asBitmap=false;   //只支持bitmap
    private boolean isCrossFade=true; //是否渐变平滑的显示图片,默认为true
    private  boolean isSkipMemoryCache = false; //是否跳过内存缓存
    private  DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
    private  boolean blurImage = false; //是否使用高斯模糊
    private LoaderResultCallBack loaderResultCallBack;   // 返回图片加载结果
    private int blurValue;   // 高斯模糊参数，越大越模糊
    private int imageRadius= 0;
    private boolean isCircle=false;
    private boolean dontAnimate = false;
    private CircleWithBorder mCircleWithBorder;//圆形图片加边框

    private OnProgressListener mOnProgressListener;//下载进度回调

    private ImageLoaderOptions (Builder builder ){
        this.asGif=builder.asGif;
        this.holderDrawable2 = builder.holderDrawable2;
        this.errorDrawable2 = builder.errorDrawable2;
        this.dontAnimate = builder.dontAnimate;
        this.mOnProgressListener = builder.mOnProgressListener;
        this.asBitmap = builder.asBitmap;
        this.errorDrawable=builder.errorDrawable;
        this.holderDrawable=builder.holderDrawable;
        this.imageSize=builder.mImageSize;
        this.mCircleWithBorder = builder.mCircleWithBorder;
        this.isCrossFade=builder.isCrossFade;
        this.isSkipMemoryCache=builder.isSkipMemoryCache;
        this.mDiskCacheStrategy=builder.mDiskCacheStrategy;
        this.url=builder.url;
        this.mFile = builder.mFile;
        this.mByte = builder.mByte;
        this.resource=builder.resource;
        this.viewContainer=builder.mViewContainer;
        this.blurImage=builder.blurImage;
        this.loaderResultCallBack=builder.loaderResultCallBack;
        this.isCircle=builder.isCircle;
        this.blurValue=builder.blurValue;
        this.imageRadius=builder.imageRadius;
    }

    public LoaderResultCallBack getLoaderResultCallBack() {
        return loaderResultCallBack;
    }



    public int getBlurValue() {
        return blurValue;
    }
    public boolean needImageRadius() {
        return imageRadius>0;
    }
    public int getImageRadius() {
        return imageRadius;
    }
    public int getResource() {
        return resource;
    }

    public byte[] getByte() {
        return mByte;
    }

    public File getFile() {
        return mFile;
    }

    public boolean isBlurImage() {
        return blurImage;
    }

    public View getViewContainer() {
        return viewContainer;
    }

    public String getUrl() {
        return url;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public int getHolderDrawable() {
        return holderDrawable;
    }
    public Drawable getHolderDrawable2() {
        return holderDrawable2;
    }



    public ImageSize getImageSize() {
        return imageSize;
    }

    public CircleWithBorder getCircleWithBorder() {
        return mCircleWithBorder;
    }


    public int getErrorDrawable() {
        return errorDrawable;
    }

    public Drawable getErrorDrawable2() {
        return errorDrawable2;
    }

    public OnProgressListener getOnProgressListener() {
        return mOnProgressListener;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isDontAnimate() {
        return dontAnimate;
    }


    public boolean isCrossFade() {
        return isCrossFade;
    }



    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }



    public DiskCacheStrategy getDiskCacheStrategy() {
        return mDiskCacheStrategy;
    }



    public final static  class Builder{

        private int holderDrawable=-1;  // 设置展位图
        private View mViewContainer;  // 图片容器
        private String url;  // 图片地址
        private int resource = -1;  // 图片地址
        private byte[] mByte;//图片地址
        private File mFile;//图片这地址
        private ImageSize mImageSize;  //设置图片的大小
        private int errorDrawable=-1;  //是否展示加载错误的图片
        private boolean asGif=false;   //是否作为gif展示
        private boolean isCrossFade=false; //是否渐变平滑的显示图片
        private  boolean isSkipMemoryCache = false; //是否跳过内存缓存
        private  boolean blurImage = false; //是否使用高斯模糊
        private  DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
        private LoaderResultCallBack loaderResultCallBack;   // 返回图片加载结果
        private int blurValue=15;   // 高斯模糊参数，越大越模糊
        private int imageRadius= 0;
        private boolean isCircle=false;
        public boolean asBitmap = false;//只支持bitmap
        public OnProgressListener mOnProgressListener;
        public boolean dontAnimate;
        public Drawable holderDrawable2 ;
        public Drawable errorDrawable2 ;
        public CircleWithBorder mCircleWithBorder;


        public Builder(@NonNull View v, @NonNull String url){
            this.url=url;
            if (url.endsWith(".gif")) {
                this.asGif = true;
                this.isSkipMemoryCache = true;
            } else {
                this.asBitmap = true;
            }
            this.mViewContainer=v;
        }
        public Builder(@NonNull View v, @NonNull int resource){
            this.resource=resource;
            this.mViewContainer=v;
        }

        public Builder(@NonNull View v, @NonNull byte[] bytes){
            this.mByte=bytes;
            this.mViewContainer=v;
        }

        public Builder(@NonNull View v, @NonNull File file){
            this.mFile=file;
            this.mViewContainer=v;
        }

        public Builder placeholder(@DrawableRes int holderDrawable){
            this.holderDrawable=holderDrawable;
            return this;
        }
        public Builder placeholder( Drawable holderDrawable2){
            this.holderDrawable2=holderDrawable2;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade){
            this.isCrossFade=isCrossFade;
            return this;
        }
        public Builder blurImage(boolean blurImage){
            this.blurImage=blurImage;
            return this;
        }

        public Builder isCircle(){
            this.isCircle=true;
            return this;
        }


        public Builder imageRadiusPx(@Dimension(unit = Dimension.PX) int rdius){
            this.imageRadius= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, rdius, mViewContainer.getContext().getApplicationContext().getResources().getDisplayMetrics());

            return this;
        }
        public Builder imageRadiusDp(@Dimension(unit = Dimension.DP) int rdius){
            this.imageRadius= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rdius, mViewContainer.getContext().getApplicationContext().getResources().getDisplayMetrics());
            return this;
        }

        public Builder blurValue(@IntRange(from = 0) int blurvalue){
            this.blurValue=blurvalue;
            return this;
        }
        public Builder isSkipMemoryCache(boolean isSkipMemoryCache){
            this.isSkipMemoryCache=isSkipMemoryCache;
            return this;

        }
        public Builder override(int width,int height){
            this.mImageSize=new ImageSize(width,height);
            return this;
        }
        public Builder asGif(boolean asGif){
            this.asGif=asGif;
            return this;
        }
        public Builder dontAnimate(boolean dontAnimate){
            this.dontAnimate=dontAnimate;
            return this;
        }
        public Builder asBitmap(boolean asBitmap){
            this.asBitmap=asBitmap;
            return this;
        }

        public Builder setOnProgressListener(OnProgressListener onProgressListener){
            this.mOnProgressListener=onProgressListener;
            return this;
        }
        public Builder error(@DrawableRes int errorDrawable){
            this.errorDrawable=errorDrawable;
            return this;
        }
        public Builder error(Drawable errorDrawable){
            this.errorDrawable2=errorDrawable;
            return this;
        }
        public Builder error(LoaderResultCallBack resultCallBack){
            this.loaderResultCallBack=resultCallBack;
            return this;
        }

        public Builder diskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy){
            this.mDiskCacheStrategy=mDiskCacheStrategy;
            return this;

        }

        public ImageLoaderOptions build(){
            return  new ImageLoaderOptions(this);
        }


        public Builder loadCircleWithBorder(Context context, int px, int color) {
            this.mCircleWithBorder = new CircleWithBorder(context, px, color);
            return this;
        }
    }

    //对应重写图片size
    public final static class ImageSize{
        private int width=0;
        private int height=0;
        public ImageSize(int width, int heigh){
            this.width=width;
            this.height=heigh;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    public final static class CircleWithBorder{
        private Context mContext;
        private int px;
        private int color;

        public CircleWithBorder(Context context, int px, int color) {
            mContext = context;
            this.px = px;
            this.color = color;
        }

        public Context getContext() {
            return mContext;
        }

        public void setContext(Context context) {
            mContext = context;
        }

        public int getPx() {
            return px;
        }

        public void setPx(int px) {
            this.px = px;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
    //对应磁盘缓存策略
    public enum DiskCacheStrategy{
        All,NONE,SOURCE,RESULT,DEFAULT
    }
}
