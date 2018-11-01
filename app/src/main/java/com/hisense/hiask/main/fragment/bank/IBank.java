package com.hisense.hiask.main.fragment.bank;

import com.hisense.hiask.mvpbase.IBaseView;

import java.util.List;

/**
 * Created by liudunjian on 2018/5/11.
 */

public interface IBank extends IBaseView{
    void onDataPreparedStarted();

    void onDataPreparedSuccess(List<Object> data);

    void onDataPreparedFailed();
}
