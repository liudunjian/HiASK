package com.hisense.hiask.main.fragment.bank.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/13.
 */

public class BankHotQuestViewProvider extends BaseViewProvider<QuestSvcBean, BankHotQuestViewProvider.ViewHolder> {

    private IBankHotItemListener listener;

    public BankHotQuestViewProvider(IBankHotItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_bank_hot_quest_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull QuestSvcBean item) {
        holder.bindData(item);
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.multi_bank_hot_quest_title)
        TextView multiBankHotQuestTitle;

        private IBankHotItemListener listener;
        private QuestSvcBean item;

        public ViewHolder(View itemView, IBankHotItemListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(QuestSvcBean item) {
            if (item == null)
                return;
            this.item = item;
            multiBankHotQuestTitle.setText(item.getQuestion());
        }

        @OnClick(R.id.multi_bank_hot_item_root)
        public void onViewClicked() {
            if(listener == null)
                return;
            listener.onBankHotItemClicked(item);

        }
    }

    public interface IBankHotItemListener {
        void onBankHotItemClicked(QuestSvcBean item);
    }
}
