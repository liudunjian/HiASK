package com.hisense.hiask.main.fragment.home;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.main.fragment.home.multi.HomeHeaderViewProvider;
import com.hisense.hiask.main.fragment.home.multi.SpanSizeLookup;
import com.hisense.hiask.mvpbase.BaseFragment;
import com.hisense.hiask.robot.RobotActivity;
import com.hisense.hibeans.main.HomeHeaderBean;
import com.hisense.himultitype.view.GridMultiView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liudunjian on 2018/10/31.
 */

public class HomeFragment extends BaseFragment<IHome, HomePresenter> implements IHome,HomeHeaderViewProvider.IHomeHeaderListener {


    @BindView(R.id.multi_view)
    GridMultiView multiView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    /********************base override methods***************/
    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(activity, this);
    }

    @Override
    protected void initViewDisplay() {
        initMultiView();
    }

    @Override
    protected void initEventsAndListeners() {

    }

    /**************mvp override methods***************/

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    @Override
    public void onViewDataPrepared(List<Object> objects) {
        multiView.resetItems(objects);
    }

    /***************events and listeners**************/
    @Override
    public void onHomeHeaderEnterClicked() {
        activity.startActivitySafely(RobotActivity.class);
    }

    /*************private methods*******************/

    private void initMultiView() {
        multiView.getLayoutManager().setSpanSizeLookup(new SpanSizeLookup(multiView));
        multiView.getAdapter().register(HomeHeaderBean.class,new HomeHeaderViewProvider(this));
    }

}
