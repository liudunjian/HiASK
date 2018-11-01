package com.hisense.hinetapi.provider;

import android.content.Context;

import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.net.BankModel;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.hinetapi.NetConstant;
import com.hisense.hinetapi.service.AskService;
import com.hisense.hiretrofit.builder.RetrofitBuilder;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hiretrofit.transformer.RetrofitTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class AskProvider {

    private Context context;
    private AskService askService;

    public AskProvider(Context context) {
        this.context = context;
    }

    public void generateAsstService() {
        this.askService = new RetrofitBuilder()
                .baseURL(NetConstant.SERVICE_BASE_URL)
                .connectTimeout(NetConstant.CONNECT_TIMEOUT_SECOND)
                .readTimeout(NetConstant.READ_TIMEOUT_SECOND)
                .writeTimeout(NetConstant.WRITE_TIMEOUT_SECOND)
                .enableDebug(true)
                .headers(this.headers())
                .userAgent(context.getPackageName())
                .builder(AskService.class);
    }

    private Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json;charset=UTF-8");
        return headers;
    }


    public void fetchBankServices(String accountId,BaseObserver<BaseEntity<BankModel>> observer) {
        askService.fetchBankServices(accountId).
                compose(RetrofitTransformer.<BaseEntity<BankModel>>mainThread())
                .subscribe(observer);
    }

    public void fetchFormServices(int pid, BaseObserver<BaseEntity<ArrayList<SvcBean>>> observer) {
        askService.fetchFormServices(pid)
                .compose(RetrofitTransformer.<BaseEntity<ArrayList<SvcBean>>>mainThread())
                .subscribe(observer);
    }

    public void fetchQuestServices(int id, int pageNum, String accountId, BaseObserver<BaseEntity<ArrayList<QuestSvcBean>>> observer) {
        askService.fetchQuestServices(id, pageNum, accountId)
                .compose(RetrofitTransformer.<BaseEntity<ArrayList<QuestSvcBean>>>mainThread())
                .subscribe(observer);
    }

    public void collectQuestService(String accountId, String questionId, BaseObserver<BaseEntity<Boolean>> observer) {
        askService.collectQuestService(accountId, questionId)
                .compose(RetrofitTransformer.<BaseEntity<Boolean>>mainThread())
                .subscribe(observer);
    }

    public void fetchCollectedQuestServices(String accountId, BaseObserver<BaseEntity<ArrayList<QuestSvcBean>>> observer) {
        askService.fetchCollectedQuest(accountId)
                .compose(RetrofitTransformer.<BaseEntity<ArrayList<QuestSvcBean>>>mainThread())
                .subscribe(observer);
    }

    public void deleteCollectedQuestService(int id,BaseObserver<BaseEntity<Boolean>> observer) {
        askService.deleteCollectedQuest(id)
                .compose(RetrofitTransformer.<BaseEntity<Boolean>>mainThread())
                .subscribe(observer);
    }

}
