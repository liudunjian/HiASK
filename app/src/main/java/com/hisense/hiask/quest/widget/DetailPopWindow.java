package com.hisense.hiask.quest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.hitools.utils.LogUtils;

/**
 * Created by liudunjian on 2018/6/12.
 */

public class DetailPopWindow extends PopupWindow {

    private IDetailPopCollectListener listener;

    private View rootView;
    private TextView popDetailQuestion;
    private ImageView popDetailCollect;
    private TextView popDetailAnswer;

    private QuestSvcBean item;

    public DetailPopWindow(Context context, IDetailPopCollectListener listener) {
        this(context, null, 0,listener);
    }

    public DetailPopWindow(Context context, AttributeSet attrs, int defStyleAttr,IDetailPopCollectListener listener) {
        super(context, attrs, defStyleAttr);
        this.listener = listener;
        initQuestPopWindow(context);
    }

    private void initQuestPopWindow(Context context) {

        rootView = LayoutInflater.from(context).inflate(R.layout.pop_quest_answer_layout, null);
        popDetailQuestion = (TextView) rootView.findViewById(R.id.pop_detail_question);
        popDetailCollect = (ImageView) rootView.findViewById(R.id.pop_detail_collect);
        popDetailAnswer = (TextView) rootView.findViewById(R.id.pop_detail_answer);

        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(false);
        this.setOutsideTouchable(true);

        popDetailCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener == null||item == null)
                    return;
                listener.onDetailPopCollectClicked(item);
            }
        });
    }

    public QuestSvcBean getItem() {
        return item;
    }

    public void setItem(QuestSvcBean item) {
        if (item == null)
            return;
        this.item = item;
        this.popDetailQuestion.setText(item.getQuestion());
        this.popDetailAnswer.setText(item.getAnswer());
        if(item.getIs_collected() == 1) {
            popDetailCollect.setImageResource(R.drawable.ic_my_collect);
        }else if(item.getIs_collected() == 0) {
            popDetailCollect.setImageResource(R.drawable.ic_not_collect);
        }
    }

    public void notifyCollectStateChange() {
        if( item == null)
            return;
        popDetailCollect.setImageResource(R.drawable.ic_my_collect);
    }

    public interface IDetailPopCollectListener {
        void onDetailPopCollectClicked(QuestSvcBean item);
    }
}
