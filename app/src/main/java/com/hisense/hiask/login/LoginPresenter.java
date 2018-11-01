package com.hisense.hiask.login;

import android.content.Context;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hinetapi.entity.response.LoginResponse;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.utils.SpUtils;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class LoginPresenter extends BasePresenter<ILogin> {

    public LoginPresenter(Context context, ILogin iLogin) {
        super(context, iLogin);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onStart() {

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

    public void login(String phone, final String cypher) {
        NetManager.authorizeProvider(context).login(phone, "", cypher, new BaseObserver<BaseEntity<LoginResponse>>() {
            @Override
            protected void onStart() {

            }

            @Override
            protected void onSuccess(BaseEntity<LoginResponse> result) {
                if (result.getErrNo() == 200) {
                    LoginResponse response = result.getSST();
                    SpUtils spUtils = SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY);
                    spUtils.putBoolean(SpConstant.SP_USER_LOGIN_STATE, true);
                    spUtils.putString(SpConstant.SP_USER_ACCOUNT_NAME, response.getAccountName());
                    spUtils.putString(SpConstant.SP_USER_ACCOUNT_ID, response.getAccountId());
                    spUtils.putString(SpConstant.SP_USER_SECERET_KEY, cypher);
                    spUtils.putString(SpConstant.SP_USER_PHOTO_URL, response.getUserphoto());
                    spUtils.putString(SpConstant.SP_USER_NICK_NAME, response.getNickName());
                    if (isAttached())
                        attachedView.get().onLoginSuccessful();

                } else {
                    this.onFailure(new ApiException(result.getErrNo(), result.getErrMsg()));
                }
            }

            @Override
            protected void onFailure(ApiException e) {

            }

            @Override
            protected void onFinish() {

            }
        });
    }
}
