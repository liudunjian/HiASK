package com.hisense.hiask.splash;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hinetapi.entity.response.LoginResponse;
import com.hisense.hinetapi.manager.NetManager;
import com.hisense.hiretrofit.ApiException;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hitools.utils.NetworkUtils;
import com.hisense.hitools.utils.SpUtils;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class SplashPresenter extends BasePresenter<ISplash> {

    public static final int HANDLER_SPLASH_DELAY_MSG = 0x002;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_SPLASH_DELAY_MSG:
                    if (isAttached()) {
                        attachedView.get().quitActivity();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public SplashPresenter(Context context, ISplash iSplash) {
        super(context, iSplash);
    }

    public void startTimer() {
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH_DELAY_MSG, 3000);
    }

    public void stopTimer() {
        handler.removeMessages(HANDLER_SPLASH_DELAY_MSG);
    }

    @Override
    protected void onCreate() {
        if (NetworkUtils.isNetworkAvailable())
            checkLoginStateAndLogin();
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

    private void checkLoginStateAndLogin() {

        if (SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getBoolean(SpConstant.SP_USER_LOGIN_STATE, false)) {
            NetManager.authorizeProvider(context).login(SpUtils.getInstance(null).getString(SpConstant.SP_USER_ACCOUNT_NAME, ""), "",
                    SpUtils.getInstance(null).getString(SpConstant.SP_USER_SECERET_KEY, ""),
                    new BaseObserver<BaseEntity<LoginResponse>>() {
                        @Override
                        protected void onStart() {

                        }

                        @Override
                        protected void onSuccess(BaseEntity<LoginResponse> result) {
                            if (result.getErrNo() == 200) {
                                LoginResponse response = result.getSST();
                                //储存用户访问的必备信息
                                SpUtils spUtils = SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY);
                                spUtils.putString(SpConstant.SP_USER_ACCOUNT_NAME, response.getAccountName());
                                spUtils.putString(SpConstant.SP_USER_ACCOUNT_ID, response.getAccountId());
                                spUtils.putString(SpConstant.SP_USER_NICK_NAME, response.getNickName());
                                spUtils.putString(SpConstant.SP_USER_PHOTO_URL, response.getUserphoto());

                            } else {
                                this.onFailure(new ApiException(result.getErrNo(), result.getErrMsg()));
                            }
                        }

                        @Override
                        protected void onFailure(ApiException e) {
                            SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).clear();
                        }

                        @Override
                        protected void onFinish() {

                        }
                    });
        }
    }
}
