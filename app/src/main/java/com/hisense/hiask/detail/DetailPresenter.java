package com.hisense.hiask.detail;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitools.utils.SpUtils;

/**
 * Created by liudunjian on 2018/6/14.
 */

public class DetailPresenter extends BasePresenter<IDetail> {

    private Gson gson = new Gson();
    private QuestSvcBean item;

    public DetailPresenter(Context context, IDetail iDetail) {
        super(context, iDetail);
    }

    @Override
    protected void onCreate() {

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

    public void collectQuestService() {

        NetManager.askService(context)
                .collectQuestService(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_ACCOUNT_ID, "0"),
                        item.getId(),
                        new BaseObserver<BaseEntity<Boolean>>() {
                            @Override
                            protected void onStart() {

                            }

                            @Override
                            protected void onSuccess(BaseEntity<Boolean> result) {
                                if(result.getErrNo() == 200) {
                                    if(isAttached()) {
                                        item.setIs_collected(1);
                                        attachedView.get().onDataCollectSuccess();
                                    }
                                }
                            }

                            @Override
                            protected void onFailure(ApiException e) {

                            }

                            @Override
                            protected void onFinish() {

                            }
                        });

    }

    public void parseBundle(Bundle bundle) {
        if (EmptyUtils.isEmpty(bundle))
            return;
        String data = bundle.getString(DetailActivity.DETAIL_QUEST_DATA_TRANSFER_KEY);
        if (EmptyUtils.isEmpty(data))
            return;
        item = gson.fromJson(data, QuestSvcBean.class);
        if (isAttached())
            attachedView.get().onIntentParsed(item);
    }
}
