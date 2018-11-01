package com.hisense.hiask.detail;

import com.hisense.hiask.mvpbase.IBaseView;
import com.hisense.hibeans.question.QuestSvcBean;

/**
 * Created by liudunjian on 2018/6/14.
 */

public interface IDetail extends IBaseView {
    void onIntentParsed(QuestSvcBean item);
    void onDataCollectSuccess();
}
