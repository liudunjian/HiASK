package com.hisense.hiask.main.fragment.my;

import com.hisense.hiask.mvpbase.IBaseView;

import java.util.List;

/**
 * Created by liudunjian on 2018/5/17.
 */

public interface IMy extends IBaseView {

    void onDataPreparedSuccess(List<Object> data);

}
