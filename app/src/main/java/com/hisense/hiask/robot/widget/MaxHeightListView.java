package com.hisense.hiask.robot.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.hisense.hiask.hiask.R;
import com.hisense.hitools.utils.LogUtils;

/**
 * Created by liudunjian on 2018/5/2.
 */

public class MaxHeightListView extends ListView {

    private int maxHeight;

    public MaxHeightListView(Context context) {
        this(context, null);
    }

    public MaxHeightListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMaxHeightListView(context, attrs);
    }


    private void initMaxHeightListView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightListView);
        try {
            maxHeight = ta.getDimensionPixelOffset(R.styleable.MaxHeightListView_list_max_height, -1);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight != -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
