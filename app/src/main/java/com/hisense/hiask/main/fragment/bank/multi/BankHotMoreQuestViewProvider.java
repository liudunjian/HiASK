package com.hisense.hiask.main.fragment.bank.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class BankHotMoreQuestViewProvider extends BaseViewProvider<QuestSvcBean,BankHotMoreQuestViewProvider.ViewHolder>{

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_bank_hot_quest_more_item_layout,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull QuestSvcBean item) {

    }

    static class ViewHolder extends BaseViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
