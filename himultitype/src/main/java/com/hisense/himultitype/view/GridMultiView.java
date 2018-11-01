package com.hisense.himultitype.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hisense.himultitype.R;
import com.hisense.himultitype.base.BaseMultiView;

/**
 * Created by liudunjian on 2018/4/20.
 */

public class GridMultiView extends BaseMultiView {

    private int orientation;
    private boolean reverseLayout;
    private int spanSize;


    public GridMultiView(Context context) {
        this(context, null);
    }

    public GridMultiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridMultiView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGridMultiView(context, attrs);
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isReverseLayout() {
        return reverseLayout;
    }

    public void setReverseLayout(boolean reverseLayout) {
        this.reverseLayout = reverseLayout;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    private void initGridMultiView(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GridMultiView);
        try {
            orientation = ta.getInt(R.styleable.GridMultiView_grid_orientation, LinearLayout.HORIZONTAL);
            reverseLayout = ta.getBoolean(R.styleable.GridMultiView_grid_reverse_layout, false);
            spanSize = ta.getInt(R.styleable.GridMultiView_span_size, 4);
        } finally {
            ta.recycle();
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanSize, orientation, reverseLayout);
        setLayoutManager(gridLayoutManager);
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return (GridLayoutManager) super.getLayoutManager();
    }
}
