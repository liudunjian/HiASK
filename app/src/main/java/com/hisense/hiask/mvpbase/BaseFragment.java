package com.hisense.hiask.mvpbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liudunjian on 2018/5/11.
 */

public abstract class BaseFragment<V extends IBaseView, P extends BasePresenter<V>> extends Fragment {

    protected P presenter;
    protected Unbinder unbinder;
    protected ImmersionBar immersionBar;
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity)
            activity = (BaseActivity) context;
        else
            throw new UnsupportedOperationException("你必须继承BaseFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewDisplay();
        initEventsAndListeners();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isImmersionEnabled())
            initImmersionBar();
        if (presenter == null)
            initPresenter();
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.onResume();
    }

    @Override
    public void onPause() {
        if (presenter != null)
            presenter.onPause();
        super.onPause();

    }

    @Override
    public void onStop() {
        if (presenter != null)
            presenter.onStop();
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        if (unbinder != null)
            unbinder.unbind();
        if (immersionBar != null)
            immersionBar.destroy();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    abstract protected int layoutId();

    abstract protected void initPresenter();

    abstract protected void initViewDisplay();

    abstract protected void initEventsAndListeners();

    protected boolean isImmersionEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        if (immersionBar == null)
            immersionBar = ImmersionBar.with(this);
        immersionBar.init();
    }
}
