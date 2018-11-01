package com.hisense.hiask.main.fragment.bank;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.main.BankBannerBean;
import com.hisense.hibeans.main.BankHeaderBean;
import com.hisense.hibeans.main.SpaceBean;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.net.BankModel;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.broadcast.NetworkListener;
import com.hisense.hitools.utils.LogUtils;
import com.hisense.hitools.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/5/11.
 */

public class BankPresenter extends BasePresenter<IBank> {

    private NetworkListener networkListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NetworkListener.HANDLER_NETWORK_CHANGE_MSG:
                    processNetworkState(msg.arg1);
                    break;
            }
        }
    };

    protected BankPresenter(Context context, IBank iBank) {
        super(context, iBank);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onResume() {
        registerNetworkListener();
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onPause() {
        unRegisterNetworkListener();
    }

    @Override
    protected void onDestroy() {

    }

    /***********************network methods********************/
    public void requestBankServices() {

        NetManager.askService(context).fetchBankServices(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_ACCOUNT_ID, "0"),
                new BaseObserver<BaseEntity<BankModel>>() {
                    @Override
                    protected void onStart() {
                        if (isAttached())
                            attachedView.get().onDataPreparedStarted();
                    }

                    @Override
                    protected void onSuccess(BaseEntity<BankModel> result) {
                        if (result.getErrNo() == 200) {
                            if (isAttached())
                                attachedView.get().onDataPreparedSuccess(packBankService(result.getSST()));
                        } else {
                            this.onFailure(new ApiException(result.getErrNo(), result.getErrMsg()));
                        }
                    }

                    @Override
                    protected void onFailure(ApiException e) {
                        LogUtils.d("onFailure----------------------");
                        if (isAttached())
                            attachedView.get().onDataPreparedFailed();
                    }

                    @Override
                    protected void onFinish() {

                    }
                });
    }


    private void registerNetworkListener() {
        if (networkListener == null)
            networkListener = new NetworkListener(handler);
        context.registerReceiver(networkListener, NetworkListener.intentFilter());
    }

    private void unRegisterNetworkListener() {
        if (networkListener == null)
            return;
        context.unregisterReceiver(networkListener);
    }

    private void processNetworkState(int type) {
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_MOBILE:
                requestBankServices();
                break;
            default://---------无网络
                if (isAttached() && !attachedView.get().isDataPrepared())
                    attachedView.get().onDataPreparedFailed();
                break;
        }
    }

    private List<Object> packBankService(BankModel bankModel) {

        List<Object> list = new ArrayList<>();
        //banner
        BankBannerBean bankBannerBean = new BankBannerBean();
        ArrayList<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.drawable_banner_item1);
        bankBannerBean.setImages(imgs);
        list.add(bankBannerBean);

        //type
        ArrayList<SvcBean> types = bankModel.getBankType();
        list.addAll(types);
        //space
        list.add(SpaceBean.LINE_SPACE);
        //header
        list.add(new BankHeaderBean("热门问答"));
        //hot
        ArrayList<QuestSvcBean> hotQuestion = bankModel.getHotQuestion();
        list.addAll(hotQuestion);
        // list.add(new QuestSvcBean(QuestSvcBean.QuestSvcType.QUEST_SVC_MORE));
        //bottom
        list.add(SpaceBean.BOTTOM_SPACE);
        return list;
    }
}
