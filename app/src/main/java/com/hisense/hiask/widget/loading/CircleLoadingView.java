package com.hisense.hiask.widget.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.hisense.hiask.hiask.R;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/5/2.
 */

public class CircleLoadingView extends FrameLayout {

    @BindView(R.id.view_circular_progress)
    CircularProgressView viewCircularProgress;
    @BindView(R.id.view_circle_loading_progress_layout)
    LinearLayout viewCircleLoadingProgressLayout;
    @BindView(R.id.view_circle_loading_failed_layout)
    LinearLayout viewCircleLoadingFailedLayout;

    private ILoadingRetryListener loadingRetryListener;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCircleLoadingView(context);
    }

    private void initCircleLoadingView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_circle_loading_view_layout, this);
        ButterKnife.bind(rootView);
    }

    @Override
    protected void onDetachedFromWindow() {
        viewCircularProgress.stopAnimation();
        super.onDetachedFromWindow();
    }

    public void startLoadingData() {
        viewCircleLoadingFailedLayout.setVisibility(GONE);
        viewCircleLoadingProgressLayout.setVisibility(VISIBLE);
        viewCircularProgress.startAnimation();
    }

    public ILoadingRetryListener getLoadingRetryListener() {
        return loadingRetryListener;
    }

    public void setLoadingRetryListener(ILoadingRetryListener loadingRetryListener) {
        this.loadingRetryListener = loadingRetryListener;
    }

    public void stopLoadingData() {
        viewCircularProgress.stopAnimation();
    }

    public void notifyLoadingDataFailed() {
        viewCircularProgress.stopAnimation();
        viewCircleLoadingProgressLayout.setVisibility(GONE);
        viewCircleLoadingFailedLayout.setVisibility(VISIBLE);
    }

    @OnClick(R.id.view_circle_loading_failed_layout)
    public void onViewClicked() {
        if(EmptyUtils.isNotEmpty(loadingRetryListener)) {
            loadingRetryListener.onRetryLoadingClicked();
        }
    }

    public interface ILoadingRetryListener {
        void onRetryLoadingClicked();
    }
}
