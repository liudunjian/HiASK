package com.hisense.hiask.collect;

import com.hisense.hiask.mvpbase.IBaseView;
import com.hisense.hibeans.question.QuestSvcBean;

import java.util.List;

/**
 * Created by liudunjian on 2018/6/20.
 */

public interface ICollect extends IBaseView {
    void onDataPreparedStarted();

    void onDataPreparedSuccess(List<Object> data);

    void onDataPreparedFailed();

    void onDataDeleteSuccess(QuestSvcBean item);


}
