package com.hisense.hiask.main.fragment.home;

import android.content.Context;

import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.main.HomeHeaderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/10/31.
 */

public class HomePresenter extends BasePresenter<IHome> {

    public HomePresenter(Context context, IHome iHome) {
        super(context, iHome);
    }


    /**************life circle methods*************/
    @Override
    protected void onCreate() {
        loadViewData();
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onResume() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onDestroy() {

    }

    private void loadViewData() {
        List<Object> list = new ArrayList<>();
        list.add(new HomeHeaderBean());

        if(isAttached())
            attachedView.get().onViewDataPrepared(list);
    }
}
