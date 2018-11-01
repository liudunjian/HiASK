package com.hisense.hiask.main.fragment.bank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.hisense.hiask.detail.DetailActivity;
import com.hisense.hiask.form.FormActivity;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.main.fragment.bank.multi.BankBannerViewProvider;
import com.hisense.hiask.main.fragment.bank.multi.BankHeaderViewProvider;
import com.hisense.hiask.main.fragment.bank.multi.BankHotMoreQuestViewProvider;
import com.hisense.hiask.main.fragment.bank.multi.BankHotQuestViewProvider;
import com.hisense.hiask.main.fragment.bank.multi.BankSpanSizeLookup;
import com.hisense.hiask.main.fragment.bank.multi.BankSvcViewProvider;
import com.hisense.hiask.main.fragment.bank.multi.SpaceViewProvider;
import com.hisense.hiask.mvpbase.BaseFragment;
import com.hisense.hiask.widget.loading.CircleLoadingView;
import com.hisense.hibeans.main.BankBannerBean;
import com.hisense.hibeans.main.BankHeaderBean;
import com.hisense.hibeans.main.SpaceBean;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.view.GridMultiView;

import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;

import static com.hisense.hiask.main.MainActivity.MAIN_FORM_DATA_TRANSFER_KEY;

/**
 * Created by liudunjian on 2018/5/11.
 */

public class BankFragment extends BaseFragment<IBank, BankPresenter> implements IBank,
        CircleLoadingView.ILoadingRetryListener,
        BankSvcViewProvider.IMainSvcItemClickListener,
        BankHotQuestViewProvider.IBankHotItemListener {

    @BindView(R.id.bank_grid_multi_view)
    GridMultiView bankGridMultiView;
    @BindView(R.id.bank_loading_view)
    CircleLoadingView bankLoadingView;

    private boolean isDataPrepared = false;

    public static BankFragment newInstance() {
        return new BankFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_bank;
    }

    @Override
    protected void initPresenter() {
        presenter = new BankPresenter(getContext(), this);
    }

    @Override
    protected void initViewDisplay() {
        initMultiView();
    }

    @Override
    protected void initEventsAndListeners() {
        bankLoadingView.setLoadingRetryListener(this);
    }

    /************mvp override methods****************/
    @Override
    public void onDataPreparedStarted() {
        bankGridMultiView.setVisibility(View.GONE);
        bankLoadingView.setVisibility(View.VISIBLE);
        bankLoadingView.startLoadingData();
    }

    @Override
    public void onDataPreparedSuccess(List<Object> data) {
        isDataPrepared = true;
        bankLoadingView.stopLoadingData();
        bankLoadingView.setVisibility(View.GONE);
        bankGridMultiView.setVisibility(View.VISIBLE);
        bankGridMultiView.resetItems(data);
    }

    @Override
    public void onDataPreparedFailed() {
        bankGridMultiView.setVisibility(View.GONE);
        bankLoadingView.setVisibility(View.VISIBLE);
        bankLoadingView.notifyLoadingDataFailed();
    }

    @Override
    public boolean isDataPrepared() {
        return isDataPrepared;
    }

    /*******************events and listeners*******************/
    @Override
    public void onRetryLoadingClicked() {
        presenter.requestBankServices();
    }

    @Override
    public void onMainSvcItemClicked(SvcBean item) {
        if (activity != null) {
            Bundle bundle = new Bundle();
            bundle.putString(MAIN_FORM_DATA_TRANSFER_KEY, item.toString());
            activity.startActivitySafely(FormActivity.class, bundle);
        }
    }

    @Override
    public void onBankHotItemClicked(QuestSvcBean item) {
        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.DETAIL_QUEST_DATA_TRANSFER_KEY, item.toString());
        activity.startActivitySafely(DetailActivity.class, bundle);
    }

    /*******************private methods*********************/

    private void initMultiView() {

        bankGridMultiView.getLayoutManager().setSpanSizeLookup(new BankSpanSizeLookup(bankGridMultiView));
        bankGridMultiView.getAdapter().register(SvcBean.class, new BankSvcViewProvider(getContext(), this));
        bankGridMultiView.getAdapter().register(BankBannerBean.class, new BankBannerViewProvider());
        bankGridMultiView.getAdapter().register(BankHeaderBean.class, new BankHeaderViewProvider());
        bankGridMultiView.getAdapter().register(SpaceBean.class, new SpaceViewProvider());

        bankGridMultiView.getAdapter().register(QuestSvcBean.class)
                .to(new BankHotQuestViewProvider(this), new BankHotMoreQuestViewProvider())
                .withClassLinker(new ClassLinker<QuestSvcBean>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<QuestSvcBean, ?>> index(@NonNull QuestSvcBean questSvcBean) {
                        switch (questSvcBean.getType()) {
                            case QUEST_SVC_MORE:
                                return BankHotMoreQuestViewProvider.class;
                            default:
                                return BankHotQuestViewProvider.class;
                        }
                    }
                });
    }
}
