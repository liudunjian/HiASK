package com.hisense.hiask.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * Created by liudunjian on 2018/5/3.
 */

public class RxRefreshLayout extends TwinklingRefreshLayout {
    public RxRefreshLayout(Context context) {
        super(context);
    }

    public RxRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isLoadingVisible||isRefreshVisible)
            return true;
        return super.onInterceptTouchEvent(ev);
    }
}
