package com.hisense.hiask.quest.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/12.
 */

public class QuestContentViewProvider extends BaseViewProvider<QuestSvcBean, QuestContentViewProvider.ViewHolder> {

    private IQuestContentListener listener;

    public QuestContentViewProvider(IQuestContentListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_quest_content_item_layout, parent, false), this.listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull QuestSvcBean item) {
        holder.bindData(item);
    }


    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.multi_quest_content_title)
        TextView multiQuestContentTitle;
        @BindView(R.id.multi_quest_content_detail)
        TextView multiQuestContentDetail;

        private IQuestContentListener listener;
        private QuestSvcBean item;

        public ViewHolder(View itemView, IQuestContentListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(QuestSvcBean item) {
            if (item == null)
                return;
            this.item = item;
            multiQuestContentTitle.setText(item.getQuestion());
            multiQuestContentDetail.setText(item.getAnswer());
        }

        @OnClick(R.id.multi_quest_content_root)
        public void onViewClicked() {
            if (EmptyUtils.isEmpty(this.listener))
                return;
            listener.onQuestContentItemClicked(this.item);
        }
    }

    public interface IQuestContentListener {
        void onQuestContentItemClicked(QuestSvcBean item);
    }
}
