package com.hisense.hiask.form;

import com.hisense.hiask.mvpbase.IBaseView;

import java.util.List;

/**
 * Created by liudunjian on 2018/4/26.
 */

public interface IForm extends IBaseView {
    void onDataPreparedStarted();

    void onDataPreparedSuccess(List<Object> data);

    void onDataPreparedFailed();

    void onIntentParsed(String title);
}
