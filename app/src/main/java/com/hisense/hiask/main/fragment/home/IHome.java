package com.hisense.hiask.main.fragment.home;

import com.hisense.hiask.mvpbase.IBaseView;

import java.util.List;

/**
 * Created by liudunjian on 2018/10/31.
 */

public interface IHome extends IBaseView {

    void onViewDataPrepared(List<Object> objects);
}
