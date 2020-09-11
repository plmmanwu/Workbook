package com.wlwoon.workbook.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by wxy on 2020/9/10.
 */

public class WSignView extends View {

    Paint gridPaint;//格子
    Paint edgePaint;//边框
    Paint paint;//轨迹
    private float[] mPoints;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mGridPath;
    private Path path;


    public WSignView(Context context) {
        super(context);
        init();
    }

    public WSignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WSignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WSignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //初始化画笔
    private void init() {
        edgePaint = new Paint();
        edgePaint.setAntiAlias(true);//抗锯齿
        edgePaint.setColor(Color.BLACK);
        edgePaint.setStrokeWidth(8f);
        edgePaint.setStyle(Paint.Style.STROKE);


        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//虚线
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(Color.GRAY);
        gridPaint.setStrokeWidth(2f);
        gridPaint.setPathEffect(new DashPathEffect(new float[]{60, 30}, 0));
        mGridPath = new Path();
        mGridPath.moveTo(0, 0);
        mGridPath.lineTo(600, 600);
        mGridPath.moveTo(0, 300);
        mGridPath.lineTo(600, 300);
        mGridPath.moveTo(0, 600);
        mGridPath.lineTo(600, 0);
        mGridPath.moveTo(300, 600);
        mGridPath.lineTo(300, 0);

        mPoints = new float[]{0, 0, 600, 600, 0, 300, 600, 300, 0, 600, 600, 0, 300, 600, 300, 0};

        paint = new Paint();
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3f);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();

        post(new Runnable() {//画布
            @Override
            public void run() {
                mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(Color.WHITE);
            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap==null) {
            return;
        }
        canvas.drawBitmap(mBitmap, 0, 0, paint);//画背景
        canvas.drawRect(new RectF(0, 0, 600, 600), edgePaint);//画边框
        canvas.drawPath(mGridPath, gridPaint);//画虚线网格
//        canvas.drawLines(mPoints,gridPaint);//只能画实线
        canvas.drawPath(path, paint);//签名轨迹

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down(event);
                break;
            case MotionEvent.ACTION_MOVE:
                move(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                up(event);
                break;
        }
        invalidate();
        return true;
    }

    private void up(MotionEvent event) {
        mCanvas.drawPath(path, paint);
        path.reset();
    }

    private void move(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (Math.abs(x - startX) > 2 || Math.abs(y - startY) > 2) {
            path.quadTo(startX, startY, (startX + x) / 2, (startY + y) / 2);
        }
        startX = x;
        startY = y;
    }

    float startX, startY;

    private void down(MotionEvent event) {
        path.reset();
        startX = event.getX();
        startY = event.getY();
        path.moveTo(startX, startY);
    }

    public void clean() {
        mCanvas.drawColor(Color.WHITE);
        invalidate();
    }
}
