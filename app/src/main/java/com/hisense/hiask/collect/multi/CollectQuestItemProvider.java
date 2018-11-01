package com.hisense.hiask.collect.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.widget.itemtouch.extension.Extension;
import com.hisense.hiask.widget.itemtouch.extension.IItemTouchListenerExtension;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/20.
 */

public class CollectQuestItemProvider extends BaseViewProvider<QuestSvcBean, CollectQuestItemProvider.ViewHolder> {

    private ICollectQuestItemListener listener;

    public CollectQuestItemProvider(ICollectQuestItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_collect_quest_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull QuestSvcBean item) {
        holder.bindData(item);
    }


    static class ViewHolder extends BaseViewHolder implements IItemTouchListenerExtension, Extension {

        @BindView(R.id.multi_collect_delete_root)
        FrameLayout multiCollectDeleteRoot;
        @BindView(R.id.multi_collect_content_title)
        TextView multiCollectContentTitle;
        @BindView(R.id.multi_collect_content_detail)
        TextView multiCollectContentDetail;
        @BindView(R.id.multi_collect_content_root)
        LinearLayout multiCollectContentRoot;

        private ICollectQuestItemListener listener;
        private QuestSvcBean item;

        public ViewHolder(View itemView, ICollectQuestItemListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(QuestSvcBean item) {
            if (item == null)
                return;
            this.item = item;
            multiCollectContentTitle.setText(item.getQuestion());
            multiCollectContentDetail.setText(item.getAnswer());
        }

        @OnClick({R.id.multi_collect_delete_btn, R.id.multi_collect_content_root})
        public void onViewClicked(View view) {
            if (listener == null || item == null)
                return;
            switch (view.getId()) {
                case R.id.multi_collect_delete_btn:
                    listener.onCollectQuestItemDeleteClicked(item);
                    break;
                case R.id.multi_collect_content_root:
                    listener.onCollectQuestItemContentClicked(item);
                    break;
            }
        }

        @Override
        public void onChildDraw(float dx) {
            if (dx < -multiCollectDeleteRoot.getWidth()) {
                dx = -multiCollectDeleteRoot.getWidth();
            }
            multiCollectContentRoot.setTranslationX(dx);
        }

        @Override
        public float getActionWidth() {
            return multiCollectDeleteRoot.getWidth();
        }
    }

    public interface ICollectQuestItemListener {
        void onCollectQuestItemContentClicked(QuestSvcBean item);

        void onCollectQuestItemDeleteClicked(QuestSvcBean item);
    }
}
