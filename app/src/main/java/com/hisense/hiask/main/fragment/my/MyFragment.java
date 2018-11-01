package com.hisense.hiask.main.fragment.my;

import android.support.annotation.NonNull;

import com.hisense.hiask.collect.CollectActivity;
import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.login.LoginActivity;
import com.hisense.hiask.main.fragment.my.multi.MultiMyItemDecoration;
import com.hisense.hiask.main.fragment.my.multi.MyFuncTypeItemViewProvider;
import com.hisense.hiask.main.fragment.my.multi.MyFuncTypePhotoItemProvider;
import com.hisense.hiask.mvpbase.BaseFragment;
import com.hisense.hiask.userinfo.UserInfoActivity;
import com.hisense.hibeans.main.MyFuncType;
import com.hisense.himultitype.view.LinearMultiView;
import com.hisense.hitools.utils.CacheUtils;
import com.hisense.hitools.utils.LogUtils;
import com.hisense.hitools.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by liudunjian on 2018/5/17.
 */

public class MyFragment extends BaseFragment<IMy, MyPresenter> implements IMy,
        MyFuncTypePhotoItemProvider.IMyFuncTypePhotoItemListener,
        MyFuncTypeItemViewProvider.IMyFuncTypeItemListener {


    @BindView(R.id.my_linear_multi_view)
    LinearMultiView myLinearMultiView;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initPresenter() {
        presenter = new MyPresenter(getContext(), this);
    }

    @Override
    protected void initViewDisplay() {
        initMultiView();
    }

    @Override
    protected void initEventsAndListeners() {

    }

    /*******************mvp override methods****************/

    @Override
    public void onDataPreparedSuccess(List<Object> data) {
        this.myLinearMultiView.resetItems(data);
    }


    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /**********************events and listeners************/
    @Override
    public void onMyFuncTypePhotoItemClicked() {
        if (SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getBoolean(SpConstant.SP_USER_LOGIN_STATE, false)) {
            activity.startActivitySafely(UserInfoActivity.class);
        } else {
            activity.startActivitySafely(LoginActivity.class);
        }
    }

    @Override
    public void onMyFuncTypeItemClicked(MyFuncType item) {
        switch (item) {
            case MY_FUNC_TYPE_COLLECT:
                if(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getBoolean(SpConstant.SP_USER_LOGIN_STATE,false)) {
                    activity.startActivitySafely(CollectActivity.class);
                }else {
                    activity.startActivitySafely(LoginActivity.class);
                }
                break;
            case MY_FUNC_TYPE_CLEAR_CACHE:
                CacheUtils.cleanApplicationData(getContext().getApplicationContext(), (String[]) null);
                this.myLinearMultiView.updateItem(item);
                break;
            case MY_FUNC_TYPE_CHECK_VERSION:
                LogUtils.d("MY_FUNC_TYPE_CHECK_VERSION");
                break;
            case MY_FUNC_TYPE_ABOUT_US:
                LogUtils.d("MY_FUNC_TYPE_ABOUT_US");
                break;
            case MY_FUNC_TYPE_EDIT_CYPHER:
                LogUtils.d("MY_FUNC_TYPE_EDIT_CYPHER");
                break;
            case MY_FUNC_TYPE_EXIT_LOGIN:
                presenter.loginOut();
                break;
            case MY_FUNC_TYPE_LOGIN:
                activity.startActivitySafely(LoginActivity.class);
                break;
            default:
                break;
        }
    }

    /*********************private methods******************/

    private void initMultiView() {

        myLinearMultiView.addItemDecoration(new MultiMyItemDecoration(myLinearMultiView, 20));
        myLinearMultiView.getAdapter()
                .register(MyFuncType.class)
                .to(new MyFuncTypePhotoItemProvider(this), new MyFuncTypeItemViewProvider(this))
                .withClassLinker(new ClassLinker<MyFuncType>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<MyFuncType, ?>> index(@NonNull MyFuncType myFuncType) {
                        if (myFuncType == MyFuncType.MY_FUNC_TYPE_PHOTO)
                            return MyFuncTypePhotoItemProvider.class;
                        else
                            return MyFuncTypeItemViewProvider.class;
                    }
                });
    }


}
