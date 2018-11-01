package com.hisense.hiask.quest;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.form.FormActivity;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitools.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/6/11.
 */

public class QuestPresenter extends BasePresenter<IQuest> {

    private SvcBean svcBean;
    private Gson gson = new Gson();

    public QuestPresenter(Context context, IQuest iQuest) {
        super(context, iQuest);
    }

    @Override
    protected void onCreate() {
        requestQuestCategoryFromNetwork();
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

    public void parseBundle(Bundle bundle) {
        if (EmptyUtils.isEmpty(bundle))
            return;
        String data = bundle.getString(FormActivity.FORM_QUEST_DATA_TRANSFER_KEY);
        if (EmptyUtils.isEmpty(data))
            return;
        svcBean = gson.fromJson(data, SvcBean.class);
        if (isAttached())
            attachedView.get().onIntentParsed(svcBean.getName());
    }

    public void requestQuestCategoryFromNetwork() {

        NetManager.askService(context).fetchFormServices(Integer.valueOf(svcBean.getId()), new BaseObserver<BaseEntity<ArrayList<SvcBean>>>() {
            @Override
            protected void onStart() {
                if (isAttached())
                    attachedView.get().onCategoryPreparedStarted();
            }

            @Override
            protected void onSuccess(BaseEntity<ArrayList<SvcBean>> result) {
                if (result.getErrNo() == 200) {
                    if (isAttached())
                        attachedView.get().onCategoryPreparedSuccess(packCategoryService(result.getSST()));
                } else {
                    new ApiException(result.getErrNo(), result.getErrMsg());
                }
            }

            @Override
            protected void onFailure(ApiException e) {
                if (isAttached())
                    attachedView.get().onCategoryPreparedFailed();
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    public void requestQuestContentFromNetwork(SvcBean item) {

        NetManager.askService(context).fetchQuestServices(Integer.valueOf(item.getId()), 1,
                SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_ACCOUNT_ID, "0"), new BaseObserver<BaseEntity<ArrayList<QuestSvcBean>>>() {
                    @Override
                    protected void onStart() {

                    }

                    @Override
                    protected void onSuccess(BaseEntity<ArrayList<QuestSvcBean>> result) {
                        if (result.getErrNo() == 200) {
                            if (isAttached())
                                attachedView.get().onContentPreparedSuccess(packQuestContentService(result.getSST()));
                        } else {
                            this.onFailure(new ApiException(result.getErrNo(), result.getErrMsg()));
                        }
                    }

                    @Override
                    protected void onFailure(ApiException e) {
                        if (isAttached())
                            ;
                    }

                    @Override
                    protected void onFinish() {

                    }
                });
    }

    public void collectQuestService(final QuestSvcBean item) {

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
                                    if(isAttached())
                                        attachedView.get().onDataCollectSuccess(item);
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


    /***************private methods******************/


    private List<Object> packCategoryService(ArrayList<SvcBean> items) {
        List<Object> list = new ArrayList<>();
        list.addAll(items);
        return list;
    }

    private List<Object> packQuestContentService(List<QuestSvcBean> items) {
        List<Object> list = new ArrayList<>();
        list.addAll(items);
        return list;
    }
}
