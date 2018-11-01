package com.hisense.himultitype.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hisense.himultitype.R;
import com.hisense.himultitype.base.BaseMultiView;

/**
 * Created by liudunjian on 2018/4/20.
 */

public class LinearMultiView extends BaseMultiView {

    private int orientation;
    private boolean reverseLayout;

    public LinearMultiView(Context context) {
        this(context, null);
    }

    public LinearMultiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearMultiView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLinearMultiView(context,attrs);
    }

    private void initLinearMultiView(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinearMultiView);
        try {
            orientation = ta.getInt(R.styleable.LinearMultiView_linear_orientation, LinearLayout.HORIZONTAL);
            reverseLayout = ta.getBoolean(R.styleable.LinearMultiView_linear_reverse_layout, false);
        } finally {
            ta.recycle();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,orientation,reverseLayout);
        setLayoutManager(linearLayoutManager);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) super.getLayoutManager();
    }
}
