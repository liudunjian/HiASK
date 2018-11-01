package com.hisense.hiask.main.fragment.my;

import android.content.Context;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.main.MyFuncType;
import com.hisense.hitools.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/5/17.
 */

public class MyPresenter extends BasePresenter<IMy> {

    protected MyPresenter(Context context, IMy iMy) {
        super(context, iMy);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onStart() {
        requestPageData();
    }

    @Override
    protected void onResume() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onDestroy() {

    }

    public void loginOut() {
        SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).clear();
        requestPageData();
    }

    public void requestPageData() {
        List<Object> data = new ArrayList<>();
        data.add(MyFuncType.MY_FUNC_TYPE_PHOTO);
        data.add(MyFuncType.MY_FUNC_TYPE_COLLECT);
        data.add(MyFuncType.MY_FUNC_TYPE_CLEAR_CACHE);
        data.add(MyFuncType.MY_FUNC_TYPE_CHECK_VERSION);
        data.add(MyFuncType.MY_FUNC_TYPE_ABOUT_US);
        if (SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getBoolean(SpConstant.SP_USER_LOGIN_STATE, false)) {
            data.add(MyFuncType.MY_FUNC_TYPE_EDIT_CYPHER);
            data.add(MyFuncType.MY_FUNC_TYPE_EXIT_LOGIN);
        } else {
            data.add(MyFuncType.MY_FUNC_TYPE_LOGIN);
        }
        if (isAttached())
            attachedView.get().onDataPreparedSuccess(data);
    }
}
