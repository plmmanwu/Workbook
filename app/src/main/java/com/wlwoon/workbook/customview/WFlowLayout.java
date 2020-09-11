package com.wlwoon.workbook.customview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxy on 2020/9/10.
 */

public class WFlowLayout extends FrameLayout {
    public WFlowLayout(Context context) {
        super(context);
        init();
    }

    public WFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //先测量子view  遍历所有子元素的measure方法
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int measureWidth =0,measureHeight = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //计算子view所占宽度  需要减去padding值
        Map<String, Integer> compute = compute(widthSize);

        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measureWidth = compute.get("width");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measureHeight = compute.get("height");
        }

        setMeasuredDimension(measureWidth,measureHeight);

    }

    private Map<String, Integer> compute(int widthSize) {
        boolean aRow = true;//单行
        FrameLayout.LayoutParams marginLayoutParams;//margin值
        int rowWidth = getPaddingLeft()+getPaddingRight();//当前行已占宽度
        int columnHeight = getPaddingTop();//当前列已占高度
        int rowMaxHeight = 0;//当前行最大的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //获取测量宽高
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            //获取元素的margin值
            marginLayoutParams = ((FrameLayout.LayoutParams) child.getLayoutParams());
            int childWidth = measuredWidth+marginLayoutParams.leftMargin+marginLayoutParams.rightMargin;
            int childHeight = measuredHeight+marginLayoutParams.bottomMargin+marginLayoutParams.topMargin;

            //计算单行的最大高度
            rowMaxHeight = Math.max(rowMaxHeight, childHeight);
            //判断是否换行
            if (rowWidth + childWidth > widthSize) {
                //换行  重置数据
                rowWidth = getPaddingLeft()+getPaddingRight();
                rowMaxHeight = childHeight;
                columnHeight += rowMaxHeight;
                aRow = false;//非单行
            }
            rowWidth +=childWidth;

            //将子元素的位置存储起来  方便layout  不包括margin的位置
            child.setTag(new Rect(rowWidth-childWidth+marginLayoutParams.leftMargin,columnHeight+marginLayoutParams.topMargin,rowWidth-marginLayoutParams.rightMargin,columnHeight-marginLayoutParams.bottomMargin+childHeight));


        }


        Map<String, Integer> map = new HashMap<>();
        if (aRow) {
            map.put("width", rowWidth);
        } else {
            map.put("width",widthSize-getPaddingRight());
        }

        map.put("height",columnHeight+getPaddingBottom()+rowMaxHeight);

        return map;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect tag = (Rect) child.getTag();
            child.layout(tag.left,tag.top,tag.right,tag.bottom);
        }
    }


}
