package com.hisense.hiask.collect;

import android.os.Bundle;
import android.view.View;

import com.hisense.hiask.collect.multi.CollectQuestItemProvider;
import com.hisense.hiask.detail.DetailActivity;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hiask.widget.itemtouch.extension.ItemTouchCallbackExtension;
import com.hisense.hiask.widget.itemtouch.extension.ItemTouchHelperExtension;
import com.hisense.hiask.widget.itemtouch.extension.ItemTouchHelperMoveListener;
import com.hisense.hiask.widget.loading.CircleLoadingView;
import com.hisense.hiask.widget.refresh.RxRefreshLayout;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.view.LinearMultiView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/20.
 */

public class CollectActivity extends BaseActivity<ICollect, CollectPresenter> implements ICollect,
        ItemTouchHelperMoveListener,
        CollectQuestItemProvider.ICollectQuestItemListener{


    @BindView(R.id.collect_multiview)
    LinearMultiView collectMultiview;
    @BindView(R.id.collect_refresh_layout)
    RxRefreshLayout collectRefreshLayout;
    @BindView(R.id.collect_loading_view)
    CircleLoadingView collectLoadingView;

    private boolean isDataPrepared = false;
    private ItemTouchHelperExtension itemTouchHelper;

    /************************base override methods*******************/
    @Override
    protected void initViewDisplay() {
        initCollectMultiView();
        initDragCallback();
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new CollectPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_collect;
    }

    /***************************mvp override methods**********************/
    @Override
    public boolean isDataPrepared() {
        return isDataPrepared;
    }

    @Override
    public void onDataPreparedStarted() {
        collectMultiview.setVisibility(View.GONE);
        collectLoadingView.setVisibility(View.VISIBLE);
        collectLoadingView.startLoadingData();
    }

    @Override
    public void onDataPreparedSuccess(List<Object> data) {
        isDataPrepared = true;
        collectLoadingView.stopLoadingData();
        collectLoadingView.setVisibility(View.GONE);
        collectMultiview.setVisibility(View.VISIBLE);
        collectMultiview.resetItems(data);
    }

    @Override
    public void onDataPreparedFailed() {
        collectMultiview.setVisibility(View.GONE);
        collectLoadingView.setVisibility(View.VISIBLE);
        collectLoadingView.notifyLoadingDataFailed();
    }

    @Override
    public void onDataDeleteSuccess(QuestSvcBean item) {
        collectMultiview.removeItem(item);
    }


    /*************************events and listeners***********************/

    @OnClick(R.id.detail_title_back)
    public void onViewClicked() {
        this.onBackPressed();
    }

    @Override
    public void onItemTouchItemMove(int from, int to) {

    }

    @Override
    public void onCollectQuestItemContentClicked(QuestSvcBean item) {
        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.DETAIL_QUEST_DATA_TRANSFER_KEY, item.toString());
        startActivitySafely(DetailActivity.class, bundle);
    }

    @Override
    public void onCollectQuestItemDeleteClicked(QuestSvcBean item) {
        presenter.deleteCollectedQuestService(item);
    }

    /************************private methods****************************/

    private void initCollectMultiView() {
        this.collectMultiview.getAdapter().register(QuestSvcBean.class,new CollectQuestItemProvider(this));
    }

    private void initDragCallback() {
        ItemTouchCallbackExtension callback = new ItemTouchCallbackExtension(CollectActivity.this);
        this.itemTouchHelper = new ItemTouchHelperExtension(callback);
        this.itemTouchHelper.attachToRecyclerView(collectMultiview);
    }


}
