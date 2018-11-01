package com.hisense.hiask.form;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hisense.hiask.main.MainActivity;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.utils.EmptyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class FormPresenter extends BasePresenter<IForm> {

    private SvcBean svcBean;

    private Gson gson = new Gson();

    public FormPresenter(Context context, IForm iForm) {
        super(context, iForm);
    }

    @Override
    protected void onCreate() {
        requestDataFromNetwork();
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
        String data = bundle.getString(MainActivity.MAIN_FORM_DATA_TRANSFER_KEY);
        if (EmptyUtils.isEmpty(data))
            return;
        svcBean = gson.fromJson(data, SvcBean.class);
        if (isAttached())
            attachedView.get().onIntentParsed(svcBean.getName());
    }

    /*******************network methods*****************/
    public void requestDataFromNetwork() {

        if (EmptyUtils.isEmpty(svcBean))
            return;

        NetManager.askService(context).fetchFormServices(Integer.valueOf(svcBean.getId()), new BaseObserver<BaseEntity<ArrayList<SvcBean>>>() {
            @Override
            protected void onStart() {
                if (isAttached())
                    attachedView.get().onDataPreparedStarted();
            }

            @Override
            protected void onSuccess(BaseEntity<ArrayList<SvcBean>> result) {
                if (result.getErrNo() == 200) {
                    if (isAttached())
                        attachedView.get().onDataPreparedSuccess(packFormService(result.getSST()));
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

    /******************private methods******************/
    private List<Object> packFormService(ArrayList<SvcBean> data) {
        List<Object> list = new ArrayList<>();
        list.addAll(data);
        return list;
    }

}
