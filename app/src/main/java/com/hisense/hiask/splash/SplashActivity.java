package com.hisense.hiask.splash;

import android.widget.TextView;

import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.guide.GuideActivity;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.main.MainActivity;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hitools.utils.AppUtils;
import com.hisense.hitools.utils.SpUtils;

import butterknife.BindView;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class SplashActivity extends BaseActivity<ISplash, SplashPresenter> implements ISplash {

    @BindView(R.id.splash_version)
    TextView splashVersion;
    @BindView(R.id.splash_build)
    TextView splashBuild;

    /************base override methods****************/
    @Override
    protected void initViewDisplay() {
        splashVersion.setText(versionName());
        splashBuild.setText(versionCode());
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new SplashPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            presenter.startTimer();
        } else {
            presenter.stopTimer();
        }
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**************************mvp override methods***************/

    @Override
    public void quitActivity() {
        if (SpUtils.getInstance(null).getBoolean(SpConstant.SP_FIRST_START_KEY, true)) {
            SpUtils.getInstance(null).putBoolean(SpConstant.SP_FIRST_START_KEY,false);
            startActivitySafely(GuideActivity.class);
        } else {
            startActivitySafely(MainActivity.class);
        }
        finish();
    }


    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /************************private methods**********************/

    private String versionName() {
        String versionName = AppUtils.getAppVersionName(AppUtils.getPackageName());
        return String.format("V-%s", versionName);
    }

    private String versionCode() {
        int versionCode = AppUtils.getAppVersionCode(AppUtils.getPackageName());
        return String.format("B-%s", versionCode);
    }

}
