package com.hisense.hibeans.net;

import com.google.gson.annotations.SerializedName;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.question.QuestSvcBean;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/6/13.
 */

public class BankModel {

    @SerializedName("type1Info")
    private ArrayList<SvcBean> bankType;
    @SerializedName("hotQuestion")
    private ArrayList<QuestSvcBean> hotQuestion;

    public ArrayList<SvcBean> getBankType() {
        return bankType;
    }

    public void setBankType(ArrayList<SvcBean> bankType) {
        this.bankType = bankType;
    }

    public ArrayList<QuestSvcBean> getHotQuestion() {
        return hotQuestion;
    }

    public void setHotQuestion(ArrayList<QuestSvcBean> hotQuestion) {
        this.hotQuestion = hotQuestion;
    }
}
