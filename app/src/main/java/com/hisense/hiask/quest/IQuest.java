package com.hisense.hiask.quest;

import com.hisense.hiask.mvpbase.IBaseView;
import com.hisense.hibeans.question.QuestSvcBean;

import java.util.List;

/**
 * Created by liudunjian on 2018/6/11.
 */

public interface IQuest extends IBaseView {
    void onIntentParsed(String name);

    void onCategoryPreparedStarted();

    void onCategoryPreparedSuccess(List<Object> data);

    void onCategoryPreparedFailed();

    void onContentPreparedStarted();

    void onContentPreparedSuccess(List<Object> data);

    void onContentPreparedFailed();

    void onDataCollectSuccess(QuestSvcBean item);
}
