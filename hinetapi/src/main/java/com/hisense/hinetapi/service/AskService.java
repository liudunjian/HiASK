package com.hisense.hinetapi.service;

import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.net.BankModel;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hibeans.question.QuestSvcBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liudunjian on 2018/4/26.
 */

public interface AskService {

    @GET("office/getType1Info1")
    Observable<BaseEntity<BankModel>> fetchBankServices(@Query("accountId") String accountId);

    @GET("office/getChildTypeInfo")
    Observable<BaseEntity<ArrayList<SvcBean>>> fetchFormServices(@Query("pid") int pid);

    @GET("office/getAnswerAndQuestion")
    Observable<BaseEntity<ArrayList<QuestSvcBean>>> fetchQuestServices(@Query("typeId") int id, @Query("pageNum") int pageNum, @Query("accountId") String accountId);

    @GET("office/saveQuestion")
    Observable<BaseEntity<Boolean>> collectQuestService(@Query("accountId") String accountId, @Query("questionId") String questionId);

    @GET("office/checkQuestion")
    Observable<BaseEntity<ArrayList<QuestSvcBean>>> fetchCollectedQuest(@Query("accountId") String accountId);

    @GET("office/cancelQuestion")
    Observable<BaseEntity<Boolean>> deleteCollectedQuest(@Query("id") int id);
}

