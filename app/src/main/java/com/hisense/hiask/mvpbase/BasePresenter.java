package com.hisense.hiask.mvpbase;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by liudunjian on 2018/4/17.
 */

public abstract class BasePresenter<V extends IBaseView> {

    protected WeakReference<V> attachedView;
    protected Context context;

    protected BasePresenter(Context context,V v) {
        attachedView = new WeakReference<>(v);
        this.context = context.getApplicationContext();
    }

    protected abstract void onCreate();

    protected abstract void  onStart();

    protected abstract void onResume();

    protected abstract void onStop();

    protected abstract void onPause();

    protected abstract void onDestroy();

    protected void detachView() {
        if(isAttached())
            attachedView.clear();
    }

    protected boolean isAttached() {
        return attachedView!=null&&attachedView.get()!=null;
    }
}
