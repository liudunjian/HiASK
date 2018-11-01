package com.hisense.hiask.quest;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hiask.quest.multi.QuestCategoryViewProvider;
import com.hisense.hiask.quest.multi.QuestContentViewProvider;
import com.hisense.hiask.quest.widget.DetailPopWindow;
import com.hisense.hiask.widget.loading.CircleLoadingView;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.view.LinearMultiView;
import com.hisense.hitools.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/11.
 */

public class QuestActivity extends BaseActivity<IQuest, QuestPresenter> implements IQuest,
        QuestCategoryViewProvider.IQuestListener,
        QuestContentViewProvider.IQuestContentListener,
        DetailPopWindow.IDetailPopCollectListener {

    @BindView(R.id.quest_toolbar_title)
    TextView questToolbarTitle;
    @BindView(R.id.quest_linear_category_multi_view)
    LinearMultiView questLinearCategoryMultiView;
    @BindView(R.id.quest_linear_content_multi_view)
    LinearMultiView questLinearContentMultiView;
    @BindView(R.id.quest_loading_view)
    CircleLoadingView questLoadingView;
    @BindView(R.id.quest_linear_multi_view_layout)
    LinearLayout questLinearMultiViewLayout;
    @BindView(R.id.quest_toolbar_layout)
    RelativeLayout questToolbarLayout;
    @BindView(R.id.quest_title_close)
    ImageView questTitleClose;

    private boolean isDataPrepared = false;
    private QuestCategoryViewProvider categoryViewProvider = null;
    private DetailPopWindow detailPopWindow;

    /*********************base override methods******************/
    @Override
    protected void initViewDisplay() {
        presenter.parseBundle(getActivityStartedBundle());
        initCategoryMultiView();
        initContentMultiView();
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new QuestPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_quest;
    }

    @Override
    public void onBackPressed() {
        if (detailPopWindow != null && detailPopWindow.isShowing()) {
            detailPopWindow.dismiss();
            detailPopWindow = null;
            notifyCloseButtonState();
        } else {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (detailPopWindow != null) {
            detailPopWindow.dismiss();
            detailPopWindow = null;
        }
        super.onDestroy();
    }

    /*************************mvp override methods******************/

    @Override
    public void onCategoryPreparedStarted() {
        questLinearMultiViewLayout.setVisibility(View.GONE);
        questLoadingView.setVisibility(View.VISIBLE);
        questLoadingView.startLoadingData();
    }

    @Override
    public void onCategoryPreparedSuccess(List<Object> data) {
        questLinearMultiViewLayout.setVisibility(View.VISIBLE);
        questLinearCategoryMultiView.resetItems(data);
        categoryViewProvider.setCurPos(0);
    }

    @Override
    public void onCategoryPreparedFailed() {
        questLinearMultiViewLayout.setVisibility(View.GONE);
        questLoadingView.setVisibility(View.VISIBLE);
        questLoadingView.notifyLoadingDataFailed();
    }

    @Override
    public void onContentPreparedStarted() {
        questLinearContentMultiView.setVisibility(View.GONE);
        questLoadingView.setVisibility(View.VISIBLE);
        questLoadingView.startLoadingData();
    }

    @Override
    public void onContentPreparedSuccess(List<Object> data) {
        questLinearContentMultiView.setVisibility(View.VISIBLE);
        questLoadingView.setVisibility(View.GONE);
        questLinearContentMultiView.resetItems(data);
    }

    @Override
    public void onContentPreparedFailed() {
        questLinearContentMultiView.setVisibility(View.GONE);
        questLoadingView.setVisibility(View.VISIBLE);
        questLoadingView.notifyLoadingDataFailed();
    }

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    @Override
    public void onIntentParsed(String name) {
        this.questToolbarTitle.setText(name);
    }


    /*******************listeners and events***************/
    @OnClick({R.id.quest_title_back, R.id.quest_title_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quest_title_back:
                this.onBackPressed();
                break;
            case R.id.quest_title_close:
                this.finish();
                break;
        }
    }

    @Override
    public void onQuestItemChoose(SvcBean item, int pos) {
        questLinearCategoryMultiView.smoothScrollToPosition(pos);
        presenter.requestQuestContentFromNetwork(item);
    }

    @Override
    public void onDetailPopCollectClicked(QuestSvcBean item) {
        if (SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getBoolean(SpConstant.SP_USER_LOGIN_STATE, false)) {
            presenter.collectQuestService(item);
        } else {

        }
    }

    @Override
    public void onQuestContentItemClicked(QuestSvcBean item) {
        showPopDetailWindow(item);
    }

    @Override
    public void onDataCollectSuccess(QuestSvcBean item) {
        item.setIs_collected(1);
        detailPopWindow.notifyCollectStateChange();
    }

    /************************private methods****************/
    private void initCategoryMultiView() {
        categoryViewProvider = new QuestCategoryViewProvider(this);
        questLinearCategoryMultiView.getAdapter().register(SvcBean.class, this.categoryViewProvider);
    }

    private void initContentMultiView() {
        questLinearContentMultiView.getAdapter().register(QuestSvcBean.class, new QuestContentViewProvider(this));
    }

    private void showPopDetailWindow(QuestSvcBean item) {
        if (detailPopWindow == null)
            detailPopWindow = new DetailPopWindow(this, null, android.R.style.Theme_Dialog, this);
        if (detailPopWindow.isShowing())
            detailPopWindow.dismiss();
        detailPopWindow.setItem(item);
        detailPopWindow.showAsDropDown(questToolbarLayout);
        notifyCloseButtonState();
    }

    private void notifyCloseButtonState() {
        if (detailPopWindow != null && detailPopWindow.isShowing()) {
            if (questTitleClose.getVisibility() != View.VISIBLE)
                questTitleClose.setVisibility(View.VISIBLE);
        } else
            questTitleClose.setVisibility(View.GONE);
    }

}
