package com.wlwoon.contactspicker;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "lzy";  
    private ContactsAdapter mAdapter;
  
    public StickyItemDecoration(ContactsAdapter mAdapter) {
        super();  
        this.mAdapter = mAdapter;  
    }  
  
    //最后调用 绘制顶部固定的header  
    @Override  
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int count = parent.getChildCount();  
  
        for (int layoutPos = 0; layoutPos < count; layoutPos++) {  
            final View child = parent.getChildAt(layoutPos);
  
            final int adapterPos = parent.getChildAdapterPosition(child);  
            //只有在最上面一个item或者有header的item才绘制ItemDecoration  
            if (adapterPos != RecyclerView.NO_POSITION && (layoutPos == 0 || hasHeader(adapterPos))) {  
                View header = getHeader(parent, adapterPos).itemView;  
                c.save();  
                final int left = child.getLeft();  
                final int top = getHeaderTop(parent, child, header, adapterPos, layoutPos);  
                c.translate(left, top);  
                header.setTranslationX(left);  
                header.setTranslationY(top);  
                header.draw(c);  
                c.restore();  
            }  
        }  
    }  
  
    @Override  
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);  
        //得到该item所在的位置  
        int position = parent.getChildAdapterPosition(view);  
  
        int headerHeight = 0;  
        //在使用adapterPosition时最好的加上这个判断  
        if (position != RecyclerView.NO_POSITION && hasHeader(position)) {  
            //获取到ItemDecoration所需要的高度  
            View header = getHeader(parent, position).itemView;  
            headerHeight = header.getHeight();  
        }  
        outRect.set(0, headerHeight, 0, 0);  
    }  
  
    /** 
     * 判断是否有header 
     * 
     * @param position 
     * @return 
     */  
    private boolean hasHeader(int position) {  
        if (position == 0) {//第一个位置必然有  
            return true;  
        }  
        //判断和上一个的id不同则有header  
        int previous = position - 1;  
        return !mAdapter.getHeaderId(position).equals(mAdapter.getHeaderId(previous));  
    }  
  
    /** 
     * 获得自定义的Header 
     * 
     * @param parent 
     * @param position 
     * @return 
     */  
    private RecyclerView.ViewHolder getHeader(RecyclerView parent, int position) {  
        //创建HeaderViewHolder  
        ContactsAdapter.HeaderHolder holder = mAdapter.onCreateHeaderViewHolder(parent);
        final View header = holder.itemView;  
        //绑定数据  
        mAdapter.onBindHeaderViewHolder(holder, position);  
        //测量View并且layout  
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);  
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);  
        //根据父View的MeasureSpec和子view自身的LayoutParams以及padding来获取子View的MeasureSpec  
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);  
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,  
                parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);  
        //进行测量  
        header.measure(childWidth, childHeight);  
        //根据测量后的宽高放置位置  
        header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());  
        return holder;  
    }  
  
    /** 
     * 计算距离顶部的高度 
     * 
     * @param parent 
     * @param child 
     * @param header 
     * @param adapterPos 
     * @param layoutPos 
     * @return 
     */  
    private int getHeaderTop(RecyclerView parent, View child, View header, int adapterPos, int layoutPos) {  
        int headerHeight = header.getHeight();  
       int top = ((int) child.getY()) - headerHeight;  
        if (layoutPos == 0) {//处理最上面两个ItemDecoration切换时  
            final int count = parent.getChildCount();  
            final String currentId = mAdapter.getHeaderId(adapterPos);  
            for (int i = 1; i < count; i++) {  
                int adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(i));  
                if (adapterPosHere != RecyclerView.NO_POSITION) {  
                    String nextId = mAdapter.getHeaderId(adapterPosHere);  
                    //找到下一个不同类的view  
                    if (!nextId.equals(currentId)) {  
                        final View next = parent.getChildAt(i);  
                        //这里计算offset画个图会很清楚  
                        final int offset = ((int) next.getY()) - (headerHeight + getHeader(parent, adapterPosHere).itemView.getHeight());  
                        if (offset < 0) {//如果大于0的话，此时并没有切换  
                            return offset;  
                        } else {  
                            break;  
                        }  
                    }  
                }  
            }  
            //top不能小于0，否则最上面的ItemDecoration不会一直存在  
            top = Math.max(0, top);  
        }  
        return top;  
    }  
}  