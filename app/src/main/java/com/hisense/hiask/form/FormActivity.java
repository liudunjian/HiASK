package com.hisense.hiask.form;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.hisense.hiask.form.multi.FormSvcViewProvider;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hiask.quest.QuestActivity;
import com.hisense.hiask.widget.loading.CircleLoadingView;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.himultitype.view.LinearMultiView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class FormActivity extends BaseActivity<IForm, FormPresenter> implements IForm,
        FormSvcViewProvider.IFormSvcClickListener,
        CircleLoadingView.ILoadingRetryListener {

    @BindView(R.id.form_toolbar_title)
    TextView formToolbarTitle;
    @BindView(R.id.form_linear_multi_view)
    LinearMultiView formLinearMultiView;

    public static final String FORM_QUEST_DATA_TRANSFER_KEY = "MAIN_FORM_DATA_TRANSFER_KEY";

    @BindView(R.id.form_circle_loading_view)
    CircleLoadingView formCircleLoadingView;

    private boolean isDataPrepared = false;

    /*****************base override methods*****************/
    @Override
    protected void initViewDisplay() {
        presenter.parseBundle(getActivityStartedBundle());
        initMultiView();
    }

    @Override
    protected void initEventsAndListeners() {
        formCircleLoadingView.setLoadingRetryListener(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new FormPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_form;
    }

    /*****************mvp override methods*************/

    @Override
    public void onDataPreparedStarted() {
        formCircleLoadingView.setVisibility(View.VISIBLE);
        formLinearMultiView.setVisibility(View.GONE);
        formCircleLoadingView.startLoadingData();
    }

    @Override
    public void onDataPreparedSuccess(List<Object> data) {
        formCircleLoadingView.stopLoadingData();
        formCircleLoadingView.setVisibility(View.GONE);
        formLinearMultiView.setVisibility(View.VISIBLE);
        this.formLinearMultiView.resetItems(data);
    }

    @Override
    public void onDataPreparedFailed() {
        formLinearMultiView.setVisibility(View.GONE);
        formCircleLoadingView.setVisibility(View.VISIBLE);
        formCircleLoadingView.notifyLoadingDataFailed();
    }

    @Override
    public boolean isDataPrepared() {
        return isDataPrepared;
    }

    @Override
    public void onIntentParsed(String title) {
        formToolbarTitle.setText(title);
    }

    /*****************events and listeners**************/
    @Override
    public void onFormSvcClicked(SvcBean item) {
        Bundle bundle = new Bundle();
        bundle.putString(FORM_QUEST_DATA_TRANSFER_KEY, item.toString());
        startActivitySafely(QuestActivity.class, bundle);
    }

    @OnClick(R.id.form_title_back)
    public void onViewClicked() {
        this.onBackPressed();
    }

    @Override
    public void onRetryLoadingClicked() {
        presenter.requestDataFromNetwork();
    }

    /********************private methods****************/

    private void initMultiView() {
        formLinearMultiView.getAdapter().register(SvcBean.class, new FormSvcViewProvider(this));
    }

    public void moveToPosition(int n) {
        LinearLayoutManager manager = formLinearMultiView.getLayoutManager();
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            formLinearMultiView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int movePos = n - firstItem;
            if (movePos >= 0 && movePos < formLinearMultiView.getChildCount()) {
                int top = formLinearMultiView.getChildAt(movePos).getTop();
                formLinearMultiView.scrollBy(0, top);
            }
        } else {
            formLinearMultiView.scrollToPosition(n);
        }
    }
}
