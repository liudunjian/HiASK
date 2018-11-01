package com.hisense.hiask.mvpbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.hisense.hiautolayout.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liudunjian on 2018/4/17.
 */

public abstract class BaseActivity<V extends IBaseView, P extends BasePresenter<V>> extends AutoLayoutActivity {

    private static final String START_NEW_ACTIVITY_BUNDLE_KEY = "START_NEW_ACTIVITY_BUNDLE_KEY";

    protected P presenter;
    protected Unbinder unbinder;
    protected ImmersionBar immersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.finish();
            this.restart();
        }
        setContentView(layoutId());
        unbinder = ButterKnife.bind(this);
        if (isImmersionBarEnabled())
            initImmersionBar();
        initPresenter();
        initViewDisplay();
        initEventsAndListeners();
        if (presenter != null)
            presenter.onCreate();
    }

    protected abstract void initViewDisplay();

    protected abstract void initEventsAndListeners();

    protected abstract void initPresenter();

    protected abstract int layoutId();

    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        if (immersionBar == null)
            immersionBar = ImmersionBar.with(this);
        immersionBar.init();
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        if ((localView != null) && (imm != null)) {
            imm.hideSoftInputFromWindow(localView.getWindowToken(), 0);
        }
    }

    /**
     * 防止后台杀死导致页面重复
     */
    protected void restart() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null)
            presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
            presenter.detachView();
        }
        if (unbinder != null)
            unbinder.unbind();
        if (immersionBar != null)
            immersionBar.destroy();
    }

    public void startActivitySafely(Class<?> clz) {
        this.startActivitySafely(clz, null);
    }

    public void startActivitySafely(Class<?> clz, @Nullable Bundle bundle) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(START_NEW_ACTIVITY_BUNDLE_KEY, bundle);
        startActivity(intent);
    }

    public Bundle getActivityStartedBundle() {
        return getIntent().getBundleExtra(START_NEW_ACTIVITY_BUNDLE_KEY);
    }
}
