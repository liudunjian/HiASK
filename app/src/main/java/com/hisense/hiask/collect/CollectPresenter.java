package com.hisense.hiask.collect;

import android.content.Context;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/6/20.
 */

public class CollectPresenter extends BasePresenter<ICollect> {

    public CollectPresenter(Context context, ICollect iCollect) {
        super(context, iCollect);
    }

    @Override
    protected void onCreate() {
        requestCollectedQuestServices();
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

    public void requestCollectedQuestServices() {
        NetManager.askService(context).fetchCollectedQuestServices(
                SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_ACCOUNT_ID, "0"),
                new BaseObserver<BaseEntity<ArrayList<QuestSvcBean>>>() {
                    @Override
                    protected void onStart() {
                        if (isAttached())
                            attachedView.get().onDataPreparedStarted();
                    }

                    @Override
                    protected void onSuccess(BaseEntity<ArrayList<QuestSvcBean>> result) {
                        if (result.getErrNo() == 200) {
                            if (isAttached())
                                attachedView.get().onDataPreparedSuccess(packCollectedQuestServices(result.getSST()));
                        } else {
                            this.onFailure(new ApiException(result.getErrNo(), result.getErrMsg()));
                        }
                    }

                    @Override
                    protected void onFailure(ApiException e) {
                        if (isAttached())
                            attachedView.get().onDataPreparedFailed();
                    }

                    @Override
                    protected void onFinish() {

                    }
                });
    }

    public void deleteCollectedQuestService(final QuestSvcBean item) {
        NetManager.askService(context).deleteCollectedQuestService(Integer.valueOf(item.getId()),
                new BaseObserver<BaseEntity<Boolean>>() {
                    @Override
                    protected void onStart() {

                    }

                    @Override
                    protected void onSuccess(BaseEntity<Boolean> result) {
                        if(result.getErrNo() == 200) {
                            if(isAttached())
                                attachedView.get().onDataDeleteSuccess(item);
                        }else {
                            new ApiException(result.getErrNo(),result.getErrMsg());
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

    private List<Object> packCollectedQuestServices(ArrayList<QuestSvcBean> items) {
        List<Object> data = new ArrayList<>();
        data.addAll(items);
        return data;
    }
}
