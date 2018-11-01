package com.hisense.hiask.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hibeans.question.QuestSvcBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/14.
 */

public class DetailActivity extends BaseActivity<IDetail, DetailPresenter> implements IDetail {

    @BindView(R.id.detail_title_back)
    ImageView detailTitleBack;
    @BindView(R.id.detail_title_close)
    ImageView detailTitleClose;
    @BindView(R.id.detail_toolbar_title)
    TextView detailToolbarTitle;
    @BindView(R.id.detailt_toolbar_layout)
    RelativeLayout detailtToolbarLayout;
    @BindView(R.id.detail_question)
    TextView detailQuestion;
    @BindView(R.id.detail_collect)
    ImageView detailCollect;
    @BindView(R.id.detail_answer)
    TextView detailAnswer;

    public static final String DETAIL_QUEST_DATA_TRANSFER_KEY = "DETAIL_QUEST_DATA_TRANSFER_KEY";

    /************************base override methods********************/
    @Override
    protected void initViewDisplay() {
        presenter.parseBundle(getActivityStartedBundle());
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new DetailPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_detail;
    }

    /*********************mvp override methods************************/

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    @Override
    public void onIntentParsed(QuestSvcBean item) {
        detailQuestion.setText(item.getQuestion());
        detailAnswer.setText(item.getAnswer());
        if (item.getIs_collected() == 1) {
            detailCollect.setImageResource(R.drawable.ic_my_collect);
        } else {
            detailCollect.setImageResource(R.drawable.ic_not_collect);
        }
    }

    @Override
    public void onDataCollectSuccess() {
        detailCollect.setImageResource(R.drawable.ic_my_collect);
    }

    /********************listeners and events************************/

    @OnClick({R.id.detail_title_back, R.id.detail_title_close, R.id.detail_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_title_back:
                this.finish();
                break;
            case R.id.detail_title_close:
                this.finish();
                break;
            case R.id.detail_collect:
                presenter.collectQuestService();
                break;
        }
    }
}
