package com.hisense.hiask.quest.multi;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/11.
 */

public class QuestCategoryViewProvider extends BaseViewProvider<SvcBean, QuestCategoryViewProvider.ViewHolder> {

    private IQuestListener listener;
    private int curPos = -1;

    public QuestCategoryViewProvider(IQuestListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_quest_category_item_layout, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SvcBean item) {
        holder.bindData(item);
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        if(EmptyUtils.isEmpty(this.listener))
            return;
        this.curPos = curPos;
        getAdapter().notifyDataSetChanged();
        listener.onQuestItemChoose((SvcBean) getAdapter().getItems().get(curPos),curPos);
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.multi_quest_category_title)
        TextView multiQuestCategoryTitle;
        @BindView(R.id.multi_quest_category_indicator)
        View multiQuestCategoryIndicator;
        @BindView(R.id.multi_quest_content_layout)
        LinearLayout multiQuestContentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindData(SvcBean item) {
            if (EmptyUtils.isEmpty(item))
                return;
            multiQuestCategoryTitle.setText(item.getName());
            if (getAdapterPosition() == curPos) {
                multiQuestCategoryTitle.setTextColor(Color.rgb(70, 159, 239));
                multiQuestCategoryIndicator.setVisibility(View.VISIBLE);
            } else {
                multiQuestCategoryTitle.setTextColor(Color.rgb(81, 79, 79));
                multiQuestCategoryIndicator.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.multi_quest_content_layout)
        public void onViewClicked() {
            setCurPos(getAdapterPosition());
        }
    }

    public interface IQuestListener {
        void onQuestItemChoose(SvcBean item, int pos);
    }

}
